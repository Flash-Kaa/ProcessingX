package com.flasshka.processingx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.flasshka.processingx.models.PermissionUtils
import com.flasshka.processingx.ui.theme.ProcessingXTheme
import com.flasshka.processingx.views.TestField

class MainActivity : ComponentActivity() {
    private val storageRequestCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var haveMemoryPermission by mutableStateOf(
            PermissionUtils.haveMemoryPermission(this)
        )

        setContent {
            ProcessingXTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TestField.EditText()
                }

                if(!haveMemoryPermission) {
                    AlertDialog(
                        onDismissRequest = { /*NOTHING*/ },
                        title = {Text("Memory permission")},
                        text = {Text("The application needs permission to use memory")},
                        buttons = {
                            Button(
                                onClick = {
                                    PermissionUtils.requestPermissions(this, storageRequestCode)
                                    haveMemoryPermission = PermissionUtils.haveMemoryPermission(this)
                                },
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
        }
    }
}