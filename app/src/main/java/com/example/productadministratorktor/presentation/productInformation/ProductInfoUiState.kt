package com.example.productadministratorktor.presentation.productInformation

import com.example.productadministratorktor.presentation.ProductDetails

data class ProductInfoUiState(
    val error: String = "",
    val productDetails: ProductDetails = ProductDetails(),
    val isLoading: Boolean = false
)

