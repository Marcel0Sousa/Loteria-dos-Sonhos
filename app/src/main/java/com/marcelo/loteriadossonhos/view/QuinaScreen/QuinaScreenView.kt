package com.marcelo.loteriadossonhos.view.QuinaScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marcelo.loteriadossonhos.R
import com.marcelo.loteriadossonhos.component.CustomTextField
import com.marcelo.loteriadossonhos.component.LotteryHeaderItemTypeCustom
import com.marcelo.loteriadossonhos.data.AppDatabase
import com.marcelo.loteriadossonhos.data.Bet
import com.marcelo.loteriadossonhos.routes.AppRouter

@Composable
fun QuinaScreen(onClick: (String) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val db = AppDatabase.getInstance(context)
    val bet = Bet(
        type = "Mega Sena",
        numbers = "34,2,55,24,10"
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        var numero by remember { mutableStateOf("") }
        var resultado by remember { mutableStateOf("") }
        val context = LocalContext.current

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 20.dp)
        ) {
            LotteryHeaderItemTypeCustom(
                labelBet = "Quina",
                icon = R.drawable.trevo_blue
            )

            CustomTextField(
                value = numero,
                placeholder = R.string.numero_quina,
                imeAction = ImeAction.Done
            ) {
                if (it.length <= 5) {
                    numero = it
                }
            }

            Button(
                onClick = {
                    onClick(AppRouter.QUINA.route)
                }
            ) {
                Text(
                    text = "Apostar",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.padding(top = 20.dp, bottom = 20.dp))
            Text(
                text = resultado
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun QuinaScreenPreviw() {
    QuinaScreen{}
}