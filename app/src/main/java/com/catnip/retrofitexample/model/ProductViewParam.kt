package com.catnip.retrofitexample.model

import com.catnip.retrofitexample.data.network.api.model.Product

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
data class ProductViewParam(
    val id: Long,
    val title: String,
    val desc: String,
    val price: Long,
    val images: List<String>
)

fun Product.toProductViewParam() = ProductViewParam(
    id = this.id,
    title = this.title,
    desc = this.desc,
    price = this.price,
    images = this.images
)

fun Collection<Product>.toProductViewParams() = this.map {
    it.toProductViewParam()
}