package com.marcelo.loteriadossonhos.view.quinaScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Button
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
import com.marcelo.loteriadossonhos.routes.AppRouter
import com.marcelo.loteriadossonhos.ui.theme.BlueLigth
import com.marcelo.loteriadossonhos.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuinaScreen(onClick: (String) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Quina",
                            color = White
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = BlueLigth
                    ),
                    actions = {
                        IconButton(
                          onClick = {
                              onClick(AppRouter.QUINA.route)
                          }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.List,
                                tint = White,
                                contentDescription = "")
                        }
                    }
                )
            }
        ) { paddingValues ->
            QuinaContentScreen(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())) {
                onClick(it)
            }
        }
    }
}

@Composable
fun QuinaContentScreen(modifier: Modifier, onClick: (String) -> Unit) {
    var numero by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
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
        Spacer(modifier = modifier.padding(top = 20.dp, bottom = 20.dp))
        Text(
            text = resultado
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun QuinaScreenPreview() {
    QuinaScreen {}
}