package com.android.master.presentation.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.master.presentation.ui.theme.RPAPPTheme

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    RPAPPTheme {
        HomeScreen(
            padding = PaddingValues(0.dp),
            onVideoButtonClicked = {}
        )
    }
}

@Composable
fun HomeRoute(
    padding: PaddingValues,
    onVideoButtonClicked: () -> Unit
) {
    HomeScreen(
        padding = padding,
        onVideoButtonClicked = onVideoButtonClicked
    )
}

@Composable
fun HomeScreen(
    padding: PaddingValues,
    onVideoButtonClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        Text(text = "Home")
        Button(onClick = onVideoButtonClicked) {
            Text(text = "Video")
        }
    }
}