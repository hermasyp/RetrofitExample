package com.catnip.retrofitexample.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.retrofitexample.data.model.ProductsResponse
import com.catnip.retrofitexample.data.network.service.ProductService
import com.catnip.retrofitexample.data.repository.ProductRepository
import com.catnip.retrofitexample.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class MainViewModel (
    private val productRepo: ProductRepository
) : ViewModel() {
    val service: ProductService by lazy {
        ProductService.invoke()
    }
    val responseLiveData = MutableLiveData<ResultWrapper<ProductsResponse>>()

    fun getProducts() {
        viewModelScope.launch(Dispatchers.IO){
            productRepo.getProducts().collect{
                responseLiveData.postValue(it)
            }
        }
    }
}