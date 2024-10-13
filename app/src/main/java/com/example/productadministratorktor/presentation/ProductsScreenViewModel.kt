package com.example.productadministratorktor.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productadministratorktor.data.ProductsRepository
import com.example.productadministratorktor.data.response.toProductDetails
import com.example.productadministratorktor.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsScreenViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : ViewModel() {
    private var _productsUiState = MutableStateFlow(ProductsUiState())
    val productsUiState = _productsUiState.asStateFlow().onStart {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
//            _productsUiState.update { it.copy(loading = true) }
            val productList = productsRepository.getProducts()
            productList.collect { response ->
                when (response) {
                    is Response.Error -> {
                        _productsUiState.update { prevState ->
                            prevState.copy(error = response.error)
                        }
                    }

                    is Response.Loading -> {
                        _productsUiState.update { prevState ->
                            prevState.copy(loading = response.isLoading)
                        }
                    }

                    is Response.Success -> {
                        _productsUiState.update { prevState ->
                            prevState.copy(products = response.products.data.map { product ->
                                product.toProductDetails()
                            })
                        }
                    }
                }
            }
        }
    }
}