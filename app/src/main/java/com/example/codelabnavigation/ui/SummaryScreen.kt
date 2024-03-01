package com.example.codelabnavigation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.codelabnavigation.R
import com.example.codelabnavigation.data.OrderUiState

@Composable
fun SummaryScreen(
    modifier: Modifier,
    orderUiState: OrderUiState,
    onSendButton: (String, String) -> Unit
){
    Column () {
        Text("QUANTITY")
        Text("${orderUiState.quantity}")
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
        Text("FLAVOR")
        Text(orderUiState.flavor)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
        Text("PICK UP DATE")
        Text(orderUiState.date)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
        Text("Total: ${orderUiState.price}")
        Button(
            modifier = Modifier.padding(vertical = 4.dp),
            onClick = {
                val summary = "Q: ${orderUiState.quantity}: F: ${orderUiState.flavor}: D: ${orderUiState.date}"
                onSendButton("Order #198273D", summary) }) {
            Text(text = "Send Another App")
        }
    }
}