package com.marcelo.loteriadossonhos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.marcelo.loteriadossonhos.nav.AppNavHost
import com.marcelo.loteriadossonhos.ui.theme.LoteriaDosSonhosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LoteriaDosSonhosTheme {
                AppNavHost()
            }
        }
    }
}