package com.example.productadministratorktor.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.productadministratorktor.CreateProduct
import com.example.productadministratorktor.ProductInformation
import com.example.productadministratorktor.Products
import com.example.productadministratorktor.UpdateProduct
import com.example.productadministratorktor.presentation.ProductsScreen
import com.example.productadministratorktor.presentation.createProduct.CreateProductScreen
import com.example.productadministratorktor.presentation.productInformation.ProductInformationScreen
import com.example.productadministratorktor.presentation.updateProduct.UpdateProductScreen

fun NavGraphBuilder.products(
    onNavigateToProductInformation: (Int) -> Unit,
    onNavigateToCreateProduct: () -> Unit
) {
    composable<Products> {
        ProductsScreen(
            onNavigateToProductInformation = onNavigateToProductInformation,
            onNavigateToCreateProduct = onNavigateToCreateProduct
        )
    }
}

fun NavController.navigateToProductInformation(productId: Int) {
    navigate(ProductInformation(productId))
}

fun NavController.navigateToCreateProduct() {
    navigate(CreateProduct)
}

fun NavGraphBuilder.productInformation(
    onNavigateUp: () -> Unit,
    onNavigateToUpdateProduct: (Int) -> Unit
) {
    composable<ProductInformation> {
        ProductInformationScreen(
            onNavigateUp = onNavigateUp,
            onNavigateToUpdateProduct = onNavigateToUpdateProduct
        )
    }
}

fun NavController.navigateToUpdateProduct(productId: Int) {
    navigate(UpdateProduct(productId))
}

fun NavGraphBuilder.createProduct(
    onNavigateUp: () -> Unit,
    onNavigateToProducts: () -> Unit
) {
    composable<CreateProduct> {
        CreateProductScreen(
            onNavigateUp = onNavigateUp,
            onNavigateToProducts = onNavigateToProducts
        )
    }
}

fun NavController.navigateToProducts() {
    navigate(Products)
}

fun NavController.onNavigateUp() {
    navigateUp()
}

fun NavGraphBuilder.updateProduct(
    onNavigateUp: () -> Unit
) {
    composable<UpdateProduct> {
        UpdateProductScreen(
            onNavigateUp = onNavigateUp
        )
    }
}

