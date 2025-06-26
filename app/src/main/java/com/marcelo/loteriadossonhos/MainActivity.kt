package com.marcelo.loteriadossonhos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.marcelo.loteriadossonhos.component.CustomTextField
import com.marcelo.loteriadossonhos.component.LotteryHeaderItemCustom
import com.marcelo.loteriadossonhos.routes.AppRouter
import com.marcelo.loteriadossonhos.ui.theme.Green
import com.marcelo.loteriadossonhos.ui.theme.LoteriaDosSonhosTheme
import com.marcelo.loteriadossonhos.ui.theme.White
import kotlinx.coroutines.launch
import java.util.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoteriaDosSonhosTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = AppRouter.HOME.route
                ) {
                    composable(AppRouter.HOME.route) {
                        HomeScreen {
                            navController.navigate(route = AppRouter.FORM.route)
                        }
                    }

                    composable(AppRouter.FORM.route) {
                        FormScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(onClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LotteryItem("Mega Sena", onClick)
    }
}

@Composable
fun LotteryItem(name: String, onClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        modifier = Modifier
            .wrapContentSize()
            .clickable {
                onClick()
            }
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(Green)
        ) {

            LotteryHeaderItemCustom(
                backgroundColor = Green,
                colorLabel = White,
                labelBet = name
            )
        }
    }

}

@Composable
private fun FormScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        var quantityNumbers by remember { mutableStateOf("") }
        var quantityBets by remember { mutableStateOf("") }
        val snackbarHostState by remember { mutableStateOf(SnackbarHostState()) }
        var result by remember { mutableStateOf("") }
        val coroutineScope = rememberCoroutineScope()
        val keyboardController = LocalSoftwareKeyboardController.current

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 20.dp)
        ) {

            LotteryHeaderItemCustom(
                labelBet = "Mega Sena"
            )

            Text(
                text = stringResource(R.string.label_announcement),
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(10.dp)
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
                    for (index in 1..quantityBets.toInt()) {
                        result += "[ $index ] "
                        result += numberGenerator(quantityNumbers)
                        result += "\n\n"
                    }
                    keyboardController?.hide()
                }
            ) {
                Text(stringResource(R.string.label_bets_generate))
            }

            Text(result)
        }

        Box{
            SnackbarHost(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 50.dp),
                hostState = snackbarHostState
            )
        }
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


@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_5")
@Composable
fun GreetingPreview() {
    LoteriaDosSonhosTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LotteryItem("Mega Sena") {

            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_5")
@Composable
private fun FormScreenPreview() {
    FormScreen()
}