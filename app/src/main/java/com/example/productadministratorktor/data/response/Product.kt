package com.example.productadministratorktor.data.response

import com.example.productadministratorktor.presentation.ProductDetails
import com.example.productadministratorktor.presentation.updateProduct.ProductState
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: Int,
    val name: String,
    val price: String,
    val available: Boolean
)

@Serializable
data class PartialProductDto(
    val name: String,
    val price: Double,
    val available: Boolean = true
)

fun ProductDto.toProductDetails() = ProductDetails(
    id = id,
    name = name,
    price = price,
    available = available
)

fun ProductDto.toProductUpdateState() = ProductState(
    name = name,
    price = price,
    available = available
)