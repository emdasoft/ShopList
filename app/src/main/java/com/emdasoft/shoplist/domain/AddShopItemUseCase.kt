package com.emdasoft.shoplist.domain

class AddShopItemUseCase(private val repository: Repository) {

    fun addShopItem(shopItem: ShopItem) {
        repository.addShopItem(shopItem)
    }
}