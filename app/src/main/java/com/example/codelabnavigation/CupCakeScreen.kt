@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.codelabnavigation

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.codelabnavigation.data.DataSource
import com.example.codelabnavigation.ui.OrderViewModel
import com.example.codelabnavigation.ui.SelectOptionScreen
import com.example.codelabnavigation.ui.StartOrderScreen
import com.example.codelabnavigation.ui.SummaryScreen

/**
 * enum values that represent the screens in the app
 */
enum class CupCakeScreen(
    //Realiza una ruta hacia la carpeta Res del archivo strings
    @StringRes val title: Int
) {
    Start(title = R.string.app_name),
    Flavor(title = R.string.choose_flavor),
    Pickup(title = R.string.choose_pickup_date),
    Summary(title = R.string.order_summary)
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */

@Composable
fun CupcakeAppBar(
    currentScreen: CupCakeScreen,
    canNavigateBack: Boolean,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onNavigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun CupCakeApp(
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
){
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = CupCakeScreen.valueOf(
        backStackEntry?.destination?.route?: CupCakeScreen.Start.name
    )

    Scaffold(
        topBar = {
            CupcakeAppBar(
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
            // Primera pantalla de la aplicación para iniciar la orden de cupcakes
            composable(route = CupCakeScreen.Start.name) {
                StartOrderScreen(
                    quantityOptions = DataSource.quantityOptions,
                    onNextButtonClicked = {cupcakeQuantity ->
                        viewModel.setQuantity(cupcakeQuantity)
                        navController.navigate(CupCakeScreen.Flavor.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }

            // Segunda pantalla de la aplicación que muestra las opciones de flavors
            composable(route = CupCakeScreen.Flavor.name) {
                val context = LocalContext.current
                SelectOptionScreen(
                    modifier = Modifier.fillMaxHeight(),
                    radioButtonList = DataSource.flavors.map { id -> context.resources.getString(id) },
                    currentPrice = uiState.price,
                    onSelectionChanged = {
                        newFlavor -> viewModel.setFlavor(newFlavor)
                    },
                    onNavigateNext = {
                        navController.navigate(CupCakeScreen.Pickup.name)
                    },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    }
                )
            }

            composable(route = CupCakeScreen.Pickup.name) {
                SelectOptionScreen(
                    modifier = Modifier.fillMaxHeight(),
                    radioButtonList = uiState.pickupOptions,
                    currentPrice = uiState.price,
                    onSelectionChanged = {
                        newDate -> viewModel.setDate(newDate)
                    },
                    onNavigateNext = {
                        navController.navigate(CupCakeScreen.Summary.name)
                    },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    }
                )
            }

            composable(route = CupCakeScreen.Summary.name){
                val context = LocalContext.current
                SummaryScreen(
                    modifier = Modifier.fillMaxHeight(),
                    orderUiState = uiState
                ){
                        subject: String, summary: String ->
                    shareOrder(context, subject = subject, summary = summary)
                }
            }
        }
    }
}


// Resetea el estado del [OrderViewModel] y redirige a la pantalla de inicio
private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    navController.popBackStack(CupCakeScreen.Start.name, inclusive = false)
}

private fun shareOrder(context: Context, subject: String, summary: String) {
    // Create an ACTION_SEND implicit intent with order details in the intent extras
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.new_cupcake_order)
        )
    )
}