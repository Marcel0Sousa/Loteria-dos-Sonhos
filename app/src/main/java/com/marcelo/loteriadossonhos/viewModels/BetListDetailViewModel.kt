package com.marcelo.loteriadossonhos.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.marcelo.loteriadossonhos.App
import com.marcelo.loteriadossonhos.data.Bet
import com.marcelo.loteriadossonhos.data.BetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BetListDetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val betRepository: BetRepository
) : ViewModel() {

    private val _bet = MutableStateFlow<List<Bet>>(emptyList())
    val bet: StateFlow<List<Bet>> = _bet.asStateFlow()

    init {
        viewModelScope.launch {
            val type = savedStateHandle.get<String>("type") ?: throw Exception("Erro, tipy not found!")
            val bets = betRepository.getBets(type)
            _bet.value = bets
        }
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

                val application = extras[APPLICATION_KEY]
                val betDao = (application as App).appDatabse.betDao()
                val repository = BetRepository.getInstance(betDao = betDao)

                val savedStateHandle = extras.createSavedStateHandle()
                return BetListDetailViewModel(savedStateHandle, repository) as T
            }
        }
    }
}