package com.example.arquitecturamvvm.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.arquitecturamvvm.ui.theme.Purple80
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Home(){
    val currentUser = FirebaseAuth.getInstance().currentUser

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp), color = Purple80
    ) {
        Column {
            Text(text = "home")
            if (currentUser != null) {
                Text(text = "Email: ${currentUser.email}")
            }
            if (currentUser != null) {
                Text(text = "Name: ${currentUser.displayName}")
            }
        }
    }
}