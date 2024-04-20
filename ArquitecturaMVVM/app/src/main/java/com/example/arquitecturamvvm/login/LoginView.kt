package com.example.arquitecturamvvm.login

import android.widget.Toast
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.arquitecturamvvm.R
import com.example.arquitecturamvvm.ui.theme.Purple80
import com.example.arquitecturamvvm.ui.theme.Typography

@Composable
fun Login(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel,
    navController: NavHostController
) {

    val user:String by loginViewModel.user.observeAsState(initial = "")
    val password:String by loginViewModel.password.observeAsState(initial = "")
    val isLoginEnable:Boolean by loginViewModel.isLoginEnable.observeAsState(initial = false)
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    val context = LocalContext.current

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
                val (logo, title, userField, passwordField, button) = createRefs()

                Image(painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo image",
                    modifier = Modifier
                        .constrainAs(logo) {
                            top.linkTo(parent.top, margin = 175.dp)
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
                    onValueChange = { userText -> loginViewModel.onLoginChanged(userText, password) },
                    label = { Text("Login") },
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
                    value = password,
                    onValueChange = { passwordText -> loginViewModel.onLoginChanged(user, passwordText) },
                    singleLine = true,
                    label = { Text("Password") },
                    modifier = Modifier
                        .constrainAs(passwordField) {
                            top.linkTo(userField.bottom, margin = 19.dp)
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
                    navController.navigate("home")
                },
                modifier = Modifier.constrainAs(button) {
                    top.linkTo(passwordField.bottom, margin = 35.dp)
                    centerHorizontallyTo(parent)
                },
                    enabled = isLoginEnable) {
                    Text("Acceder")
                }
            }
        }

    }
}