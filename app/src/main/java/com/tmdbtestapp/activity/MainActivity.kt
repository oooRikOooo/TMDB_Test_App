package com.tmdbtestapp.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.tmdbtestapp.navigation.AppNavigation
import com.tmdbtestapp.theme.TMDBTestAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TMDBTestAppTheme {
                Content()
            }
        }
    }

    @Composable
    private fun Content() {
        val navController = rememberNavController()

        Box(Modifier.fillMaxSize()) {
            AppNavigation(
                navController = navController
            )
        }
    }
}