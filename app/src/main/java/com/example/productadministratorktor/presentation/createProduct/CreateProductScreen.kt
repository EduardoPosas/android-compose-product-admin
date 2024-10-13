package com.example.productadministratorktor.presentation.createProduct

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.productadministratorktor.TopAppBar
import com.example.productadministratorktor.presentation.composables.FormTextField

@Composable
fun CreateProductScreen(
    createProductViewModel: CreateProductViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onNavigateToProducts: () -> Unit
) {
    val createProductState = createProductViewModel.partialProduct
    val validationErrors = createProductViewModel.errors
    val successfulResponseState = createProductViewModel.success
    val context = LocalContext.current


    LaunchedEffect(key1 = successfulResponseState.isSuccessful) {
        if (successfulResponseState.isSuccessful) {
            Toast.makeText(
                context,
                context.getString(successfulResponseState.message),
                Toast.LENGTH_LONG
            ).show()
            onNavigateToProducts()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Add Product",
                navigateUp = true,
                onNavigateUp = onNavigateUp
            )
        },
    ) { paddingValues ->
        CreateProductForm(
            createProductState = createProductState,
            formEvents = createProductViewModel::onEvent,
            validationErrors = validationErrors,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun CreateProductForm(
    createProductState: CreateProductUiState,
    formEvents: (CreateProductUiEvent) -> Unit,
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
            value = createProductState.name,
            onValueChange = { formEvents(CreateProductUiEvent.ProductNameChanged(it)) },
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
            value = createProductState.price,
            onValueChange = { formEvents(CreateProductUiEvent.ProductPriceChanged(it)) },
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
            imeAction = ImeAction.Done
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { formEvents(CreateProductUiEvent.FormSubmit) },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Text(text = "Crear Producto")
        }
    }
}

@Preview(
    showBackground = true,
    name = "Create Product Preview"
)
@Composable
fun CreateProductFormPreview() {
    MaterialTheme {
        val vm: CreateProductViewModel = hiltViewModel()
        CreateProductForm(
            createProductState = vm.partialProduct,
            formEvents = vm::onEvent,
            validationErrors = vm.errors
        )
    }
}