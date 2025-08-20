package com.marcelo.loteriadossonhos.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoTextDropDown(initial: String, label: String, list: List<String>) {
    var dropDownExpanded by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf(TextFieldValue(initial)) }

    ExposedDropdownMenuBox(
        expanded = dropDownExpanded,
        onExpandedChange = {
            dropDownExpanded = !dropDownExpanded
        }
    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            value = textFieldValue,
            onValueChange = {

            },
            readOnly = true,
            label = {
                Text(label)
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropDownExpanded)
            }
        )

        ExposedDropdownMenu(
            expanded = dropDownExpanded,
            onDismissRequest = { dropDownExpanded = false }
        ) {
            list.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(item)
                    },
                    onClick = {
                        textFieldValue = TextFieldValue(item)
                        dropDownExpanded = false
                    })
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun AutoTextDropDownPreview() {
    AutoTextDropDown("", "", listOf())
}