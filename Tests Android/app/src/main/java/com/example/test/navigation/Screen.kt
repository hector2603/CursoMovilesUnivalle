package com.example.test.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Users : Screen("users")
    object Profile : Screen("profile/{userId}") {
        fun createRoute(userId: Int) = "profile/$userId"
    }
}
