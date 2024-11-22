package com.android.master.presentation.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.android.master.domain.model.TempItem
import com.android.master.presentation.viewmodel.MainViewModel

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    navController: NavHostController
) {
    Column {
        Button(onClick = { viewModel.openTemp(navController, TempItem("Temp")) }) {
            Text(text = "Temp")
        }

        Button(onClick = { viewModel.openVideo(navController) }) {
            Text(text = "Video")
        }
    }
}