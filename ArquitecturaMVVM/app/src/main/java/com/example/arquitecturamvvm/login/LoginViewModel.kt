package com.example.arquitecturamvvm.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class LoginViewModel:ViewModel(){

    private val _user = MutableLiveData<String>()
    val user : LiveData<String> = _user

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    private val _isLoginEnable = MutableLiveData<Boolean>()
    val isLoginEnable : LiveData<Boolean> = _isLoginEnable

    fun onLoginChanged(user:String, password:String){
        _user.value = user
        _password.value = password
        _isLoginEnable.value = enableLogin(user, password )
    }

    fun enableLogin(email: String, password: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() && password. length > 6

}