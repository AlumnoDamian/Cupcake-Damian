package com.example.codelabnavigation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SelectOptionScreen(
    modifier: Modifier = Modifier,
    radioButtonList: List<String>,
    currentPrice: String,
    onSelectionChanged: (String) -> Unit,
    onNavigateNext: () -> Unit
) {
    var selectedOption by remember {
        mutableStateOf(radioButtonList[0])
    }
    var showError by remember { mutableStateOf(true) }

    Column(modifier = modifier) {
        radioButtonList.forEach {eachOption: String ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
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
                Text(
                    text = eachOption,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
        if (showError){
            Text(
                text = "Debe seleccionar una opción primero",
                modifier = Modifier.padding(vertical = 40.dp, horizontal = 10.dp),
                color = MaterialTheme.colorScheme.error,
                fontWeight = FontWeight(4)
            )
        }
        Text(
            text = "Has seleccionado: $selectedOption",
            modifier = Modifier.padding(vertical = 40.dp, horizontal = 10.dp)
        )
        Text(
            text = "Precio: $currentPrice",
            modifier = Modifier.padding(10.dp)
        )
        Button(
            onClick = {
                if (selectedOption.isBlank()){
                    showError = true
                } else {
                    showError = false
                    onNavigateNext()
                }
            }
        ) {
            Text(text = "Siguiente")
        }
    }
}
