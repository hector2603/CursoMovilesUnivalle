package com.example.arquitecturamvvm.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.arquitecturamvvm.ui.theme.Purple80

@Composable
fun Home(){

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp), color = Purple80
    ) {
        Text(text = "home")
    }
}