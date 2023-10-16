package com.catnip.retrofitexample.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.catnip.retrofitexample.data.network.datasource.ProductDataSourceImpl
import com.catnip.retrofitexample.data.network.service.ProductService
import com.catnip.retrofitexample.data.repository.ProductRepository
import com.catnip.retrofitexample.data.repository.ProductRepositoryImpl
import com.catnip.retrofitexample.databinding.ActivityMainBinding
import com.catnip.retrofitexample.utils.GenericViewModelFactory
import com.catnip.retrofitexample.utils.proceedWhen

class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val adapterProduct = MainListAdapter()

    private val viewModel : MainViewModel by viewModels {
        val service = ProductService.invoke()
        val dataSource = ProductDataSourceImpl(service)
        val repo : ProductRepository = ProductRepositoryImpl(dataSource)
        GenericViewModelFactory.create(MainViewModel(repo))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRvListProduct()
        observeData()
        getData()
    }

    private fun setupRvListProduct() {
        binding.rvProduct.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = adapterProduct
            adapterProduct.refreshList()
        }
    }

    private fun observeData() {
        viewModel.responseLiveData.observe(this){
            it.proceedWhen(
                doOnSuccess = {
                    binding.rvProduct.isVisible = true
                    binding.commonLayoutState.root.isVisible = false
                    binding.commonLayoutState.tvError.isVisible = false
                    binding.rvProduct.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = adapterProduct
                    }
                    it.payload?.let {
                        adapterProduct.setData(it.products)
                    }
                },
                doOnLoading = {
                    binding.commonLayoutState.root.isVisible = true
                    binding.commonLayoutState.tvError.isVisible = false
                    binding.rvProduct.isVisible = false
                },
                doOnError = { err ->
                    binding.commonLayoutState.root.isVisible = true
                    binding.commonLayoutState.tvError.isVisible = true
                    binding.commonLayoutState.tvError.text = err.exception?.message.orEmpty()
                    binding.rvProduct.isVisible = false
                },
                doOnEmpty = {
                    binding.commonLayoutState.root.isVisible = true
                    binding.commonLayoutState.tvError.isVisible = true
                    binding.commonLayoutState.tvError.text = "Empty"
                    binding.rvProduct.isVisible = false
                }
            )
        }
    }

    private fun getData() {
        viewModel.getProducts()
    }

}
