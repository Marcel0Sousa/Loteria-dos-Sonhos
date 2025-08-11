package com.marcelo.loteriadossonhos.view.QuinaScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun QuinaScreen(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxSize(),
        color = Color.Blue
    ) {

    }
}

@Preview(showSystemUi = true)
@Composable
private fun QuinaScreenPreviw() {
    QuinaScreen()
}