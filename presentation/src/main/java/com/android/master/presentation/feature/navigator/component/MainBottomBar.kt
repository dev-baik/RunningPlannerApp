package com.android.master.presentation.feature.navigator.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.master.presentation.type.MainNavigationBarItemType
import com.android.master.presentation.ui.theme.RPAPPTheme
import com.android.master.presentation.ui.theme.RPAppTheme

@Preview
@Composable
fun CustomNavigationBarItemPreview() {
    RPAPPTheme {
        MainBottomBar(
            isVisible = true,
            navigationBarItems = MainNavigationBarItemType.entries.toList(),
            onNavigationBarItemSelected = {},
            currentNavigationBarItem = MainNavigationBarItemType.HOME
        )
    }
}

@Composable
fun MainBottomBar(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    navigationBarItems: List<MainNavigationBarItemType>,
    onNavigationBarItemSelected: (MainNavigationBarItemType) -> Unit,
    currentNavigationBarItem: MainNavigationBarItemType?
) {
    AnimatedVisibility(isVisible) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(RPAppTheme.colors.white)
                .border(1.dp, RPAppTheme.colors.gray200)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navigationBarItems.forEach { mainNavigationBarItemType ->
                CustomNavigationBarItem(
                    onClick = { onNavigationBarItemSelected(mainNavigationBarItemType) },
                    isSelected = currentNavigationBarItem == mainNavigationBarItemType,
                    mainNavigationBarItemType = mainNavigationBarItemType,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun CustomNavigationBarItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isSelected: Boolean,
    mainNavigationBarItemType: MainNavigationBarItemType
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .background(if (isSelected) RPAppTheme.colors.white else Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(11.dp))
        Icon(
            painter = painterResource(id = mainNavigationBarItemType.iconRes),
            tint = if (isSelected) RPAppTheme.colors.black else RPAppTheme.colors.gray200,
            contentDescription = stringResource(mainNavigationBarItemType.label)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(mainNavigationBarItemType.label),
            style = RPAppTheme.typography.capReg12,
            color = if (isSelected) RPAppTheme.colors.black else RPAppTheme.colors.gray300
        )
        Spacer(modifier = Modifier.height(11.dp))
    }
}