package com.example.codelabnavigation.screens.other

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
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var showError by remember { mutableStateOf(true) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            radioButtonList.forEach { eachOption: String ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (selectedOption == eachOption),
                            onClick = {
                                if (selectedOption == eachOption) {
                                    selectedOption = null
                                    showError = true
                                } else {
                                    selectedOption = eachOption
                                    onSelectionChanged(selectedOption.orEmpty())
                                    showError = false
                                }
                            }
                        )
                ) {
                    RadioButton(
                        selected = (selectedOption == eachOption),
                        onClick = {
                            if (selectedOption == eachOption) {
                                selectedOption = null
                                showError = true
                            } else {
                                selectedOption = eachOption
                                onSelectionChanged(selectedOption.orEmpty())
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

            ConditionalText(
                showError,
                selectedOption.orEmpty(),
                currentPrice
            )

        }

        RowButton(
            onCancelButtonClicked,
            onNavigateNext,
            showError
        )

    }
}

@Composable
fun ConditionalText(
    showError: Boolean,
    selectedOption: String,
    currentPrice: String
){
    if (showError) {
        ErrorText()
    } else {
        PriceSelectionText(
            selectedOption = selectedOption,
            currentPrice = currentPrice
        )
    }
}

@Composable
fun ErrorText(){
    Text(
        text = "Debe seleccionar una opción primero",
        modifier = Modifier.padding(
            top = dimensionResource(R.dimen.padding_small),
            bottom = dimensionResource(R.dimen.padding_medium)
        ),
        color = MaterialTheme.colorScheme.error,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun PriceSelectionText(
    selectedOption: String,
    currentPrice: String
){
    Text(
        buildAnnotatedString {
            append("Has seleccionado: ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(selectedOption)
            }

            appendLine()
            appendLine()

            append("Precio: ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)) {
                append(currentPrice)
            }
        },
        modifier = Modifier.padding(
            top = dimensionResource(R.dimen.padding_small),
            bottom = dimensionResource(R.dimen.padding_medium)
        )
    )
}

@Composable
fun RowButton(
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
        ButtonCancel(
            modifier = Modifier.weight(1f),
            onCancelButtonClicked = onCancelButtonClicked
        )
        ButtonNext(
            modifier = Modifier.weight(1f),
            onNavigateNext = onNavigateNext,
            showError = showError
        )
    }
}

@Composable
fun ButtonCancel(
    modifier: Modifier = Modifier,
    onCancelButtonClicked: () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onCancelButtonClicked
    ) {
        Text(stringResource(R.string.cancel))
    }
}

@Composable
fun ButtonNext(
    modifier: Modifier = Modifier,
    onNavigateNext: () -> Unit,
    showError: Boolean
) {
    Button(
        modifier = modifier,
        onClick = onNavigateNext,

        enabled = showError.not()
    ) {
        Text(stringResource(R.string.next))
    }
}