package com.marcelo.loteriadossonhos.component

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.marcelo.loteriadossonhos.R


@Composable
fun CustomTextField(
    value: String,
    @StringRes placeholder: Int,
    imeAction: ImeAction = ImeAction.Next,
    onValueChange: (String) -> Unit,
) {

    OutlinedTextField(
        value = value,
        maxLines = 1,
        placeholder = {
            Text(stringResource(id = placeholder))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        ),
        onValueChange = onValueChange

    )
}

@Preview(showBackground = true)
@Composable
private fun CustomTextFieldPreiew() {
    CustomTextField(
        value = "",
        placeholder = R.string.label_trevo,
        imeAction = ImeAction.Next) {

    }
}