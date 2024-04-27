package com.example.arquitecturamvvm.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.arquitecturamvvm.ui.theme.Purple80
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Home(navController: NavHostController) {
    val auth = FirebaseAuth.getInstance()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp), color = Purple80
    ) {
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
    }
}