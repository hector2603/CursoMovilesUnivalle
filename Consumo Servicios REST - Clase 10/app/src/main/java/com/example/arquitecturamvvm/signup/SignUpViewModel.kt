package com.example.arquitecturamvvm.signup

import android.app.Application
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.arquitecturamvvm.repository.User
import com.example.arquitecturamvvm.repository.UserDatabase
import com.example.arquitecturamvvm.repository.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class SignUpViewModel(application: Application) : ViewModel() {

    private var auth: FirebaseAuth = Firebase.auth

    private val _user = MutableLiveData<String>()
    val user: LiveData<String> = _user

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _isButtonEnable = MutableLiveData<Boolean>()
    val isButtonEnable: LiveData<Boolean> = _isButtonEnable

    private val userRepository: UserRepository
    val allUsers: LiveData<List<User>>

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        userRepository = UserRepository(userDao)
        allUsers = userRepository.allUsers
    }


    fun onRegisterChanged(user: String, password: String, name: String) {
        _user.value = user
        _password.value = password
        _name.value = name
        _isButtonEnable.value = enableButton(user, password, name)
    }

    fun enableButton(email: String, password: String, name: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 6 && name.length > 3

    fun signUpUser(home: () -> Unit) {
        _isButtonEnable.value = false
        auth.createUserWithEmailAndPassword(_user.value!!, _password.value!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Una vez que el usuario se ha registrado correctamente, actualiza el nombre en el perfil
                    val firebaseUser = auth.currentUser
                    val profileUpdates = userProfileChangeRequest {
                        displayName = _name.value
                    }
                    firebaseUser?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { profileTask ->
                            if (profileTask.isSuccessful) {
                                // El perfil se ha actualizado correctamente
                                Log.d("SignUp", "User profile updated.")

                                // Insertar el usuario en la base de datos SQLite
                                val user = User(0, firebaseUser.uid, _user.value!!, _name.value!!)
                                CoroutineScope(Dispatchers.IO).launch {
                                    userRepository.insert(user)
                                }

                                // Llama a la función home para ir a la pantalla principal
                                home()
                            } else {
                                // Error al actualizar el perfil
                                Log.e("SignUp", "Error updating profile: ${profileTask.exception}")
                            }
                        }
                } else {
                    // Error al crear la cuenta
                    Log.e("SignUp", "createUserWithEmailAndPassword failed: ${task.exception}")
                }
            }
    }

}