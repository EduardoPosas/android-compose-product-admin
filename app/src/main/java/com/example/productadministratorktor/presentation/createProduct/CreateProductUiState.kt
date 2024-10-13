package com.example.productadministratorktor.presentation.createProduct

import androidx.annotation.StringRes
import com.example.productadministratorktor.R
import com.example.productadministratorktor.data.response.PartialProductDto

data class CreateProductUiState(
    var name: String = "",
    val price: String = "",
)

data class ValidationErrorsUiState(
    var error: Boolean = false,
    var errorData: Map<String, String> = emptyMap()
)

data class SuccessfulResponseUiState(
    val isSuccessful: Boolean = false,
    @StringRes val message: Int = R.string.not_found
)

fun CreateProductUiState.toPartialProduct() = PartialProductDto(
    name = name,
    price = price.toDouble(),
)
