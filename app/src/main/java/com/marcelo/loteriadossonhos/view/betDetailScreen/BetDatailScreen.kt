package com.marcelo.loteriadossonhos.view.betDetailScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marcelo.loteriadossonhos.App
import com.marcelo.loteriadossonhos.R
import com.marcelo.loteriadossonhos.data.AppDatabase
import com.marcelo.loteriadossonhos.data.Bet
import com.marcelo.loteriadossonhos.ui.theme.Gray
import com.marcelo.loteriadossonhos.ui.theme.White
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BetDatailScreen(type: String) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Lista de Apostas",
                            color = White
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Gray
                    )
                )
            }
        ) { paddingValues ->
            BetDatailContentScreen(
                modifier = Modifier.padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()),
                type = type
            )
        }
    }
}

@Composable
fun BetDatailContentScreen(type: String, modifier: Modifier = Modifier) {

    // Realiza a verificação se está renderizando no Preview
    val isInPreview = LocalInspectionMode.current
    val database: AppDatabase? = if (!isInPreview) {
        // Referencia do context da class App
        (LocalContext.current.applicationContext as App).appDatabse
    } else {
        null
    }

    val bets = remember { mutableStateListOf<Bet>() }
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", java.util.Locale("pt", "BR"))

    Thread {
        val listBets = database?.betDao()?.getNumbersByType(type)
        bets.clear()
        listBets?.let { bets.addAll(it) }
    }.start()


    LazyColumn(modifier) {
        itemsIndexed(bets) { index, bet ->
            Text(
                stringResource(
                    id = R.string.list_response,
                    index,
                    simpleDateFormat.format(bet.date),
                    bet.type,
                    bet.numbers
                ),
                Modifier.padding(vertical = 10.dp, horizontal = 10.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BetDatailsScreenPreview() {
    BetDatailScreen("megasena")
}