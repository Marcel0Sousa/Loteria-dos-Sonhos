package com.marcelo.loteriadossonhos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marcelo.loteriadossonhos.component.LotteryHeaderItemTypeCustom
import com.marcelo.loteriadossonhos.model.MainItem
import com.marcelo.loteriadossonhos.nav.AppNavHost
import com.marcelo.loteriadossonhos.ui.theme.Blue
import com.marcelo.loteriadossonhos.ui.theme.Green
import com.marcelo.loteriadossonhos.ui.theme.LoteriaDosSonhosTheme
import com.marcelo.loteriadossonhos.ui.theme.White

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