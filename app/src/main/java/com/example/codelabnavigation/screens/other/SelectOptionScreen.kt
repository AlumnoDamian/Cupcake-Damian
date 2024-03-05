package com.example.codelabnavigation.screens.other

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.example.codelabnavigation.R
import com.example.codelabnavigation.screens.components.CancelButton
import com.example.codelabnavigation.screens.components.NextButton


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
        /* Columna que contiene la lista de opciones de radio */
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            radioButtonList.forEach { eachOption: String ->
                /* Llama a la función RadioOptionList para cada opción de radio */
                RadioOptionItem(
                    isSelected = (selectedOption == eachOption),
                    onSelectionChanged = {
                        selectedOption = if (selectedOption == eachOption) null else eachOption
                        showError = (selectedOption == null)
                        onSelectionChanged(selectedOption.orEmpty())
                    },
                    eachOption = eachOption
                )
            }

            /* Línea divisora entre la lista de opciones de radio y el texto condicional */
            Divider(
                thickness = dimensionResource(R.dimen.thickness_divider),
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
            )

            /* Texto condicional que muestra el mensaje de error o los detalles de selección */
            SelectionDetailsText(
                showError = showError,
                selectedOption = selectedOption.orEmpty(),
                currentPrice = currentPrice
            )
        }

        /* Botones de fila (Cancelar y Siguiente) */
        RowButton(
            onCancelButtonClicked = onCancelButtonClicked,
            onNavigateNext = onNavigateNext,
            showError = showError
        )
    }
}

@Composable
private fun RadioOptionItem(
    isSelected: Boolean,
    onSelectionChanged: () -> Unit,
    eachOption: String
) {
    /* Fila que contiene el botón de radio y el texto para cada opción */
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = isSelected,
                onClick = onSelectionChanged
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        /* Botón de radio para seleccionar la opción */
        RadioButton(
            selected = isSelected,
            onClick = onSelectionChanged
        )
        /* Texto que muestra la opción */
        Text(text = eachOption)
    }
}

@Composable
fun SelectionDetailsText(
    showError: Boolean,
    selectedOption: String,
    currentPrice: String
) {
    val errorText = "Debe seleccionar una opción primero"
    val textDetails =
        buildAnnotatedString {
            /* Agrega el texto de la selección y el precio con formato */
            append("Has seleccionado: ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("$selectedOption\n\n")
            }
            append("Precio: ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(currentPrice)
            }
        }

    /* Texto condicional que muestra el mensaje de error o los detalles de selección */
    Text(
        text = if (showError) errorText else textDetails.toString(),
        modifier = Modifier.padding(
            top = dimensionResource(R.dimen.padding_small),
            bottom = dimensionResource(R.dimen.padding_medium)
        ),
        color = if (showError) MaterialTheme.colorScheme.error else Color.Black,
        fontWeight = if (showError) FontWeight.Bold else FontWeight.Normal
    )
}

@Composable
fun RowButton(
    onCancelButtonClicked: () -> Unit,
    onNavigateNext: () -> Unit,
    showError: Boolean
) {
    /* Fila que contiene los botones de Cancelar y Siguiente */
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        verticalAlignment = Alignment.Bottom
    ) {
        /* Botón Cancelar */
        CancelButton(
            modifier = Modifier.weight(1f),
            onClick = onCancelButtonClicked,
            labelResourceId = R.string.cancel,
        )
        /* Botón Siguiente con estado habilitado o deshabilitado */
        NextButton(
            modifier = Modifier.weight(1f),
            onClick = onNavigateNext,
            labelResourceId = R.string.next,
            isEnabled = showError.not()
        )
    }
}