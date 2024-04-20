package com.example.arquitecturamvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.arquitecturamvvm.home.Home
import com.example.arquitecturamvvm.login.Login
import com.example.arquitecturamvvm.login.LoginViewModel
import com.example.arquitecturamvvm.ui.theme.ArquitecturaMVVMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArquitecturaMVVMTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login"){
                    composable("login"){Login(Modifier, LoginViewModel(), navController ) }
                    composable("home"){Home()}
                }
            }
        }
    }
}
