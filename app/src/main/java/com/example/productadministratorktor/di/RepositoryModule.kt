package com.example.productadministratorktor.di

import com.example.productadministratorktor.data.ProductsRepository
import com.example.productadministratorktor.data.ProductsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindProductsRepository(productsRepositoryImpl: ProductsRepositoryImpl): ProductsRepository
}