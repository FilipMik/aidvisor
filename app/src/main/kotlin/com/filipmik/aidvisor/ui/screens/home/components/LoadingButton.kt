package com.filipmik.aidvisor.ui.screens.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.filipmik.aidvisor.ui.screens.home.ScreenState

@Composable
fun LoadingButton(
    modifier: Modifier,
    text: String,
    apiCompletionState: ScreenState,
    onClick: () -> Unit
) {

    Button(
        modifier = modifier,
        contentPadding = PaddingValues(4.dp),
        onClick = { onClick() },
        enabled = apiCompletionState != ScreenState.LOADING
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(4.dp)
        ){
            if (apiCompletionState == ScreenState.LOADING) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(text = text)
            }
        }
    }
}
