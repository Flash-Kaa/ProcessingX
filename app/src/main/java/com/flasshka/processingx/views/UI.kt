package com.flasshka.processingx.views

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flasshka.processingx.R
import com.flasshka.processingx.viewmodels.MainViewModel
import kotlinx.coroutines.launch

class UI(private val mainVM: MainViewModel) {
    @Composable
    fun EditText() {
        TextField(
            value = mainVM.code,
            onValueChange = { mainVM.code = it },
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            visualTransformation = ColorTextTransformation()
        )
    }

    @Composable
    fun StartStopAndRestartMenu() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    mainVM.stopProgram()
                    mainVM.startProgram()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_autorenew_24),
                    contentDescription = "restart"
                )
            }

            Button(onClick = { mainVM.startProgram() }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_chevron_right_24),
                    contentDescription = "start"
                )
            }

            Button(onClick = { mainVM.stopProgram() }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_check_box_outline_blank_24),
                    contentDescription = "stop"
                )
            }
        }
    }

    @Composable
    fun UIWithScaffold(component: ComponentActivity) {
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()

        Scaffold(
            scaffoldState = scaffoldState,
            drawerContent = {
                Text("Файл 1", fontSize = 24.sp)
                Text("Файл 2", fontSize = 24.sp)
                Text("Файл 3", fontSize = 24.sp)
            }
        ) {
            Button(
                modifier = Modifier.padding(it),
                onClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }) {
                Icon(
                    painter = painterResource(R.drawable.baseline_dehaze_24),
                    contentDescription = "open panel"
                )
            }

            Column(Modifier.fillMaxSize()) {
                mainVM.StartStopAndRestartMenu()
                mainVM.EditText()
            }

            mainVM.Permission(component)
        }
    }

    @Composable
    fun Permission(onClick: () -> Unit) {
        AlertDialog(
            onDismissRequest = { /*NOTHING*/ },
            title = { Text("Memory permission") },
            text = { Text("The application needs permission to use memory") },
            buttons = {
                Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(text = "OK")
                }
            }
        )
    }
}