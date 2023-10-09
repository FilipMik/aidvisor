package com.filipmik.aidvisor.ui

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.filipmik.aidvisor.ui.components.BottomNavigationBar
import com.filipmik.aidvisor.ui.theme.AidvisorTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppUi() {
    AidvisorTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val navController = rememberNavController()

            Scaffold(
                bottomBar = { BottomNavigationBar(navController) },
            ) {
                Navigation(navController = navController)
            }
        }
    }
}
