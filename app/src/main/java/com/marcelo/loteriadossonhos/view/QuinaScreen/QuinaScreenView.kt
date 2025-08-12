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

@Composable
fun QuinaScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
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
                    resultado = numero
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
    QuinaScreen()
}