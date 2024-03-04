@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.codelabnavigation.screens.main

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.codelabnavigation.R
import com.example.codelabnavigation.data.DataSource
import com.example.codelabnavigation.screens.main.utils.cancelOrderAndNavigateToStart
import com.example.codelabnavigation.screens.main.utils.shareOrder
import com.example.codelabnavigation.screens.other.SelectOptionScreen
import com.example.codelabnavigation.screens.other.StartOrderScreen
import com.example.codelabnavigation.screens.other.SummaryScreen
import com.example.codelabnavigation.ui.OrderViewModel

enum class CupCakeScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Flavor(title = R.string.choose_flavor),
    Pickup(title = R.string.choose_pickup_date),
    Summary(title = R.string.order_summary)
}

@Composable
fun CupCakeApp(
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CupCakeScreen.valueOf(backStackEntry?.destination?.route ?: CupCakeScreen.Start.name)

    Scaffold(
        topBar = {
            CupCakeAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                onNavigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = CupCakeScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(route = CupCakeScreen.Start.name) {
                StartOrderScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    quantityOptions = DataSource.quantityOptions,
                    onNextButtonClicked = { cupcakeQuantity ->
                        viewModel.setQuantity(cupcakeQuantity)
                        navController.navigate(CupCakeScreen.Flavor.name)
                    }
                )
            }

            composable(route = CupCakeScreen.Flavor.name) {
                val context = LocalContext.current
                SelectOptionScreen(
                    modifier = Modifier.fillMaxHeight(),
                    radioButtonList = DataSource.flavors.map { id -> context.resources.getString(id) },
                    currentPrice = uiState.price,
                    onSelectionChanged = { newFlavor ->
                        viewModel.setFlavor(newFlavor)
                    },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    onNavigateNext = {
                        navController.navigate(CupCakeScreen.Pickup.name)
                    }
                )
            }

            composable(route = CupCakeScreen.Pickup.name) {
                SelectOptionScreen(
                    modifier = Modifier.fillMaxHeight(),
                    radioButtonList = uiState.pickupOptions,
                    currentPrice = uiState.price,
                    onSelectionChanged = { newDate ->
                        viewModel.setDate(newDate)
                    },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    onNavigateNext = {
                        navController.navigate(CupCakeScreen.Summary.name)
                    },
                )
            }

            composable(route = CupCakeScreen.Summary.name) {
                val context = LocalContext.current
                SummaryScreen(
                    modifier = Modifier.fillMaxHeight(),
                    orderUiState = uiState,
                    onSendButtonClicked = { subject: String, summary: String ->
                        shareOrder(context, subject = subject, summary = summary)
                    },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    }
                )
            }
        }
    }
}