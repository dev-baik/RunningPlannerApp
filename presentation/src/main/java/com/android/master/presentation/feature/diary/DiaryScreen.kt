package com.android.master.presentation.feature.diary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.master.presentation.ui.theme.RPAPPTheme
import com.android.master.presentation.ui.theme.RPAppTheme

@Preview(showBackground = true)
@Composable
fun DiaryScreenPreview() {
    RPAPPTheme {
        DiaryScreen(PaddingValues(0.dp))
    }
}

@Composable
fun DiaryRoute(
    padding: PaddingValues
) {
    DiaryScreen(
        padding = padding
    )
}

@Composable
fun DiaryScreen(
    padding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        Text(text = "Diary")
    }
}
