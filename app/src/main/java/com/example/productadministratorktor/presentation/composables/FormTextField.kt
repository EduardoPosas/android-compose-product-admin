package com.example.productadministratorktor.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    imageVector: ImageVector,
    contentDescription: String,
    isError: Boolean,
    supportingText: @Composable() (() -> Unit)? = null,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    focusManager: FocusManager? = null
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = label) },
        leadingIcon = {
            Icon(imageVector = imageVector, contentDescription = contentDescription)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager?.clearFocus() }
        ),
        singleLine = true,
        isError = isError,
        supportingText = supportingText,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            unfocusedTextColor = MaterialTheme.colorScheme.onTertiaryContainer,
            focusedTextColor = MaterialTheme.colorScheme.onTertiaryContainer,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
            focusedIndicatorColor = MaterialTheme.colorScheme.background,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
            errorIndicatorColor = MaterialTheme.colorScheme.background
        ),
        shape = MaterialTheme.shapes.small
    )
}