package com.marcelo.loteriadossonhos.view.megaSenaScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.marcelo.loteriadossonhos.App
import com.marcelo.loteriadossonhos.data.Bet
import com.marcelo.loteriadossonhos.data.BetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Random

class MegaSenaViewModel(
    private val betRepository: BetRepository,
) : ViewModel() {


    private val _quantityNumbers = MutableStateFlow("")
    var quantityNumbers = _quantityNumbers.asStateFlow()
        private set

    private val _quantityBets = MutableStateFlow("")
    var quantityBets = _quantityBets.asStateFlow()
        private set

    private val _result = MutableStateFlow("")
    var result = _result.asStateFlow()
        private set

    private val _resultsToSaveNumbers = MutableStateFlow<List<String>>(emptyList())

    private val _showAlertDialog = MutableStateFlow(false)
    var showAlertDialog: StateFlow<Boolean> = _showAlertDialog.asStateFlow()
        private set

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    fun updateQuantityNumbers(newNumber: String) {
        if (newNumber.length < 3) {
            _quantityNumbers.value = validateInput(newNumber)
        }
    }

    fun updateQuantityBets(newBet: String) {
        if (newBet.length < 3) {
            _quantityBets.value = validateInput(newBet)
        }
    }

    private fun validateInput(input: String): String {
        return input.filter { char ->
            char in "0123456789"
        }
    }

    fun updateNumbers(rule: Int) {
        _result.value = ""

        val betList = mutableListOf<String>()
        val resultDisplayBuilder = StringBuilder()
        val quantityBetsInt = _quantityBets.value.toIntOrNull() ?: 0

        for (index in 1..quantityBetsInt) {
            val generatedNumbers = numberGenerator(_quantityNumbers.value, rule)

            betList.add(generatedNumbers)

            resultDisplayBuilder.append("[ $index ] ")
            resultDisplayBuilder.append(generatedNumbers)
            resultDisplayBuilder.append("\n\n")
        }
        _resultsToSaveNumbers.value = betList
        _result.value = resultDisplayBuilder.toString()
        _showAlertDialog.value = true
    }

    fun dismissAlertDialog() {
        _showAlertDialog.value = false
    }

    fun validateAndUpdate(rule: Int) {
        viewModelScope.launch {
            val quantityBetsInt = _quantityBets.value.toIntOrNull() ?: 0
            val quantityNumbersInt = _quantityNumbers.value.toIntOrNull() ?: 0

            when {
                quantityBetsInt < 1 || quantityBetsInt > 10 -> {
                    _errorMessage.value = "Número máximo de apostas não permitido"
                    return@launch
                }
                quantityNumbersInt < 6 || quantityNumbersInt > 15 -> {
                    _errorMessage.value = "O número deve ser entre 6 e 15"
                    return@launch
                }
                else -> {
                    updateNumbers(rule)
                }
            }
        }
    }


    fun saveNumberBet(type: String) {
        viewModelScope.launch {
            for (result in _resultsToSaveNumbers.value) {
                val bet = Bet(
                    type = type,
                    numbers = result
                )
                betRepository.insertBet(bet)
            }
            dismissAlertDialog()
        }
    }

    private fun numberGenerator(quantity: String, rule: Int): String {
        val numbers = mutableSetOf<Int>()
        while (true) {
            val numberRandom = Random().nextInt(60)

            when (rule) {
                1 -> if (numberRandom % 2 == 0) continue
                2 -> if (numberRandom % 2 != 0) continue
            }

            numbers.add(numberRandom + 1)
            if (numbers.size == quantity.toInt()) break
        }

        return numbers.joinToString(" - ")

    }

    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

                val application =
                    extras[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY]
                val betDao = (application as App).appDatabse.betDao()
                val repository = BetRepository.Companion.getInstance(betDao = betDao)

                return MegaSenaViewModel(betRepository = repository) as T
            }
        }
    }
}