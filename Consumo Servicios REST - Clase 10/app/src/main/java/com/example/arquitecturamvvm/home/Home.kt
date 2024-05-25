package com.example.arquitecturamvvm.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.arquitecturamvvm.ui.theme.Purple80
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Alignment

@Composable
fun Home(navController: NavHostController) {
    val auth = FirebaseAuth.getInstance()
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp), color = Purple80
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                Text(text = "home")
                if (auth.currentUser != null) {
                    Text(text = "Email: ${auth.currentUser!!.email}")
                }
                if (auth.currentUser != null) {
                    Text(text = "Name: ${auth.currentUser!!.displayName}")
                }

                Button(
                    onClick = {
                        auth.signOut()
                        navController.navigate("login")
                    },
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(text = "Cerrar sesión")
                }
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomEnd
            ) {
                FloatingActionButton(
                    onClick = { navController.navigate("searchmovie") },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(Icons.Filled.Search, contentDescription = "Search Content")
                }
            }
        }
    }
}