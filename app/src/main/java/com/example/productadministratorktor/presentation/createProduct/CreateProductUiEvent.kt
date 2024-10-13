package com.example.productadministratorktor.presentation.createProduct

sealed interface CreateProductUiEvent {
    data class ProductNameChanged(val name: String) : CreateProductUiEvent
    data class ProductPriceChanged(val price: String) : CreateProductUiEvent
    data object FormSubmit : CreateProductUiEvent
}