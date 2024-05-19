package com.task_baham.ui.composable.universal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.task_baham.util.PermissionDeniedText

@Composable
fun DisplayPermissionNeed(onRetryClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
            .clickable {
                onRetryClicked.invoke()
            }
    ) {
        Text(
            text = PermissionDeniedText,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        )
    }
}