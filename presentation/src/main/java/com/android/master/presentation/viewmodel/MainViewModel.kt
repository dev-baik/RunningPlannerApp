package com.android.master.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.android.master.domain.model.TempItem
import com.android.master.presentation.ui.NavigationRouteName
import com.android.master.presentation.utils.NavigationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    fun openTemp(navHostController: NavHostController, tempItem: TempItem) {
        NavigationUtils.navigate(navHostController, NavigationRouteName.TEMP, tempItem)
    }
}