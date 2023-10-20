package com.filipmik.aidvisor.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.filipmik.aidvisor.ui.components.BottomNavigationBar
import com.filipmik.aidvisor.ui.theme.AidvisorTheme
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun AppUi() {
    AidvisorTheme {
        Surface(color = MaterialTheme.colorScheme.background) {

            val bottomSheetNavigator = rememberBottomSheetNavigator()
            val navController = rememberNavController(bottomSheetNavigator)

            ModalBottomSheetLayout(
                modifier = Modifier.fillMaxSize(),
                bottomSheetNavigator = bottomSheetNavigator,
                sheetShape = MaterialTheme.shapes.large
            ) {
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController) },
                ) {
                    Box(modifier = Modifier.padding(it)) {
                        Navigation(navController = navController)
                    }
                }
            }
        }
    }
}
