package com.android.master.presentation.ui.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.master.presentation.ui.theme.RPAPPTheme
import com.android.master.presentation.ui.theme.RPAppTheme

@Preview
@Composable
fun RPAppBasicTextFieldPreview() {
    RPAPPTheme {
        RPAppBasicTextField(
            icon = Icons.Filled.Search to "",
            placeholder = "텍스트 필드 안에 표시됩니다."
        )
    }
}

@Composable
fun RPAppBasicTextField(
    modifier: Modifier = Modifier,
    icon: Pair<ImageVector, String>? = null,
    value: String = "",
    onValueChange: (String) -> Unit = { _ -> },
    placeholder: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .background(color = RPAppTheme.colors.gray100)
            .border(width = 1.dp, color = RPAppTheme.colors.black)
            .padding(vertical = 16.dp, horizontal = 14.dp),
        value = value,
        onValueChange = onValueChange,
        textStyle = RPAppTheme.typography.bodyMed14,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                icon?.let {
                    Icon(
                        imageVector = it.first,
                        contentDescription = it.second,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    innerTextField()
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = RPAppTheme.colors.gray500,
                            style = RPAppTheme.typography.bodyMed14
                        )
                    }
                }
            }
        }
    )
}