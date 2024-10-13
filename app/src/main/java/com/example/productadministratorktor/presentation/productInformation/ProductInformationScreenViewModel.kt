package com.example.productadministratorktor.presentation.productInformation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productadministratorktor.R
import com.example.productadministratorktor.data.ProductsRepository
import com.example.productadministratorktor.data.response.toProductDetails
import com.example.productadministratorktor.presentation.createProduct.SuccessfulResponseUiState
import com.example.productadministratorktor.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ProductInformationScreenViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val productId: Int = checkNotNull(savedStateHandle["productId"])
    private val _productDetails = MutableStateFlow(ProductInfoUiState())
    val productDetails = _productDetails.asStateFlow().onStart {
        getProductDetails(productId)
    }
    var deleteSuccessful by mutableStateOf(SuccessfulResponseUiState())
    private var _updateAvailabilitySuccess = MutableStateFlow(SuccessfulResponseUiState())
    val updateAvailabilitySuccess = _updateAvailabilitySuccess.asStateFlow()

    private fun getProductDetails(id: Int) {
        viewModelScope.launch {
            val productDetails = productsRepository.getProductById(id)
            productDetails.collect { productData ->
                when (productData) {
                    is Response.Error -> {
                        _productDetails.update {
                            it.copy(error = productData.error)
                        }
                    }

                    is Response.Loading -> {
                        _productDetails.update {
                            it.copy(isLoading = productData.isLoading)
                        }
                    }

                    is Response.Success -> {
                        _productDetails.update {
                            it.copy(productDetails = productData.products.data.toProductDetails())
                        }
                    }
                }
            }
        }
    }

    fun deleteProduct(productId: Int) {
        try {
            viewModelScope.launch {
                productsRepository.deleteProduct(productId)
                deleteSuccessful = deleteSuccessful.copy(
                    isSuccessful = true,
                    message = R.string.producto_eliminado
                )
            }
        } catch (e: IOException) {
            deleteSuccessful = deleteSuccessful.copy(
                isSuccessful = false,
                message = R.string.error_al_eliminar_producto
            )
        }
    }

    fun updateAvailability(productId: Int) {
        try {
            viewModelScope.launch {
                productsRepository.updateAvailability(productId)
                _updateAvailabilitySuccess.update {
                    it.copy(
                        isSuccessful = true,
                        message = R.string.disponibilidad_actualizada
                    )
                }
                delay(500L)
                getProductDetails(productId)
            }
        } catch (e: IOException) {
            _updateAvailabilitySuccess.update {
                it.copy(
                    isSuccessful = false,
                    message = R.string.error_al_actualizar_disponibilidad
                )
            }
        }
    }
}