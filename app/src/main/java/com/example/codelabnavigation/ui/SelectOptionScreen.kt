package com.example.codelabnavigation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.codelabnavigation.R

@Composable
fun SelectOptionScreen(
    modifier: Modifier = Modifier,
    radioButtonList: List<String>,
    currentPrice: String,
    onSelectionChanged: (String) -> Unit,
    onNavigateNext: () -> Unit,
    onCancelButtonClicked: () -> Unit
) {
    var selectedOption by remember {
        mutableStateOf(radioButtonList[0])
    }
    var showError by remember { mutableStateOf(true) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {

            radioButtonList.forEach {eachOption: String ->

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.selectable(
                        selected = (selectedOption == eachOption),
                        onClick = {
                            if (selectedOption.isBlank()){
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
                            if (selectedOption.isBlank()){
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

            if (showError){
                Text(
                    text = "Debe seleccionar una opción primero",
                    modifier = Modifier.padding(
                        top = dimensionResource(R.dimen.padding_medium),
                        bottom = dimensionResource(R.dimen.padding_medium)
                    ),
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight(50)
                )
            } else {
                Column(
                    modifier = Modifier
                        .padding(
                            top = dimensionResource(R.dimen.padding_medium),
                            bottom = dimensionResource(R.dimen.padding_medium)
                        )
                ) {
                    Text(
                        text = "Has seleccionado: $selectedOption",
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                    )
                    Text(
                        text = "Precio: $currentPrice",
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = onCancelButtonClicked
            ) {
                Text(stringResource(R.string.cancel))
            }

            Button(
                modifier = Modifier.weight(1f),
                enabled = showError.not(),
                onClick = onNavigateNext
            ) {
                Text(stringResource(R.string.next))
            }
        }
    }
}