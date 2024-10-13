package com.example.productadministratorktor.presentation.updateProduct

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productadministratorktor.R
import com.example.productadministratorktor.data.ProductsRepository
import com.example.productadministratorktor.data.response.toProductUpdateState
import com.example.productadministratorktor.presentation.createProduct.SuccessfulResponseUiState
import com.example.productadministratorktor.presentation.createProduct.ValidationErrorsUiState
import com.example.productadministratorktor.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class UpdateProductScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val productsRepository: ProductsRepository
) : ViewModel() {
    private val productId: Int = checkNotNull(savedStateHandle["productId"])
    var productState by mutableStateOf(UpdateProductUiState())
    var errors by mutableStateOf(ValidationErrorsUiState())
    var success by mutableStateOf(SuccessfulResponseUiState())

    init {
        viewModelScope.launch {
            val actualProduct = productsRepository.getProductById(productId)
            actualProduct.collect { response ->
                when (response) {
                    is Response.Error -> {
                        productState = productState.copy(
                            error = response.error
                        )
                    }

                    is Response.Loading -> {
                        productState = productState.copy(
                            isLoading = response.isLoading
                        )
                    }

                    is Response.Success -> {
                        productState = productState.copy(
                            product = response.products.data.toProductUpdateState()
                        )
                    }
                }

            }
        }
    }

    fun onEvent(event: UpdateProductUiEvent) {
        when (event) {
            UpdateProductUiEvent.FormSubmit -> {
                if (validateProduct(productState.product)) {
                    viewModelScope.launch {
                        try {
                            productsRepository.updateProduct(
                                productId,
                                productState.product.toPartialProductDto()
                            )
                            success = success.copy(
                                isSuccessful = true,
                                message = R.string.producto_actualizado
                            )
                        } catch (e: IOException) {
                            success = success.copy(
                                isSuccessful = false,
                                message = R.string.error_al_actualizar_producto
                            )
                        }
                    }
                }
            }

            is UpdateProductUiEvent.ProductAvailabilityChanged -> {
                productState = productState.copy(
                    product = productState.product.copy(available = event.available)
                )
            }

            is UpdateProductUiEvent.ProductNameChanged -> {
                productState = productState.copy(
                    product = productState.product.copy(name = event.name)
                )
            }

            is UpdateProductUiEvent.ProductPriceChanged -> {
                productState = productState.copy(
                    product = productState.product.copy(price = event.price)
                )
            }
        }

    }

    private fun validateProduct(state: ProductState): Boolean {
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