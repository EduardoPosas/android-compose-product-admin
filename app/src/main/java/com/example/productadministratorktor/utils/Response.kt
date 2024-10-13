package com.example.productadministratorktor.utils

sealed interface Response<T> {
    data class Loading<T>(val isLoading: Boolean) : Response<T>
    data class Success<T>(val products: T) : Response<T>
    data class Error<T>(val error: String) : Response<T>
}