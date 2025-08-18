package com.marcelo.loteriadossonhos.view.betDetailScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun BetDatailScreen(type: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Tipo de aposta: $type",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
            )
    }
}

@Preview(showBackground = true)
@Composable
private fun betDatailsScreenPreview() {
    BetDatailScreen("megasena")
}