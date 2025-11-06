package com.example.callapi

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.callapi.presentation.main.MainViewModel
import com.example.callapi.presentation.navigation.AppNavigation
import com.example.callapi.ui.theme.CallAPITheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MainViewModel = viewModel()
            val isDarkTheme by viewModel.isDarkTheme.collectAsState()
            val navController = rememberNavController()

            CallAPITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    AppNavigation(navController = navController,
                        isDarkTheme = isDarkTheme,
                        onToggleTheme = { viewModel.toggleTheme()})
                }
            }
        }
    }
}

