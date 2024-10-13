package com.example.productadministratorktor.presentation


data class ProductsUiState(
    val loading: Boolean = false,
    val products: List<ProductDetails> = emptyList(),
    val error: String = ""
)

data class ProductDetails(
    val id: Int? = null,
    val name: String = "",
    val price: String = "",
    val available: Boolean = true
)
