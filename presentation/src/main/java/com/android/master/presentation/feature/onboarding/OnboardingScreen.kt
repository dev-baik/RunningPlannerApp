package com.android.master.presentation.feature.onboarding

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.android.master.presentation.Onboarding.FIRST
import com.android.master.presentation.R
import com.android.master.presentation.feature.onboarding.navigation.OnboardingRoute
import com.android.master.presentation.type.OnboardingType
import com.android.master.presentation.ui.component.button.RPAppOutlinedButton
import com.android.master.presentation.ui.component.dotsindicator.DotsIndicator
import com.android.master.presentation.ui.theme.RPAPPTheme
import com.android.master.presentation.ui.theme.RPAppTheme
import com.android.master.presentation.util.context.findActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    RPAPPTheme {
        OnboardingScreen(
            padding = PaddingValues(0.dp),
            onHomeButtonClicked = {}
        )
    }
}

@Composable
fun OnboardingRoute(
    viewModel: OnBoardingViewModel = hiltViewModel(),
    padding: PaddingValues,
    onShowSnackbar: (String, SnackbarDuration) -> Unit,
    navigateToHome: (NavOptions) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { OnboardingType.entries.size })
    val context = LocalContext.current
    var backPressedTime = 0L

    BackHandler {
        when (pagerState.currentPage) {
            FIRST -> {
                if (System.currentTimeMillis() - backPressedTime <= 500L) {
                    context.findActivity().finish()
                } else {
                    onShowSnackbar(context.getString(R.string.app_finish_toast), SnackbarDuration.Short)
                }
                backPressedTime = System.currentTimeMillis()
            }

            else -> {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                }
            }
        }
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { onBoardingSideEffect ->
                when (onBoardingSideEffect) {
                    OnBoardingContract.OnBoardingSideEffect.NavigationToHome -> navigateToHome(
                        navOptions {
                            popUpTo(OnboardingRoute.ROUTE) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    )
                }
            }
    }

    OnboardingScreen(
        padding = padding,
        pagerState = pagerState,
        coroutineScope = coroutineScope,
        onHomeButtonClicked = { viewModel.setSideEffect(OnBoardingContract.OnBoardingSideEffect.NavigationToHome) }
    )
}

@Composable
fun OnboardingScreen(
    padding: PaddingValues,
    pagerState: PagerState = rememberPagerState(pageCount = { OnboardingType.entries.size }),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    onHomeButtonClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
            val onboardingType = OnboardingType.entries[page]
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = onboardingType.imageRes),
                    contentDescription = stringResource(id = onboardingType.imageDescription),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 40.dp)
                )
                Spacer(modifier = Modifier.height(22.dp))
                Text(
                    text = stringResource(id = onboardingType.titleRes),
                    style = RPAppTheme.typography.titleSemi24
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(id = onboardingType.descriptionRes),
                    style = RPAppTheme.typography.bodyMed14,
                    color = RPAppTheme.colors.gray500,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = stringResource(id = onboardingType.subDescription),
                    style = RPAppTheme.typography.capReg12,
                    color = RPAppTheme.colors.gray400,
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.height(14.dp))
        RPAppOutlinedButton(
            textContent = if (pagerState.currentPage == OnboardingType.entries.size - 1) {
                stringResource(id = R.string.onboarding_home)
            } else {
                stringResource(id = R.string.onboarding_next)
            },
            textStyle = RPAppTheme.typography.bodySemi16,
            contentColor = RPAppTheme.colors.black,
            backgroundColor = RPAppTheme.colors.pink,
            borderColor = RPAppTheme.colors.black,
            borderWidth = 3.dp,
            cornerRadius = 10.dp,
            paddingHorizontal = 20.dp,
            paddingVertical = 20.dp,
            onClick = {
                if (pagerState.currentPage == OnboardingType.entries.size - 1) {
                    onHomeButtonClicked()
                } else {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(15.dp))
        DotsIndicator(
            totalDots = OnboardingType.entries.size,
            selectedIndex = pagerState.currentPage,
            indicatorSize = 8.dp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(15.dp))
    }
}
