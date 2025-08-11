package com.marcelo.loteriadossonhos.view.HomeScreen

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
import com.marcelo.loteriadossonhos.R
import com.marcelo.loteriadossonhos.component.LotteryHeaderItemTypeCustom
import com.marcelo.loteriadossonhos.model.MainItem
import com.marcelo.loteriadossonhos.ui.theme.Blue
import com.marcelo.loteriadossonhos.ui.theme.Green
import com.marcelo.loteriadossonhos.ui.theme.White

@Composable
fun HomeScreen(onClick: (MainItem) -> Unit) {
    val mainItems = mutableListOf(
        MainItem(1, "Mega Sena", Green, R.drawable.trevo),
        MainItem(2, "Quina", Blue, R.drawable.trevo_blue)
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyVerticalGrid(
            verticalArrangement = Arrangement.SpaceAround,
            columns = GridCells.Fixed(2)
        ) {
            items(mainItems) {
                LotteryItem(it) {
                    onClick(it)
                }
            }

        }
    }
}

@Composable
fun LotteryItem(
    item: MainItem,
    onClick: () -> Unit,
) {
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

            LotteryHeaderItemTypeCustom(
                backgroundColor = item.color,
                colorLabel = White,
                labelBet = item.name,
                icon = item.icon
            )
        }
    }

}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        onClick = {}
    )
}