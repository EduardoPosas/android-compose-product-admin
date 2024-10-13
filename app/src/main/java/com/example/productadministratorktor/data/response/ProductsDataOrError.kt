package com.example.productadministratorktor.data.response

import kotlinx.serialization.Serializable


sealed interface ProductsDataOrError {
    @Serializable
    data class ProductsDto(val data: List<ProductDto> ) : ProductsDataOrError
    data class ResponseError(val error: String) : ProductsDataOrError
}