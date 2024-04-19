package com.example.jetpackcomposeejemploclase

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.jetpackcomposeejemploclase.ui.theme.JetpackComposeEjemploClaseTheme
import com.example.jetpackcomposeejemploclase.ui.theme.Purple80


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeEjemploClaseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
            .fillMaxSize()
            .padding(50.dp),
        color = Purple80

    )
    Text(
        text = "hhehhhhhh $name!",
        modifier = modifier
            .fillMaxSize()
            .padding(50.dp),
        color = Purple80

    )
}

@Composable
fun MyBox(name: String, modifier: Modifier) {
    Box(modifier, contentAlignment = Alignment.Center) {
        Text(text = name)
    }
}

@Composable
fun MyColumn() {
    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        MyBox(
            name = "Caja 1",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Green)
                .height(100.dp)
        )
        MyBox(
            name = "Caja 2",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue)
                .height(100.dp)
        )
        MyBox(
            name = "Caja 3", modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
                .height(100.dp)
        )
    }
}

@Composable
fun MyRow() {
    Row(modifier = Modifier, horizontalArrangement = Arrangement.Center) {
        MyBox(
            name = "Caja 1",
            modifier = Modifier
                .width(100.dp)
                .background(Color.Green)
                .height(100.dp)
        )
        MyBox(
            name = "Caja 2",
            modifier = Modifier
                .width(100.dp)
                .background(Color.Blue)
                .height(100.dp)
        )
        MyBox(
            name = "Caja 3", modifier = Modifier
                .width(100.dp)
                .background(Color.Red)
                .height(100.dp)
        )
    }
}

@Composable
fun MyComplex() {
    Column(modifier = Modifier.fillMaxSize()) {
        MyRow()
        Spacer(modifier = Modifier.height(10.dp))
        MyRow()
        Box() {
            Text(text = "Otra Caja")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    MyButton()
}


@Composable
fun LayoutPorRestricciones() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (red, blue, yellow, magenta) = createRefs()
        val startGuide = createGuidelineFromStart(0.1f)
        Box(modifier = Modifier
            .size(125.dp)
            .background(Color.Red)
            .constrainAs(red) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            })
        Box(modifier = Modifier
            .size(125.dp)
            .background(Color.Blue)
            .constrainAs(blue) {
                start.linkTo(red.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            })
        Box(modifier = Modifier
            .size(125.dp)
            .background(Color.Yellow)
            .constrainAs(yellow) {
                top.linkTo(blue.bottom)
                start.linkTo(red.end)
            })
        Box(modifier = Modifier
            .size(125.dp)
            .background(Color.Magenta)
            .constrainAs(magenta) {
                start.linkTo(startGuide)
            })
    }
}

@Composable
fun MyButton() {
    var counter = rememberSaveable {
        mutableStateOf(0);
    }
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Text(text = "Counter: ${counter.value}")
        Button(onClick = {
            Log.i("Counter", counter.toString())
            counter.value ++

        }) {
            Text(text = "Click me")
        }
    }
}


