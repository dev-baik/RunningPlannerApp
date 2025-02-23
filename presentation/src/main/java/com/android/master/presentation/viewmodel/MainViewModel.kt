package com.android.master.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.android.master.domain.model.AccountInfo
import com.android.master.domain.model.TempItem
import com.android.master.domain.usecase.auth.GetAccountInfoUseCase
import com.android.master.domain.usecase.auth.LoginUseCase
import com.android.master.domain.usecase.auth.LogoutUseCase
import com.android.master.presentation.view.Temp
import com.android.master.presentation.view.Video
import com.android.master.presentation.utils.NavigationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAccountInfoUseCase: GetAccountInfoUseCase,
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    val accountInfo = getAccountInfoUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = null
        )

    fun openTemp(navHostController: NavHostController, tempItem: TempItem) {
        NavigationUtils.navigate(navHostController, Temp.navigateWithArg(tempItem))
    }

    fun openVideo(navHostController: NavHostController) {
        NavigationUtils.navigate(navHostController, Video.route)
    }

    fun signIn(accountInfo: AccountInfo) {
        viewModelScope.launch {
            loginUseCase(accountInfo)
        }
    }

    fun signOut() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }
}