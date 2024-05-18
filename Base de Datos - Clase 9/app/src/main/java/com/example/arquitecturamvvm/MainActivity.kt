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
import com.example.arquitecturamvvm.signup.SignUp
import com.example.arquitecturamvvm.signup.SignUpViewModel
import com.example.arquitecturamvvm.ui.theme.ArquitecturaMVVMTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {

    private val auth: FirebaseAuth by lazy { Firebase.auth }

    override fun onCreate(savedInstanceState: Bundle?) {
        val currentUser = auth.currentUser
        var starDestination : String = if (currentUser != null) "home" else "login"
        super.onCreate(savedInstanceState)
        setContent {
            ArquitecturaMVVMTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = starDestination){
                    composable("login"){Login(Modifier, LoginViewModel(), navController ) }
                    composable("sign-up"){SignUp(Modifier, SignUpViewModel(application), navController ) }
                    composable("home"){Home(navController)}
                }
            }
        }
    }
}
