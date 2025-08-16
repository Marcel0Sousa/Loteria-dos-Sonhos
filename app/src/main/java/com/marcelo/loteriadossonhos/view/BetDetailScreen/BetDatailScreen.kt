package com.marcelo.loteriadossonhos.view.BetDetailScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BetDatailScreen(type: String) {
    Text("Tipo de aposta: $type")
}

@Preview(showBackground = true)
@Composable
private fun betDatailsScreenPreview() {
    BetDatailScreen("megasena")
}