package com.example.arquitecturamvvm.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

open class LoginViewModel :ViewModel(){

    private var auth: FirebaseAuth = Firebase.auth

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

    fun login(home: () -> Unit) {
        _isLoginEnable.value = false
        auth.signInWithEmailAndPassword(_user.value!!, _password.value!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    home()
                } else {
                    Log.e("Login", "signInWithEmailAndPassword failed: ${task.exception}")
                }
            }
    }
}