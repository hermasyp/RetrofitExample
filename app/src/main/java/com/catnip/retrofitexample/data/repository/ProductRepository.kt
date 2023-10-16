package com.catnip.retrofitexample.data.repository

import com.catnip.retrofitexample.data.network.api.datasource.ProductDataSource
import com.catnip.retrofitexample.model.ProductViewParam
import com.catnip.retrofitexample.model.toProductViewParams
import com.catnip.retrofitexample.utils.ResultWrapper
import com.catnip.retrofitexample.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface ProductRepository {
    suspend fun getProducts(): Flow<ResultWrapper<List<ProductViewParam>>>
}

class ProductRepositoryImpl(private val dataSource: ProductDataSource) : ProductRepository {
    override suspend fun getProducts(): Flow<ResultWrapper<List<ProductViewParam>>> {
        return proceedFlow { dataSource.getProducts().products.toProductViewParams() }
    }
}