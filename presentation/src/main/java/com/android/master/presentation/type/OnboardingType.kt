package com.android.master.presentation.type

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.android.master.presentation.R

enum class OnboardingType(
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int,
    @DrawableRes val imageRes: Int,
    @StringRes val imageDescription: Int,
    @StringRes val subDescription: Int
) {
    FIRST(
        titleRes = R.string.onboarding_first_title,
        descriptionRes = R.string.onboarding_first_description,
        imageRes = R.drawable.img_onboarding_background1,
        imageDescription = R.string.onboarding_first_image_description,
        subDescription = R.string.onboarding_first_sub_description
    ),
    SECOND(
        titleRes = R.string.onboarding_second_title,
        descriptionRes = R.string.onboarding_second_description,
        imageRes = R.drawable.img_onboarding_background2,
        imageDescription = R.string.onboarding_second_image_description,
        subDescription = R.string.onboarding_second_sub_description
    ),
    THIRD(
        titleRes = R.string.onboarding_third_title,
        descriptionRes = R.string.onboarding_third_description,
        imageRes = R.drawable.img_onboarding_background3,
        imageDescription = R.string.onboarding_third_image_description,
        subDescription = R.string.onboarding_third_sub_description
    )
}