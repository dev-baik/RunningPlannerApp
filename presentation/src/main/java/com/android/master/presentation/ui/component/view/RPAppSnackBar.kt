package com.android.master.presentation.ui.component.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.master.presentation.R
import com.android.master.presentation.ui.theme.Gray400
import com.android.master.presentation.ui.theme.RPAPPTheme
import com.android.master.presentation.ui.theme.RPAppTheme
import com.android.master.presentation.ui.theme.White

@Preview(showBackground = true)
@Composable
fun RPAppSnackBarContentPreview() {
    RPAPPTheme {
        RPAppSnackBarContent("스낵바 메시지가 표시됩니다.")
    }
}

@Composable
fun RPAppSnackBar(
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = snackBarHostState,
        modifier = modifier
    ) {
        snackBarHostState.currentSnackbarData?.let { snackbarData ->
            RPAppSnackBarContent(snackbarData.visuals.message)
        }
    }
}

@Composable
fun RPAppSnackBarContent(
    snackBarMessage: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(36.dp),
        colors = CardDefaults.cardColors(containerColor = Gray400),
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp, start = 20.dp, end = 20.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = stringResource(R.string.snackbar_icon_description),
                modifier = Modifier.size(36.dp)
            )
            Text(
                text = snackBarMessage,
                style = RPAppTheme.typography.capReg16,
                color = White,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}