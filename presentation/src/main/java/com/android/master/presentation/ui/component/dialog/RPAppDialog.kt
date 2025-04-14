package com.android.master.presentation.ui.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog

@Composable
fun RPAppDialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        content()
    }
}