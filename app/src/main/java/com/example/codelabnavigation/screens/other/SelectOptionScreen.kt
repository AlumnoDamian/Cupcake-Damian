package com.example.codelabnavigation.screens.other

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
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
    /* Estado mutable para la opción seleccionada y para controlar si se debe mostrar un error */
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var showError by remember { mutableStateOf(true) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        /* Sección de radioButtonList y SelectionDetailsText */
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            radioButtonList.forEach { eachOption: String ->
                /* Llama a la función RadioOptionList para cada opción de radio */
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (selectedOption == eachOption),
                            onClick = {
                                if (selectedOption == eachOption){
                                    selectedOption  = null
                                    showError = true
                                } else{
                                    selectedOption = eachOption
                                    showError = false
                                    onSelectionChanged(selectedOption.orEmpty())
                                }
                            }
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    /* Botón de radio para seleccionar la opción */
                    RadioButton(
                        selected = (selectedOption == eachOption),
                        onClick = {
                            if (selectedOption == eachOption){
                                selectedOption  = null
                                showError = true
                            } else{
                                selectedOption = eachOption
                                showError = false
                                onSelectionChanged(selectedOption.orEmpty())
                            }
                        }
                    )
                    /* Texto que muestra la opción */
                    Text(text = eachOption)
                }
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
        /* Sección de radioButtonList y SelectionDetailsText */

        /* Sección de botones (Cancelar y Siguiente) */
        ButtonSection(
            onCancelButtonClicked = onCancelButtonClicked,
            onNavigateNext = onNavigateNext,
            showError = showError
        )
        /* Sección de botones (Cancelar y Siguiente) */
    }
}

@Composable
fun SelectionDetailsText(
    showError: Boolean,
    selectedOption: String,
    currentPrice: String
) {
    /* Mensaje de error predeterminado */
    val errorText = "Debe seleccionar una opción primero"

    /* Construye un texto con formato que muestra la selección y el precio */
    val textDetails = "Has seleccionado: $selectedOption | Precio: $currentPrice"

    /* Texto condicional que muestra el mensaje de error o los detalles de selección */
    Text(
        text = if (showError) errorText else textDetails,
        modifier = Modifier.padding(
            top = dimensionResource(R.dimen.padding_small),
            bottom = dimensionResource(R.dimen.padding_medium)
        ),
        color = if (showError) MaterialTheme.colorScheme.error else Color.Black,
    )
}

@Composable
fun ButtonSection(
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
        /* Botón Siguiente con estado habilitado o deshabilitado según el estado de error */
        NextButton(
            modifier = Modifier.weight(1f),
            onClick = onNavigateNext,
            labelResourceId = R.string.next,
            isEnabled = showError.not()
        )
    }
}