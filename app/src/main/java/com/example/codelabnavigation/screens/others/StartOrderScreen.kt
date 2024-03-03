package com.example.codelabnavigation.screens.others

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
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
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            Image(
                painter = painterResource(R.drawable.cupcake),
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .width(148.dp)
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            Text(
                text = stringResource(R.string.order_cupcakes),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
            quantityOptions.forEach { item ->
                SelectQuantityButton(
                    labelResourceId = item.first,
                    onClick = { onNextButtonClicked(item.second) },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
fun SelectQuantityButton(
    modifier: Modifier = Modifier,
    @StringRes labelResourceId: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.widthIn(min = 250.dp)
    ) {
        Text(stringResource(labelResourceId))
    }
}
