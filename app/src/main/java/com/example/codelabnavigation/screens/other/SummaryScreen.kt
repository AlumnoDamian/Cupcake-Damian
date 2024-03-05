package com.example.codelabnavigation.screens.other

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.codelabnavigation.R
import com.example.codelabnavigation.data.OrderUiState
import com.example.codelabnavigation.screens.components.CancelButton
import com.example.codelabnavigation.screens.components.ShareOrderButton
import java.util.Locale

@Composable
fun SummaryScreen(
    modifier: Modifier,
    orderUiState: OrderUiState,
    onSendButtonClicked: (orderId: String, summary: String) -> Unit,
    onCancelButtonClicked: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        OrderSummarySection(orderUiState)
        ButtonSection(orderUiState, onSendButtonClicked, onCancelButtonClicked)
    }
}

/* Sección de Resumen */
@Composable
fun OrderSummarySection(orderUiState: OrderUiState) {
    Column (
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ){
        OrderInfoItem(stringResource(R.string.quantity), value = "${orderUiState.quantity}")
        OrderInfoItem(stringResource(R.string.flavor), orderUiState.flavor)
        OrderInfoItem(stringResource(R.string.pickup_date), orderUiState.date)
        OrderInfoItem(stringResource(R.string.total_price), orderUiState.price)
    }
}

/* Elemento de información de la orden */
@Composable
fun OrderInfoItem(
    label: String,
    value: String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ) {
        Text(text = label.toUpperCase(Locale.getDefault()))
        Text(text = value, fontWeight = FontWeight.Bold)
        Divider(thickness = dimensionResource(R.dimen.thickness_divider))
    }
}

/* Sección de Botones */
@Composable
fun ButtonSection(
    orderUiState: OrderUiState,
    onSendButtonClicked: (orderId: String, summary: String) -> Unit,
    onCancelButtonClicked: () -> Unit
) {
    Row (
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Column (
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            /* Botón para compartir la orden */
            ShareOrderButton(
                modifier = Modifier.fillMaxWidth(),
                labelResourceId = R.string.send,
                orderId = "Order #123456S",
                summary = "Quantity: ${orderUiState.quantity} | Flavor: ${orderUiState.flavor} | PickupDate: ${orderUiState.date} | Total: ${orderUiState.price}",
                onSendButtonClicked = { orderId, summary ->
                    println("Enviando orden $orderId con resumen: $summary")
                    onSendButtonClicked(orderId, summary)
                }
            )
            /* Boton Cancel */
            CancelButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onCancelButtonClicked,
                labelResourceId = R.string.cancel,
            )
        }
    }
}