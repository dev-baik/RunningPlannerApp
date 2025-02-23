package com.android.master.presentation.view.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.android.master.domain.model.TempItem
import com.android.master.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavHostController
) {
    val viewModel = hiltViewModel<HomeViewModel>()

    Column {
        Button(onClick = { viewModel.openTemp(navController, TempItem("Temp")) }) {
            Text(text = "Temp")
        }

        Button(onClick = { viewModel.openVideo(navController) }) {
            Text(text = "Video")
        }
    }
}