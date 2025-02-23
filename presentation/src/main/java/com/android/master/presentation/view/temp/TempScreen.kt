package com.android.master.presentation.view.temp

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.android.master.domain.model.TempItem

@Composable
fun TempScreen(tempItem: TempItem) {
    Text(text = tempItem.data)
}