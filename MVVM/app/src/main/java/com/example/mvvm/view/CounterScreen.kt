package com.example.mvvm.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mvvm.viewmodel.CounterViewModel

@Composable
fun counterScreen( counterViewModel: CounterViewModel ){
    val counter = counterViewModel.counter
    Column (modifier = Modifier.fillMaxSize().padding(16.dp) ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text( text = counter.value.toString() )
        Row( modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            Button( onClick = {counterViewModel.decrement()}) { Text("-") }
            Button(onClick = {counterViewModel.reset()}) { Text("Reset") }
            Button(onClick = {counterViewModel.increment()}) { Text("+")  }
        }
    }
}