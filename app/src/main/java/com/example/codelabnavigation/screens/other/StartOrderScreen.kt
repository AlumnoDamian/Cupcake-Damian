package com.example.codelabnavigation.screens.other

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.codelabnavigation.R
import com.example.codelabnavigation.screens.components.SelectQuantityButton

@Composable
fun StartOrderScreen(
    modifier: Modifier = Modifier,
    quantityOptions: List<Pair<Int, Int>>,
    onNextButtonClicked: (Int) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        OrderDetailsSection(modifier = Modifier.fillMaxWidth())
        QuantityOptionsSection(quantityOptions, onNextButtonClicked)
    }
}

@Composable
fun OrderDetailsSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        /* CupCake Image */
        Image(
            painter = painterResource(R.drawable.cupcake),
            contentDescription = null,
            modifier = Modifier.width(300.dp)
        )
        /* CupCake Image */
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        /* Title */
        Text(
            text = stringResource(R.string.order_cupcakes),
            style = MaterialTheme.typography.headlineSmall,
        )
        /* Title */
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
    }
}

@Composable
fun QuantityOptionsSection(
    quantityOptions: List<Pair<Int, Int>> = listOf(),
    onNextButtonClicked : (Int) -> Unit = {}
) {
    Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))) {
        quantityOptions.forEach { (labelResourceId, quantity) ->
            SelectQuantityButton(
                modifier = Modifier.fillMaxWidth(),
                labelResourceId = labelResourceId,
                onClick = { onNextButtonClicked(quantity) },
            )
        }
    }
}