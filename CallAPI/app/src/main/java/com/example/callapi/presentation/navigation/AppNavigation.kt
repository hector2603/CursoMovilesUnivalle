package com.example.callapi.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.callapi.presentation.characters.CharactersScreen
import com.example.callapi.presentation.characters.CharactersViewModel
import com.example.callapi.presentation.main.MainScreen

object Routes {
    const val MAIN = "main"
    const val CHARACTERS = "characters"
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
){
    NavHost(
        navController = navController,
        startDestination = Routes.MAIN
    ) {
        composable(Routes.MAIN) {
            MainScreen(
                isDarkTheme = isDarkTheme,
                onThemeToggle = onToggleTheme,
                onNavigateToCharacters = {
                    navController.navigate(Routes.CHARACTERS)
                }
            )
        }
        composable(Routes.CHARACTERS) {
            CharactersScreen(navController)
        }
    }
}