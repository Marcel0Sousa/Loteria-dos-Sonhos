package com.marcelo.loteriadossonhos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.marcelo.loteriadossonhos.routes.AppRouter
import com.marcelo.loteriadossonhos.ui.theme.Green
import com.marcelo.loteriadossonhos.ui.theme.LoteriaDosSonhosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoteriaDosSonhosTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = AppRouter.HOME.route) {
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
            Image(
                painter =
                    painterResource(R.drawable.trevo),
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp)
                    .padding(10.dp)
            )

            Text(
                text = name,
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 6.dp)
                    .align(Alignment.CenterHorizontally)
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

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.trevo),
                contentDescription = stringResource(R.string.label_trevo),
                modifier = Modifier
                    .size(100.dp)
                    .padding(10.dp)
            )

            Text(
                text = "Mega Senha",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Text(
                text = stringResource(R.string.label_announcement),
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(10.dp)
            )

            OutlinedTextField(
                value = quantityNumbers,
                maxLines = 1,
                placeholder = {
                    Text(stringResource(R.string.label_mega_rule))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                onValueChange = {
                    if (it.length < 3) {
                        quantityNumbers = validateInput(it)
                    }
                }
            )

            OutlinedTextField(
                value = quantityBets,
                maxLines = 1,
                placeholder = {
                    Text(stringResource(R.string.label_bets))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                onValueChange = {
                    if (it.length < 3) {
                        quantityBets = validateInput(it)
                    }
                }
            )

            OutlinedButton(
                onClick = {

                }
            ) {
                Text(stringResource(R.string.label_bets_generate))
            }
        }
    }
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