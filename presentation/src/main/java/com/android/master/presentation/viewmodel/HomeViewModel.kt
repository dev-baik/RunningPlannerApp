package com.android.master.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.android.master.domain.model.TempItem
import com.android.master.presentation.utils.NavigationUtils
import com.android.master.presentation.view.Temp
import com.android.master.presentation.view.Video

class HomeViewModel : ViewModel() {

    fun openTemp(navHostController: NavHostController, tempItem: TempItem) {
        NavigationUtils.navigate(navHostController, Temp.navigateWithArg(tempItem))
    }

    fun openVideo(navHostController: NavHostController) {
        NavigationUtils.navigate(navHostController, Video.route)
    }
}