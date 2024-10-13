package com.example.productadministratorktor.data

import android.util.Log
import com.example.productadministratorktor.data.resources.Products
import com.example.productadministratorktor.data.response.PartialProductDto
import com.example.productadministratorktor.data.response.ProductData
import com.example.productadministratorktor.data.response.ProductsDataOrError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.resources.delete
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.patch
import io.ktor.client.plugins.resources.post
import io.ktor.client.plugins.resources.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.errors.IOException
import javax.inject.Inject

class ProductsDatasource @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getProducts(): ProductsDataOrError {
        try {
            val response = client.get(Products())
            val body: ProductsDataOrError.ProductsDto = response.body()
            Log.d("RESPONSE", body.toString())
            return body
        } catch (e: ResponseException) {
            e.printStackTrace()
            Log.d("RESPONSE", e.message ?: "")
            return ProductsDataOrError.ResponseError(
                error = e.message ?: "Failed to fetch products data"
            )
        }
    }

    suspend fun getProductById(id: Int): ProductData {
        val response = client.get(Products.Id(id = id))
        val body: ProductData = response.body()
        Log.d("RESPONSE", body.toString())
        return body
    }

    suspend fun createProduct(content: PartialProductDto) {
        val response = client.post(Products()) {
            contentType(ContentType.Application.Json)
            setBody(content)
        }
        if (response.status.value != 201) {
            throw IOException()
        }
    }

    suspend fun updateProduct(id: Int, content: PartialProductDto) {
        val response = client.put(Products.Id(id = id)) {
            contentType(ContentType.Application.Json)
            setBody(content)
        }
        if (response.status.value != 200) {
            throw IOException()
        }
    }

    suspend fun updateAvailability(id: Int) {
        val response = client.patch(Products.Id(id = id))
        if (response.status.value != 200) {
            throw IOException()
        }
    }

    suspend fun deleteProduct(id: Int) {
        val response = client.delete(Products.Id(id = id))
        if (response.status.value != 204) {
            throw IOException()
        }
    }
}