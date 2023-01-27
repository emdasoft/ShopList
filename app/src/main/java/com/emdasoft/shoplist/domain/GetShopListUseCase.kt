package com.emdasoft.shoplist.domain

import androidx.lifecycle.LiveData

class GetShopListUseCase(private val repository: Repository) {

    fun getShopList(): LiveData<List<ShopItem>> {
        return repository.getShopList()
    }
}