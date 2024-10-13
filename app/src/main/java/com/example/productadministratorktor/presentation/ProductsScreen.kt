package com.example.productadministratorktor.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.productadministratorktor.FloatingButton
import com.example.productadministratorktor.ProgressIndicator
import com.example.productadministratorktor.R
import com.example.productadministratorktor.TopAppBar
import java.util.Locale

@Composable
fun ProductsScreen(
    modifier: Modifier = Modifier,
    productsViewModel: ProductsScreenViewModel = hiltViewModel(),
    onNavigateToProductInformation: (Int) -> Unit,
    onNavigateToCreateProduct: () -> Unit
) {
    val productsState by productsViewModel.productsUiState.collectAsStateWithLifecycle(initialValue = ProductsUiState())

    Scaffold(
        modifier = modifier.fillMaxWidth(),
        topBar = {
            TopAppBar(title = stringResource(R.string.productos))
        },
        floatingActionButton = {
            FloatingButton(
                onNavigateToScreen = onNavigateToCreateProduct
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
    ) { paddingValues ->

        if (productsState.loading) {
            ProductsLoading(Modifier.padding(paddingValues))
        }

        if (productsState.error.isNotEmpty()) {
            ProductsError(error = productsState.error, modifier = Modifier.padding(paddingValues))
        }

        ProductsList(
            products = productsState.products,
            modifier = Modifier.padding(paddingValues),
            onNavigateToProductInformation = onNavigateToProductInformation
        )
    }
}

@Composable
fun ProductsLoading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ProgressIndicator()
    }
}

@Composable
fun ProductsError(
    error: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = error)
    }
}

@Composable
fun ProductsList(
    products: List<ProductDetails>,
    modifier: Modifier = Modifier,
    onNavigateToProductInformation: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(products, key = { it.name }) { product ->
            ProductItem(
                product = product,
                onNavigateToProductInformation = onNavigateToProductInformation
            )
        }
    }
}

@Composable
fun ProductItem(
    product: ProductDetails,
    onNavigateToProductInformation: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onNavigateToProductInformation(product.id!!)
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
        ),
//        elevation = CardDefaults.cardElevation(
//            defaultElevation = 16.dp
//        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = product.name!!,
                    modifier = Modifier.weight(1F),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Text(text = String.format(Locale.US, product.price))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (product.available) stringResource(R.string.disponible) else stringResource(
                    R.string.no_disponible
                ),
                style = MaterialTheme.typography.bodyMedium,

                )
        }

    }
}