package com.tmdbtestapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.tmdbtestapp.common.navigation.NavRoute
import com.tmdbtestapp.ui.screen.detail.DetailScreen
import com.tmdbtestapp.ui.screen.detail.DetailViewModel
import com.tmdbtestapp.ui.screen.home.HomeScreen
import com.tmdbtestapp.ui.screen.player.PlayerScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.HomeNavRoute
    ) {
        composable<NavRoute.HomeNavRoute> {
            HomeScreen {
                navController.navigate(it)
            }
        }

        composable<NavRoute.DetailNavRoute> { entry ->
            val detail = entry.toRoute<NavRoute.DetailNavRoute>()

            val viewModel = hiltViewModel<DetailViewModel, DetailViewModel.DetailViewModelFactory> {
                it.create(detail.movieId)
            }

            DetailScreen(
                viewModel = viewModel,
                navigateTo = { navController.navigate(it) })
        }

        composable<NavRoute.PlayerNavRoute> {
            PlayerScreen()
        }
    }
}