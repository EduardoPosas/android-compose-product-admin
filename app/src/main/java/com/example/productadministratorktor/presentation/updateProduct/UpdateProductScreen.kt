package com.example.productadministratorktor.presentation.updateProduct

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.productadministratorktor.ProgressIndicator
import com.example.productadministratorktor.TopAppBar
import com.example.productadministratorktor.presentation.composables.FormTextField
import com.example.productadministratorktor.presentation.createProduct.ValidationErrorsUiState
import kotlinx.coroutines.isActive

@Composable
fun UpdateProductScreen(
    onNavigateUp: () -> Unit,
    updateProductViewModel: UpdateProductScreenViewModel = hiltViewModel(),
) {
    val productState = updateProductViewModel.productState
    val product = updateProductViewModel.productState.product
    val errors = updateProductViewModel.errors
    val success = updateProductViewModel.success
    val context = LocalContext.current

    LaunchedEffect(key1 = success.isSuccessful) {
        if (success.isSuccessful) {
            Toast.makeText(
                context,
                context.getString(success.message),
                Toast.LENGTH_LONG
            ).show()
            onNavigateUp()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Edit Product",
                navigateUp = true,
                onNavigateUp = onNavigateUp
            )
        },
    ) { paddingValues ->
        if (productState.isLoading) {
            ProductFormLoading()
        }
        UpdateProductForm(
            updateProductState = product,
            formEvents = updateProductViewModel::onEvent,
            validationErrors = errors,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun ProductFormLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        ProgressIndicator()
    }
}

@Composable
fun UpdateProductForm(
    updateProductState: ProductState,
    formEvents: (UpdateProductUiEvent) -> Unit,
    validationErrors: ValidationErrorsUiState,
    modifier: Modifier = Modifier
) {

    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        FormTextField(
            value = updateProductState.name,
            onValueChange = { formEvents(UpdateProductUiEvent.ProductNameChanged(it)) },
            label = "Nombre",
            imageVector = Icons.Filled.Person,
            contentDescription = "product name",
            isError = validationErrors.error && !validationErrors.errorData["name"].isNullOrBlank(),
            supportingText = {
                if (!validationErrors.errorData["name"].isNullOrBlank()) {
                    Text(text = validationErrors.errorData["name"] ?: "")
                }
            },
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )
        FormTextField(
            value = updateProductState.price,
            onValueChange = { formEvents(UpdateProductUiEvent.ProductPriceChanged(it)) },
            label = "Precio",
            imageVector = Icons.Default.AttachMoney,
            contentDescription = "product price",
            isError = validationErrors.error && !validationErrors.errorData["price"].isNullOrBlank(),
            supportingText = {
                if (!validationErrors.errorData["price"].isNullOrBlank()) {
                    Text(text = validationErrors.errorData["price"] ?: "")
                }
            },
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done,
            focusManager = focusManager
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = "Is Available:")
            Checkbox(
                checked = updateProductState.available,
                onCheckedChange = { formEvents(UpdateProductUiEvent.ProductAvailabilityChanged(it)) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { formEvents(UpdateProductUiEvent.FormSubmit) },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Text(text = "Actualizar Producto")
        }
    }
}