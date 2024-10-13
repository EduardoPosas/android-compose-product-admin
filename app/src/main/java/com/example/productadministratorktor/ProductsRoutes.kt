package com.example.productadministratorktor

import kotlinx.serialization.Serializable

@Serializable
object Products

@Serializable
data class ProductInformation(val productId: Int)

@Serializable
data object CreateProduct

@Serializable
data class UpdateProduct(val productId: Int)