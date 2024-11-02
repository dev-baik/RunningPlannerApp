package com.android.master.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.android.master.domain.model.AccountInfo
import com.android.master.domain.model.TempItem
import com.android.master.domain.usecase.auth.AccountUseCase
import com.android.master.presentation.ui.Temp
import com.android.master.presentation.utils.NavigationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val accountUseCase: AccountUseCase
) : ViewModel() {

    val accountInfo = accountUseCase.getAccountInfo()

    fun openTemp(navHostController: NavHostController, tempItem: TempItem) {
        NavigationUtils.navigate(navHostController, Temp.navigateWithArg(tempItem))
    }

    fun signIn(accountInfo: AccountInfo) {
        viewModelScope.launch {
            accountUseCase.signIn(accountInfo)
        }
    }

    fun signOut() {
        viewModelScope.launch {
            accountUseCase.signOut()
        }
    }
}