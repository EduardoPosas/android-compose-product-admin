package com.example.productadministratorktor.presentation.updateProduct

import com.example.productadministratorktor.data.response.PartialProductDto

data class UpdateProductUiState(
    val error: String = "",
    val isLoading: Boolean = false,
    val product: ProductState = ProductState()
)


data class ProductState(
    val name: String = "",
    val price: String = "",
    val available: Boolean = true
)

fun ProductState.toPartialProductDto() = PartialProductDto(
    name = name,
    price = price.toDouble(),
    available = available
)
