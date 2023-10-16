package com.catnip.retrofitexample.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.retrofitexample.data.network.api.service.ProductService
import com.catnip.retrofitexample.data.network.api.model.ProductsResponse
import com.catnip.retrofitexample.data.repository.ProductRepository
import com.catnip.retrofitexample.model.ProductViewParam
import com.catnip.retrofitexample.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class MainViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _responseLiveData = MutableLiveData<ResultWrapper<List<ProductViewParam>>>()
    val responseLiveData: LiveData<ResultWrapper<List<ProductViewParam>>>
        get() = _responseLiveData

    fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getProducts().collect {
                _responseLiveData.postValue(it)
            }
        }
    }
}