package com.android.master.presentation.ui.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.android.master.presentation.ui.theme.Black
import com.android.master.presentation.ui.theme.RPAPPTheme
import com.android.master.presentation.ui.theme.White

@Preview
@Composable
fun RPAppButtonPreview() {
    RPAPPTheme {
        RPAppButton(
            backgroundColor = White,
            borderColor = Black,
            cornerRadius = 10.dp,
            borderWidth = 3.dp,
            paddingHorizontal = 30.dp,
            paddingVertical = 10.dp,
            onClick = {}
        ) {
            Text("버튼 안에 표시됩니다.")
        }
    }
}

@Composable
fun RPAppButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    borderColor: Color = Color.Unspecified,
    cornerRadius: Dp = 0.dp,
    borderWidth: Dp = 0.dp,
    paddingHorizontal: Dp = 0.dp,
    paddingVertical: Dp = 0.dp,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(color = backgroundColor)
            .border(width = borderWidth, color = borderColor, shape = RoundedCornerShape(cornerRadius))
            .padding(horizontal = paddingHorizontal, vertical = paddingVertical)
            .clickable { onClick() }
    ) {
        content()
    }
}