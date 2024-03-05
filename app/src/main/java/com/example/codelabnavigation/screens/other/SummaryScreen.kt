package com.example.codelabnavigation.screens.other

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.example.codelabnavigation.R
import com.example.codelabnavigation.data.OrderUiState
import com.example.codelabnavigation.screens.components.CancelButton
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
        /* Sección de Resumen */
        Column (
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ){
            /* Cantidad */
            Text(stringResource(R.string.quantity).toUpperCase(Locale.getDefault()))
            Text(
                text = "${orderUiState.quantity}",
                fontWeight = FontWeight.Bold
            )
            Divider(thickness = dimensionResource(R.dimen.thickness_divider))

            /* Sabor */
            Text(stringResource(R.string.flavor).toUpperCase(Locale.getDefault()))
            Text(
                text = orderUiState.flavor,
                fontWeight = FontWeight.Bold
            )
            Divider(thickness = dimensionResource(R.dimen.thickness_divider))

            /* Fecha de recogida */
            Text(stringResource(R.string.pickup_date).toUpperCase(Locale.getDefault()))
            Text(
                text = orderUiState.date,
                fontWeight = FontWeight.Bold
            )
            Divider(thickness = dimensionResource(R.dimen.thickness_divider))

            /* Precio total */
            Text(
                buildAnnotatedString {
                    append(stringResource(R.string.total_price).toUpperCase(Locale.getDefault()))
                    append(": ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(orderUiState.price)
                    }
                }
            )
        }

        /* Sección de Botones */
        Row (
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Column (
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        val summary =
                            "Quantity: ${orderUiState.quantity} | Flavor: ${orderUiState.flavor} | PickupDate: ${orderUiState.date} | Total: ${orderUiState.price}"
                        onSendButtonClicked("Order #198273D", summary)
                    }
                ) {
                    Text(stringResource(R.string.send))
                }

                /* Button Cancel */
                CancelButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onCancelButtonClicked,
                    labelResourceId = R.string.cancel,
                )
            }
        }
    }
}
