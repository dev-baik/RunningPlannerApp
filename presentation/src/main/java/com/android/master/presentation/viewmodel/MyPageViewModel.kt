package com.android.master.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.master.domain.model.AccountInfo
import com.android.master.domain.usecase.auth.GetAccountInfoUseCase
import com.android.master.domain.usecase.auth.LoginUseCase
import com.android.master.domain.usecase.auth.LogoutUseCase
import com.android.master.presentation.intent.MyPageIntent
import com.android.master.presentation.state.MyPageState
import com.android.master.presentation.view.myPage.MyPageEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getAccountInfoUseCase: GetAccountInfoUseCase,
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val channel = Channel<MyPageIntent>()

    private val _state = MutableStateFlow<MyPageState>(MyPageState.Idle)
    val state: StateFlow<MyPageState> = _state.asStateFlow()

    private val _effect = Channel<MyPageEffect>()
    val effect = _effect.consumeAsFlow()

    init {
        observeAccountInfo()
        handleIntents()
    }

    private fun observeAccountInfo() {
        viewModelScope.launch {
            getAccountInfoUseCase().collectLatest { accountInfo ->
                _state.value = if (accountInfo.type != null) {
                    MyPageState.Success(accountInfo)
                } else {
                    MyPageState.Idle
                }
            }
        }
    }

    fun sendIntent(intent: MyPageIntent) {
        viewModelScope.launch { channel.send(intent) }
    }

    private fun handleIntents() {
        viewModelScope.launch {
            channel.consumeAsFlow().collectLatest { intent ->
                when (intent) {
                    is MyPageIntent.SignIn -> handleSignIn(intent.accountInfo)
                    is MyPageIntent.Logout -> handleLogout()
                }
            }
        }
    }

    private fun handleSignIn(accountInfo: AccountInfo) {
        viewModelScope.launch {
            _state.value = MyPageState.Loading
            try {
                loginUseCase(accountInfo)
                _state.value = MyPageState.Success(accountInfo)
                _effect.send(MyPageEffect.ShowSnackbar("로그인 성공"))
            } catch (e: Exception) {
                _state.value = MyPageState.Error(e.message ?: "로그인 실패")
                _effect.send(MyPageEffect.ShowSnackbar("로그인 실패"))
            }
        }
    }

    private fun handleLogout() {
        viewModelScope.launch {
            _state.value = MyPageState.Loading
            try {
                logoutUseCase()
                _state.value = MyPageState.Idle
                _effect.send(MyPageEffect.ShowSnackbar("로그아웃 성공"))
            } catch (e: Exception) {
                _state.value = MyPageState.Error(e.message ?: "로그아웃 실패")
                _effect.send(MyPageEffect.ShowSnackbar("로그아웃 실패"))
            }
        }
    }
}
