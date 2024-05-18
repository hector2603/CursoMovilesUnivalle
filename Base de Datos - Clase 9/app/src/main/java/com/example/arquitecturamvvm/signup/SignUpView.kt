package com.example.arquitecturamvvm.signup

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import com.example.arquitecturamvvm.R
import com.example.arquitecturamvvm.ui.theme.Purple80
import com.example.arquitecturamvvm.ui.theme.Typography
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SignUp(
    modifier: Modifier = Modifier,
    signUpViewModel: SignUpViewModel,
    navController: NavHostController,
) {

    val user:String by signUpViewModel.user.observeAsState(initial = "")
    val password:String by signUpViewModel.password.observeAsState(initial = "")
    val name:String by signUpViewModel.name.observeAsState(initial = "")
    val isButtonEnable:Boolean by signUpViewModel.isButtonEnable.observeAsState(initial = false)
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current
    signUpViewModel.allUsers.observe(lifecycleOwner, Observer { users ->
        users.forEach { user ->
            Log.d("SignUpView", "User: $user")
        }
    })

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp), color = Purple80
    ) {
        Box(
            modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.fondo), contentScale = ContentScale.FillBounds
                )
        ) {
            ConstraintLayout(modifier = modifier.fillMaxSize()) {
                val (logo, title, userField, passwordField, nameField, button, buttonLogin) = createRefs()

                Image(painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo image",
                    modifier = Modifier
                        .constrainAs(logo) {
                            top.linkTo(parent.top, margin = 50.dp)
                            centerHorizontallyTo(parent)
                        }
                        .size(184.dp))

                Text(text = "MyList",
                    style = Typography.titleLarge,
                    modifier = Modifier.constrainAs(title) {
                        top.linkTo(logo.bottom, margin = 56.dp)
                        centerHorizontallyTo(parent)
                    })

                TextField(
                    value = user,
                    onValueChange = { userText -> signUpViewModel.onRegisterChanged(userText, password,name) },
                    label = { Text("user") },
                    singleLine = true,
                    modifier = Modifier
                        .constrainAs(userField) {
                            top.linkTo(title.bottom, margin = 28.dp)
                            centerHorizontallyTo(parent)
                        }
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.baseline_person_24),
                            contentDescription = null
                        )
                    },
                )

                TextField(
                    value = name,
                    onValueChange = { nameText -> signUpViewModel.onRegisterChanged(user, password, nameText) },
                    label = { Text("name") },
                    singleLine = true,
                    modifier = Modifier
                        .constrainAs(nameField) {
                            top.linkTo(userField.bottom, margin = 19.dp)
                            centerHorizontallyTo(parent)
                        }
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.baseline_person_24),
                            contentDescription = null
                        )
                    },
                )

                TextField(
                    value = password,
                    onValueChange = { passwordText -> signUpViewModel.onRegisterChanged(user, passwordText,name) },
                    singleLine = true,
                    label = { Text("Password") },
                    modifier = Modifier
                        .constrainAs(passwordField) {
                            top.linkTo(nameField.bottom, margin = 19.dp)
                            centerHorizontallyTo(parent)
                        }
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.baseline_password_24),
                            contentDescription = null
                        )
                    },
                    visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { passwordHidden = !passwordHidden }) {
                            val visibilityIcon =
                                if (passwordHidden) painterResource(R.drawable.baseline_visibility_24) else painterResource(
                                    R.drawable.baseline_visibility_off_24
                                )
                            val description =
                                if (passwordHidden) "Show password" else "Hide password"
                            Icon(painter = visibilityIcon, contentDescription = description)
                        }
                    })

                Button(onClick = {
                        signUpViewModel.signUpUser{ navController.navigate("home") }
                    },
                    modifier = Modifier.constrainAs(button) {
                        top.linkTo(passwordField.bottom, margin = 35.dp)
                        centerHorizontallyTo(parent)
                    },
                    enabled = isButtonEnable) {
                    Text("Crear Cuenta")
                }

                TextButton(onClick = {
                    navController.navigate("login")
                },
                    modifier = Modifier.constrainAs(buttonLogin) {
                        top.linkTo(button.bottom, margin = 10.dp)
                        centerHorizontallyTo(parent)
                    }) {
                    Text("Volver")
                }
            }
        }

    }
}