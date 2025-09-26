package com.marcelo.loteriadossonhos.view.megaSenaScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.marcelo.loteriadossonhos.R
import com.marcelo.loteriadossonhos.component.AutoTextDropDown
import com.marcelo.loteriadossonhos.component.CustomTextField
import com.marcelo.loteriadossonhos.component.LotteryHeaderItemTypeCustom
import com.marcelo.loteriadossonhos.routes.AppRouter
import com.marcelo.loteriadossonhos.ui.theme.Green
import com.marcelo.loteriadossonhos.ui.theme.White
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MegaSenaScreen(
    onMenuClick: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    val snackbarHostState by remember { mutableStateOf(SnackbarHostState()) }
    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Mega Sena",
                            color = White
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Green
                    ),
                    actions = {
                        IconButton(onClick = {
                            onMenuClick(AppRouter.MEGA_SENA.route)
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.List,
                                tint = White,
                                contentDescription = null
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = onBackClick
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                tint = White,
                                contentDescription = "Voltar"
                            )
                        }
                    }
                )
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { paddingValues ->
            MegaSenaContent(
                modifier = Modifier.padding(top = paddingValues.calculateTopPadding()),
                errorMessage = { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }
            )
        }
    }
}

@Composable
fun MegaSenaContent(
    modifier: Modifier = Modifier,
    errorMessage: (String) -> Unit,
    megaSenaViewModel: MegaSenaViewModel = viewModel(factory = MegaSenaViewModel.factory),
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    val quantityNumbers = megaSenaViewModel.quantityNumbers.collectAsState().value
    val quantityBets = megaSenaViewModel.quantityBets.collectAsState().value
    val result = megaSenaViewModel.result.collectAsState().value
    val showAlertDialog = megaSenaViewModel.showAlertDialog.collectAsState().value
    val message = megaSenaViewModel.errorMessage.collectAsState().value

    val scrollState = rememberScrollState()
    val rules = stringArrayResource(R.array.arrat_bet_rules)
    var selectedItem by remember { mutableStateOf(rules.first()) }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .verticalScroll(scrollState)
    ) {

        LotteryHeaderItemTypeCustom(
            labelBet = "Mega Sena"
        )

        Text(
            text = stringResource(R.string.label_announcement),
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(20.dp)
        )

        CustomTextField(
            value = quantityNumbers,
            placeholder = R.string.label_mega_rule
        ) {
            megaSenaViewModel.updateQuantityNumbers(it)
        }

        CustomTextField(
            value = quantityBets,
            placeholder = R.string.label_bets,
            imeAction = ImeAction.Done
        ) {
            megaSenaViewModel.updateQuantityBets(it)
        }

        Column(Modifier.width(280.dp)) {
            AutoTextDropDown(
                label = stringResource(R.string.bet_rules),
                initial = selectedItem,
                list = rules.toList()
            )
        }

        OutlinedButton(
            enabled = quantityBets.isNotEmpty() && quantityNumbers.isNotEmpty(),
            onClick = {

                val quantityBetsInt = quantityBets.toIntOrNull() ?: 0
                val quantityNumbersInt = quantityNumbers.toIntOrNull() ?: 0

                if (quantityBetsInt < 1 || quantityBetsInt > 10) {
                    errorMessage("Número máximo de apostas não permitido")
                    return@OutlinedButton
                }

                if (quantityNumbersInt < 6 || quantityNumbersInt > 15) {
                    errorMessage("O número deve ser entre 6 e 15")
                    return@OutlinedButton
                }

                val rule = rules.indexOf(selectedItem)
                megaSenaViewModel.updateNumbers(rule)
                showAlertDialog
                keyboardController?.hide()
            }
        ) {
            Text(stringResource(R.string.label_bets_generate))
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 20.dp)
        ) {
            Text(result)
        }
    }

    if (showAlertDialog) {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {
                TextButton(
                    onClick = {
                        megaSenaViewModel.dismissAlertDialog()
                    }
                ) {
                    Text(text = stringResource(id = android.R.string.ok))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        megaSenaViewModel.saveNumberBet(AppRouter.MEGA_SENA.route)

                    }
                ) {
                    Text(text = stringResource(id = R.string.save))
                }
            },
            title = {
                Text(text = stringResource(R.string.app_name))
            },
            text = {
                Text(text = stringResource(R.string.good_luck))
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun FormScreenPreview() {
    MegaSenaScreen(
        onMenuClick = {},
        onBackClick = {}
    )
}
