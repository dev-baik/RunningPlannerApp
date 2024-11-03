package com.android.master.presentation.ui.main

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.android.master.presentation.viewmodel.MainViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task

@Composable
fun MyPageScreen(
    viewModel: MainViewModel,
    googleSignInClient: GoogleSignInClient
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by remember { mutableStateOf(value = "") }
    var showPassword by remember { mutableStateOf(value = false) }

    val googleLoginForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(result.data)

            val account = task.result
            Log.i("GoogleLogin", account.idToken.toString())
        } else {
            Log.e("GoogleLogin", result.toString())
        }
    }

    Column {
        OutlinedTextField(
            value = email,
            label = { Text("이메일") },
            onValueChange = {
                email = it
            },
            singleLine = true,
        )

        OutlinedTextField(
            value = password,
            label = { Text("비밀번호") },
            onValueChange = { password = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                if (showPassword) {
                    IconButton(onClick = { showPassword = false }) {
                        Icon(
                            imageVector = Icons.Filled.Visibility,
                            contentDescription = "hide_password"
                        )
                    }
                } else {
                    IconButton(onClick = { showPassword = true }) {
                        Icon(
                            imageVector = Icons.Filled.VisibilityOff,
                            contentDescription = "hide_password"
                        )
                    }
                }
            },
            singleLine = true
        )

        Spacer(Modifier.size(50.dp))

        Button(onClick = { googleLoginForResult.launch(googleSignInClient.signInIntent) }) {
            Text("구글 로그인")
        }
    }
}