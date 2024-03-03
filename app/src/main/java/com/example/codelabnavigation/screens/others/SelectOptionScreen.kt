package com.example.codelabnavigation.screens.others

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.codelabnavigation.R

@Composable
fun SelectOptionScreen(
    modifier: Modifier = Modifier,
    radioButtonList: List<String>,
    currentPrice: String,
    onSelectionChanged: (String) -> Unit,
    onCancelButtonClicked: () -> Unit,
    onNavigateNext: () -> Unit
) {
    var selectedOption by remember { mutableStateOf(radioButtonList[0]) }
    var showError by remember { mutableStateOf(true) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            radioButtonList.forEach { eachOption: String ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.selectable(
                        selected = (selectedOption == eachOption),
                        onClick = {
                            if (selectedOption.isBlank()) {
                                showError = true
                            } else {
                                selectedOption = eachOption
                                onSelectionChanged(selectedOption)
                                showError = false
                            }
                        }
                    )
                ) {
                    RadioButton(
                        selected = (selectedOption == eachOption),
                        onClick = {
                            if (selectedOption.isBlank()) {
                                showError = true
                            } else {
                                selectedOption = eachOption
                                onSelectionChanged(selectedOption)
                                showError = false
                            }
                        }
                    )
                    Text(text = eachOption)
                }
            }

            Divider(
                thickness = dimensionResource(R.dimen.thickness_divider),
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
            )

            if (showError) {
                Text(
                    text = "Debe seleccionar una opción primero",
                    modifier = Modifier.padding(
                        top = dimensionResource(R.dimen.padding_medium),
                        bottom = dimensionResource(R.dimen.padding_medium)
                    ),
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize = 18.sp)) {
                            append("Has seleccionado")
                        }
                        append(": ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)) {
                            append(selectedOption)
                        }
                        appendLine()
                        appendLine()
                        withStyle(style = SpanStyle(fontSize = 18.sp)) {
                            append("Precio")
                        }
                        append(": ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)) {
                            append(currentPrice)
                        }
                    },
                    modifier = Modifier.padding(
                        top = dimensionResource(R.dimen.padding_small),
                        bottom = dimensionResource(R.dimen.padding_medium)
                    )
                )
            }
        }

        ButtonCancelNext(
            onCancelButtonClicked = onCancelButtonClicked,
            onNavigateNext = onNavigateNext,
            showError = showError
        )
    }
}

@Composable
fun ButtonCancelNext(
    onCancelButtonClicked: () -> Unit,
    onNavigateNext: () -> Unit,
    showError: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        verticalAlignment = Alignment.Bottom
    ) {
        OutlinedButton(
            onClick = onCancelButtonClicked,
            modifier = Modifier.weight(1f)
        ) {
            Text(stringResource(R.string.cancel))
        }

        Button(
            onClick = onNavigateNext,
            modifier = Modifier.weight(1f),
            enabled = showError.not()
        ) {
            Text(stringResource(R.string.next))
        }
    }
}
