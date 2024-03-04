package com.example.codelabnavigation.screens.main.utils

import androidx.navigation.NavHostController
import com.example.codelabnavigation.screens.main.CupCakeScreen
import com.example.codelabnavigation.ui.OrderViewModel
fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    navController.popBackStack(CupCakeScreen.Start.name, inclusive = false)
}