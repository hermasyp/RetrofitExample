package com.catnip.retrofitexample.data.network.datasource

import com.catnip.retrofitexample.data.model.ProductsResponse
import com.catnip.retrofitexample.data.network.service.ProductService

interface ProductDataSource {
    suspend fun getProducts(): ProductsResponse
}

class ProductDataSourceImpl(private val service: ProductService) : ProductDataSource {
    override suspend fun getProducts(): ProductsResponse {
        return service.getProducts()
    }
}