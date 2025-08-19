package com.marcelo.loteriadossonhos.view.megaSenaScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marcelo.loteriadossonhos.App
import com.marcelo.loteriadossonhos.R
import com.marcelo.loteriadossonhos.component.CustomTextField
import com.marcelo.loteriadossonhos.component.LotteryHeaderItemTypeCustom
import com.marcelo.loteriadossonhos.data.AppDatabase
import com.marcelo.loteriadossonhos.data.Bet
import com.marcelo.loteriadossonhos.routes.AppRouter
import com.marcelo.loteriadossonhos.ui.theme.Green
import com.marcelo.loteriadossonhos.ui.theme.White
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MegaSenaScreen(onClick: (String) -> Unit) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Mega Sena",
                            color = White
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Green
                    ),
                    actions = {
                        IconButton(onClick = {
                            onClick(AppRouter.MEGA_SENA.route)
                        }){
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.List,
                                tint = White,
                                contentDescription = null)
                        }
                    }
                )
            },
        ) {paddingValues ->
            MegaSenaContentScreen(modifier = Modifier.padding(top = paddingValues.calculateTopPadding()))
        }
    }
}

@Composable
fun MegaSenaContentScreen(modifier: Modifier = Modifier) {

    val isInPreview = LocalInspectionMode.current
    // Referencia do context da class App
    val database: AppDatabase? = if (!isInPreview) {
        (LocalContext.current.applicationContext as App).appDatabse
    } else {
        null
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    var quantityNumbers by remember { mutableStateOf("") }
    var quantityBets by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var showAlertDialog by remember { mutableStateOf(false) }

    // Armazenar as apostas para o DB
    val resultsToSave = remember { mutableStateListOf<String>() }

    val snackbarHostState by remember { mutableStateOf(SnackbarHostState()) }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .verticalScroll(scrollState)
    ) {

        LotteryHeaderItemTypeCustom(
            labelBet = "Mega Sena"
        )

        Text(
            text = stringResource(R.string.label_announcement),
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(20.dp)
        )

        CustomTextField(
            value = quantityNumbers,
            placeholder = R.string.label_mega_rule
        ) {
            if (it.length < 3) {
                quantityNumbers = validateInput(it)
            }
        }

        CustomTextField(
            value = quantityBets,
            placeholder = R.string.label_bets,
            imeAction = ImeAction.Done
        ) {
            if (it.length < 3) {
                quantityBets = validateInput(it)
            }
        }

        OutlinedButton(
            enabled = quantityBets.isNotEmpty() && quantityNumbers.isNotEmpty(),
            onClick = {

                if (quantityBets.toInt() < 1 || quantityBets.toInt() > 10) {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Máximo de números de apostas permitido")
                    }
                    return@OutlinedButton
                }

                if (quantityNumbers.toInt() < 6 || quantityNumbers.toInt() > 15) {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("O número deve ser entre 6 e 15")
                    }
                    return@OutlinedButton
                }

                result = ""
                resultsToSave.clear()
                for (index in 1..quantityBets.toInt()) {
                    val numberGenerator = numberGenerator(quantityNumbers)
                    resultsToSave.add(numberGenerator)

                    result += "[ $index ] "
                    result += numberGenerator
                    result += "\n\n"
                }
                showAlertDialog = true
                keyboardController?.hide()
            }
        ) {
            Text(stringResource(R.string.label_bets_generate))
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(bottom = 20.dp)
        ) {
            Text(result)
        }
    }

    Box {
        SnackbarHost(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp),
            hostState = snackbarHostState
        )
    }

    if (showAlertDialog) {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {
                TextButton(
                    onClick = {
                        showAlertDialog = false
                    }
                ) {
                    Text(text = stringResource(id = android.R.string.ok))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showAlertDialog = false

                        coroutineScope.launch(Dispatchers.IO) {
                            for (result in resultsToSave) {
                                val bet = Bet(type = AppRouter.MEGA_SENA.route, numbers = result)
                                database?.betDao()?.insert(bet)
                            }
                        }

                    }
                ) {
                    Text(text = stringResource(id = R.string.save))
                }
            },
            title = {
                Text(text = stringResource(R.string.app_name))
            },
            text = {
                Text(text = stringResource(R.string.good_luck))
            }
        )
    }
}

private fun numberGenerator(quantity: String): String {
    val numbers = mutableSetOf<Int>()
    while (true) {
        val numberRandom = Random().nextInt(60)
        numbers.add(numberRandom + 1)

        if (numbers.size == quantity.toInt()) break
    }

    return numbers.joinToString(" - ")
}

private fun validateInput(input: String): String {
    return input.filter { char ->
        char in "0123456789"
    }
}

@Preview(showSystemUi = true)
@Composable
private fun FormScreenPreview() {
    MegaSenaScreen(
        onClick = {}
    )
}