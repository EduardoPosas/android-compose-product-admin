package com.example.productadministratorktor.data

import com.example.productadministratorktor.data.response.PartialProductDto
import com.example.productadministratorktor.data.response.ProductData
import com.example.productadministratorktor.data.response.ProductsDataOrError
import com.example.productadministratorktor.utils.Response
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    fun getProducts(): Flow<Response<ProductsDataOrError.ProductsDto>>
    suspend fun getProductById(id: Int): Flow<Response<ProductData>>
    suspend fun createProduct(content: PartialProductDto)
    suspend fun updateProduct(id: Int, content: PartialProductDto)
    suspend fun deleteProduct(id: Int)
    suspend fun updateAvailability(id: Int)
}