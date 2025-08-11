package com.marcelo.loteriadossonhos.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.marcelo.loteriadossonhos.routes.AppRouter
import com.marcelo.loteriadossonhos.view.HomeScreen.HomeScreen
import com.marcelo.loteriadossonhos.view.MegaSenaScreen.MegaSenaScreen
import com.marcelo.loteriadossonhos.view.QuinaScreen.QuinaScreen

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
            MegaSenaScreen()
        }

        composable(AppRouter.QUINA.route) {
            QuinaScreen()
        }
    }
}