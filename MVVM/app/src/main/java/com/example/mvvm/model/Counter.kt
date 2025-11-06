package com.example.mvvm.model

data class Counter(val value: Int = 0) {

    fun increment() = copy( value = value +1  )
    fun decrement() = copy(value =  value -1)
    fun reset() = copy(value = 0)
}