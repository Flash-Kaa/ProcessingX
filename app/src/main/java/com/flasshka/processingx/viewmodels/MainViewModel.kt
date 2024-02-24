package com.flasshka.processingx.viewmodels

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.flasshka.processingx.models.PermissionUtils
import com.flasshka.processingx.views.UI

class MainViewModel : ViewModel() {
    private val storageRequestCode = 101

    private val ui = UI(this)
    var code by mutableStateOf("")


    @Composable
    fun EditText() {
        ui.EditText()
    }

    @Composable
    fun StartStopAndRestartMenu() {
        ui.StartStopAndRestartMenu()
    }

    @Composable
    fun UIWithScaffold(component: ComponentActivity) {
        ui.UIWithScaffold(component)
    }

    @Composable
    fun Permission(component: ComponentActivity) {
        var haveMemoryPermission by remember {
            mutableStateOf(
                PermissionUtils.haveMemoryPermission(component)
            )
        }

        if (!haveMemoryPermission) {
            ui.Permission {
                PermissionUtils.requestPermissions(component, storageRequestCode)
                haveMemoryPermission = PermissionUtils.haveMemoryPermission(component)
            }
        }
    }

    fun startProgram() {
        // use field `code` to void ure function
    }

    fun stopProgram() {

    }
}