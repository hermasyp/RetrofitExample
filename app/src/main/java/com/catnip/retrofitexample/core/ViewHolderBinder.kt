package com.catnip.retrofitexample.core

interface ViewHolderBinder<T> {
    fun bind(item: T)
}