package com.android.master.presentation.feature.signin

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
fun SignInScreenPreview() {
    RPAPPTheme {
        SignInScreen(
            padding = PaddingValues(0.dp),
            navigateToHome = {}
        )
    }
}

@Composable
fun SignInRoute(
    padding: PaddingValues,
    navigateToHome: () -> Unit
) {
    SignInScreen(
        padding = padding,
        navigateToHome = navigateToHome
    )
}

@Composable
fun SignInScreen(
    padding: PaddingValues,
    navigateToHome: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        Button(
            onClick = navigateToHome
        ) {
            Text(text = "SignIn")
        }
    }
}