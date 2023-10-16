package com.catnip.retrofitexample.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.catnip.retrofitexample.data.network.api.datasource.ProductApiDataSource
import com.catnip.retrofitexample.data.network.api.datasource.ProductDataSource
import com.catnip.retrofitexample.data.network.api.service.ProductService
import com.catnip.retrofitexample.data.repository.ProductRepository
import com.catnip.retrofitexample.data.repository.ProductRepositoryImpl
import com.catnip.retrofitexample.databinding.ActivityMainBinding
import com.catnip.retrofitexample.utils.GenericViewModelFactory
import com.catnip.retrofitexample.utils.proceedWhen

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels {
        GenericViewModelFactory.create(createViewModel())
    }

    private fun createViewModel(): MainViewModel {
        val service = ProductService.invoke()
        val dataSource: ProductDataSource = ProductApiDataSource(service)
        val repo: ProductRepository = ProductRepositoryImpl(dataSource)
        return MainViewModel(repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeData()
        getProductData()
    }
    private fun getProductData(){
        viewModel.getProducts()
    }
    private fun observeData(){
        viewModel.responseLiveData.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    //add data to adapter
                    binding.tvData.text = it.payload.toString()
                },
                doOnLoading = {
                    //show loading state
                    binding.tvData.text = "Loading..."
                },
                doOnError = {
                    //show error state
                    binding.tvData.text = "Error : ${it.exception?.message}"
                }
            )
        }
    }
}