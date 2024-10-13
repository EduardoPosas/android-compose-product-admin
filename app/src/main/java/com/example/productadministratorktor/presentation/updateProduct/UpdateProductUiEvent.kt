package com.example.productadministratorktor.presentation.updateProduct

sealed interface UpdateProductUiEvent {
    data class ProductNameChanged(val name: String) : UpdateProductUiEvent
    data class ProductPriceChanged(val price: String) : UpdateProductUiEvent
    data class ProductAvailabilityChanged(val available: Boolean) : UpdateProductUiEvent
    data object FormSubmit : UpdateProductUiEvent
}