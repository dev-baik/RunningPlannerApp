package com.android.master.presentation.view.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.android.master.domain.model.TempItem
import com.android.master.presentation.utils.NavigationUtils
import com.android.master.presentation.view.Temp
import com.android.master.presentation.view.Video

@Composable
fun HomeScreen(
    navController: NavHostController
) {

    Column {
        Button(onClick = {
            NavigationUtils.navigate(
                navController, Temp.navigateWithArg(TempItem("temp"))
            )
        }) {
            Text(text = "Temp")
        }

        Button(onClick = { NavigationUtils.navigate(navController, Video.route) }) {
            Text(text = "Video")
        }
    }
}