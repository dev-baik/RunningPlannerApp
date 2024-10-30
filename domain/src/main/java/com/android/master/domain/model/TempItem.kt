package com.android.master.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TempItem(
    val data: String
): Parcelable