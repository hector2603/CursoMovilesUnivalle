package com.example.mvvm.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mvvm.model.Counter

class CounterViewModel : ViewModel() {

    var counter by mutableStateOf(Counter())

    fun increment() {
        counter = counter.increment()
    }

    fun decrement(){
        counter = counter.decrement()
    }

    fun reset(){
        counter = counter.reset()
    }
}