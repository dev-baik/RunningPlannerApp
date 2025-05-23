package com.android.master.presentation.ui.component.dotsindicator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.android.master.presentation.ui.theme.RPAppTheme

@Composable
fun DotsIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex: Int,
    indicatorSize: Dp
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        repeat(totalDots) { i ->
            val color = if (i == selectedIndex) RPAppTheme.colors.blue else RPAppTheme.colors.gray200
            Box(
                modifier = Modifier
                    .size(indicatorSize)
                    .background(color, CircleShape)
            )
        }
    }
}