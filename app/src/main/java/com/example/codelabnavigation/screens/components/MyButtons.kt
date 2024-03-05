package com.example.codelabnavigation.screens.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.codelabnavigation.R

@Composable
fun SelectQuantityButton(
    modifier: Modifier = Modifier,
    @StringRes labelResourceId: Int,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(stringResource(labelResourceId))
    }
}

@Composable
fun ShareOrderButton(
    modifier: Modifier = Modifier,
    @StringRes labelResourceId: Int,
    onSendButtonClicked: (String, String) -> Unit,
    orderId: String,
    summary: String
) {
    Button(
        modifier = modifier,
        onClick = {
            onSendButtonClicked(orderId, summary)
        }
    ) {
        Text(stringResource(labelResourceId))
    }
}

@Composable
fun NextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @StringRes labelResourceId: Int,
    isEnabled: Boolean = true
) {
    Button(
        modifier = modifier,
        onClick = { if (isEnabled) onClick() },
        enabled = isEnabled,
    ) {
        /* Se utiliza para agregar una transparencia a un elemento (en este caso un botón) */
        val alpha = if (isEnabled) 1f else 0.5f
        Text(
            stringResource(labelResourceId),
            modifier = Modifier.alpha(alpha)
        )
    }
}

@Composable
fun CancelButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @StringRes labelResourceId: Int,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
    ) {
        Text(stringResource(labelResourceId))
    }
}

