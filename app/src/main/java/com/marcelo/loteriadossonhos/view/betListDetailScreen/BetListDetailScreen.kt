package com.marcelo.loteriadossonhos.view.betListDetailScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.marcelo.loteriadossonhos.R
import com.marcelo.loteriadossonhos.data.Bet
import com.marcelo.loteriadossonhos.ui.theme.Gray
import com.marcelo.loteriadossonhos.ui.theme.White
import com.marcelo.loteriadossonhos.viewModels.BetListDetailViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BetListDatailScreen(
    type: String,
    bets: List<Bet> = emptyList(),
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
                    ),
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                tint = White,
                                contentDescription = "Voltar"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->

            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale("pt", "BR"))

            LazyColumn(
                modifier.padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
            ) {
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
    }
}

@Composable
fun BetListDatailContent(
    type: String,
    onBackClick: () -> Unit,
    betViewModel: BetListDetailViewModel = viewModel(factory = BetListDetailViewModel.factory),
) {

    val bets = betViewModel.bet.collectAsState(initial = emptyList()).value

    BetListDatailScreen(
        type = type,
        bets = bets,
        onBackClick = onBackClick
    )

}

@Preview(showBackground = true)
@Composable
private fun BetDatailsScreenPreview() {
    BetListDatailScreen(type = "megasena", onBackClick = {})
}