package com.example.productadministratorktor.presentation.productInformation

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.productadministratorktor.FloatingButton
import com.example.productadministratorktor.ProgressIndicator
import com.example.productadministratorktor.TopAppBar
import com.example.productadministratorktor.presentation.ProductDetails

@Composable
fun ProductInformationScreen(
    onNavigateUp: () -> Unit,
    onNavigateToUpdateProduct: (Int) -> Unit,
    productInfoViewModel: ProductInformationScreenViewModel = hiltViewModel(),
) {

    val productDetails by productInfoViewModel.productDetails.collectAsStateWithLifecycle(
        initialValue = ProductInfoUiState()
    )
    val deleteSuccess = productInfoViewModel.deleteSuccessful
    val availabilitySuccess by productInfoViewModel.updateAvailabilitySuccess.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = deleteSuccess.isSuccessful, availabilitySuccess.isSuccessful) {
        if (deleteSuccess.isSuccessful) {
            Toast.makeText(
                context,
                context.getString(deleteSuccess.message),
                Toast.LENGTH_LONG
            ).show()
            onNavigateUp()
        }
        if (availabilitySuccess.isSuccessful) {
            Toast.makeText(
                context,
                context.getString(availabilitySuccess.message),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Product Details",
                navigateUp = true,
                onNavigateUp = onNavigateUp
            )
        },
        floatingActionButton = {
            FloatingButton(onNavigateToScreen = {
                onNavigateToUpdateProduct(productDetails.productDetails.id!!)
            }) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "edit product action")
            }
        }
    ) { paddingValues ->

        if (productDetails.isLoading) {
            ProductDetailsLoading(modifier = Modifier.padding(paddingValues))
        }

        if (productDetails.error.isNotBlank()) {
            ProductDetailsError(
                error = productDetails.error,
                modifier = Modifier.padding(paddingValues)
            )
        }

        ProductDetails(
            product = productDetails.productDetails,
            deleteProduct = productInfoViewModel::deleteProduct,
            updateAvailability = productInfoViewModel::updateAvailability,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun ProductDetailsLoading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ProgressIndicator()
    }
}

@Composable
fun ProductDetailsError(
    error: String,
    modifier: Modifier = Modifier,
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
fun ProductDetails(
    product: ProductDetails,
    deleteProduct: (Int) -> Unit,
    updateAvailability: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                FilterChip(
                    selected = product.available,
                    onClick = { updateAvailability(product.id!!) },
                    label = { Text(text = if (product.available) "Disponible" else "No Disponible") },
                    leadingIcon = {
                        if (product.available) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Product available"
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Product not available"
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$ ${product.price}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedButton(
            onClick = { deleteProduct(product.id!!) },
            border = BorderStroke(
                color = MaterialTheme.colorScheme.tertiaryContainer,
                width = 1.dp
            ),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.onBackground
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Eliminar Producto")
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.DeleteForever,
                    contentDescription = "Delete Product"
                )
            }
        }
    }
}
