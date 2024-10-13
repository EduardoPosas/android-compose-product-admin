package com.example.productadministratorktor.data

import android.util.Log
import com.example.productadministratorktor.data.response.PartialProductDto
import com.example.productadministratorktor.data.response.ProductData
import com.example.productadministratorktor.data.response.ProductsDataOrError
import com.example.productadministratorktor.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val productsDatasource: ProductsDatasource
) : ProductsRepository {
    override fun getProducts(): Flow<Response<ProductsDataOrError.ProductsDto>> = flow {
        emit(Response.Loading(isLoading = true))
        Log.d("REPO", "loading... true")
        when (val products = productsDatasource.getProducts()) {
            is ProductsDataOrError.ProductsDto -> {
                Log.d("REPO", products.data.toString())
                emit(Response.Success(products = products))
            }

            is ProductsDataOrError.ResponseError -> {
                Log.d("REPO", products.error)
                emit(Response.Error(error = products.error))
            }
        }
        Log.d("REPO", "loading... false")
        emit(Response.Loading(isLoading = false))
    }

    override suspend fun getProductById(id: Int): Flow<Response<ProductData>> = flow {
        emit(Response.Loading(isLoading = true))
        Log.d("REPO", "LOADING .... TRUE")
        try {
            val product = productsDatasource.getProductById(id)
            emit(Response.Success(product))
            Log.d("REPO", "PRODUCT: ${product.data.toString()}")
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("REPO", e.message!!)
            emit(Response.Error(error = e.message!!))
        } finally {
            emit(Response.Loading(isLoading = false))
            Log.d("REPO", "LOADING ... FALSE")
        }
    }

    override suspend fun createProduct(content: PartialProductDto) {
        productsDatasource.createProduct(content)
    }

    override suspend fun updateProduct(id: Int, content: PartialProductDto) {
        productsDatasource.updateProduct(id, content)
    }

    override suspend fun deleteProduct(id: Int) {
        productsDatasource.deleteProduct(id)
    }

    override suspend fun updateAvailability(id: Int) {
        productsDatasource.updateAvailability(id)
    }
}
