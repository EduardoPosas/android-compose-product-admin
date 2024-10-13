package com.example.productadministratorktor.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.productadministratorktor.Products

@Composable
fun ProductsNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Products) {
        products(
            onNavigateToProductInformation = { productId ->
                navController.navigateToProductInformation(productId)
            },
            onNavigateToCreateProduct = {
                navController.navigateToCreateProduct()
            }
        )
        productInformation(
            onNavigateUp = { navController.onNavigateUp() },
            onNavigateToUpdateProduct = { productId ->
                navController.navigateToUpdateProduct(productId)
            }
        )
        createProduct(
            onNavigateUp = { navController.onNavigateUp() },
            onNavigateToProducts = { navController.navigateToProducts() }
        )
        updateProduct(
            onNavigateUp = { navController.onNavigateUp() }
        )
    }
}