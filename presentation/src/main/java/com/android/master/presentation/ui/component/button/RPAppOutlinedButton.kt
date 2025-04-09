package com.android.master.presentation.ui.component.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.android.master.presentation.ui.theme.Black
import com.android.master.presentation.ui.theme.RPAPPTheme
import com.android.master.presentation.ui.theme.RPAppTheme
import com.android.master.presentation.ui.theme.White

@Preview
@Composable
fun RPAppOutlinedButtonPreview() {
    RPAPPTheme {
        RPAppOutlinedButton(
            textContent = "버튼 안에 표시됩니다.",
            textStyle = RPAppTheme.typography.bodyMed14,
            contentColor = Black,
            backgroundColor = White,
            cornerRadius = 10.dp,
            borderColor = Black,
            borderWidth = 3.dp,
            paddingHorizontal = 30.dp,
            paddingVertical = 20.dp,
            onClick = {},
        )
    }
}

@Composable
fun RPAppOutlinedButton(
    modifier: Modifier = Modifier,
    textContent: String,
    textStyle: TextStyle,
    contentColor: Color,
    backgroundColor: Color,
    borderColor: Color,
    borderWidth: Dp,
    cornerRadius: Dp,
    paddingHorizontal: Dp = 0.dp,
    paddingVertical: Dp = 0.dp,
    onClick: () -> Unit
) {
    RPAppButton(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = paddingHorizontal),
        backgroundColor = backgroundColor,
        borderColor = borderColor,
        borderWidth = borderWidth,
        cornerRadius = cornerRadius,
        paddingVertical = paddingVertical,
        onClick = onClick
    ) {
        Text(
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = textContent,
            style = textStyle,
            color = contentColor
        )
    }
}