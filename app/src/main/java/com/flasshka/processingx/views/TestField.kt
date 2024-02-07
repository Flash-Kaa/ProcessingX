package com.flasshka.processingx.views

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object TestField {
    @Composable
    fun EditText() {
        var text by remember { mutableStateOf("") }

        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .padding(10.dp),
            visualTransformation = ColorTextTransformation()
        )
    }
}