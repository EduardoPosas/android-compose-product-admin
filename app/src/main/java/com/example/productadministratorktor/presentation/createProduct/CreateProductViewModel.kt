package com.example.productadministratorktor.presentation.createProduct

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productadministratorktor.R
import com.example.productadministratorktor.data.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CreateProductViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : ViewModel() {
    var partialProduct by mutableStateOf(CreateProductUiState())
    var errors by mutableStateOf(ValidationErrorsUiState())
    var success by mutableStateOf(SuccessfulResponseUiState())

    fun onEvent(event: CreateProductUiEvent) {
        when (event) {
            CreateProductUiEvent.FormSubmit -> {
                // Validate product data
                if (validateProduct(partialProduct)) {
                    viewModelScope.launch {
                        try {
                            productsRepository.createProduct(partialProduct.toPartialProduct())
                            success = success.copy(
                                isSuccessful = true,
                                message = R.string.producto_creado
                            )
                        } catch (e: IOException) {
                            success = success.copy(
                                isSuccessful = false,
                                message = R.string.failed_to_create_product
                            )
                        }
                    }
                }
            }

            is CreateProductUiEvent.ProductNameChanged -> {
                partialProduct = partialProduct.copy(name = event.name)
            }

            is CreateProductUiEvent.ProductPriceChanged -> {
                partialProduct = partialProduct.copy(price = event.price)
            }
        }
    }

    private fun validateProduct(state: CreateProductUiState): Boolean {
        val errorsMap = mutableMapOf<String, String>()

        if (state.name.isBlank()) {
            errorsMap["name"] = "Campo Obligatorio"
        }
        if (state.price.isBlank()) {
            errorsMap["price"] = "Campo Obligatorio"
        }
        if ((state.price.toDoubleOrNull() ?: 0.0) <= 0.0 && state.price.isNotBlank()) {
            errorsMap["price"] = "El precio debe ser mayor a 0"
        }

        errors = errors.copy(
            error = errorsMap.keys.isNotEmpty(),
            errorData = errorsMap
        )
        return errorsMap.keys.isEmpty()
    }
}