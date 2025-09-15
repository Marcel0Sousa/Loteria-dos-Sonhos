package com.marcelo.loteriadossonhos.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.marcelo.loteriadossonhos.routes.AppRouter
import com.marcelo.loteriadossonhos.view.betListDetailScreen.BetListDatailContent
import com.marcelo.loteriadossonhos.view.homeScreen.HomeScreen
import com.marcelo.loteriadossonhos.view.megaSenaScreen.MegaSenaScreen
import com.marcelo.loteriadossonhos.view.quinaScreen.QuinaScreen

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRouter.HOME.route
    ) {
        composable(AppRouter.HOME.route) {
            HomeScreen { item ->
                val router = when (item.id) {
                    1 -> AppRouter.MEGA_SENA
                    2 -> AppRouter.QUINA
                    else -> AppRouter.HOME
                }
                navController.navigate(router.route)
            }
        }

        composable(AppRouter.MEGA_SENA.route) {
            MegaSenaScreen(
                onMenuClick = {megasena ->
                    navController.navigate(AppRouter.BET_LIST_DETAIL.route + "/$megasena")
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppRouter.QUINA.route) {
            QuinaScreen(
                onMenuClick = {quina ->
                    navController.navigate(AppRouter.BET_LIST_DETAIL.route + "/$quina")
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = AppRouter.BET_LIST_DETAIL.route + "/{type}",
            arguments = listOf(
                navArgument("type") {
                    type = NavType.StringType
                })
        ) {
            val type = it.arguments?.getString("type") ?: throw Exception("Erro, type not found!")
            BetListDatailContent(
                type = type,
                onBackClick = {navController.popBackStack()}
            )
        }
    }
}