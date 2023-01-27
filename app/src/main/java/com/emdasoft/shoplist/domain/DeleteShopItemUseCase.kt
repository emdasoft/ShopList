package com.emdasoft.shoplist.domain

class DeleteShopItemUseCase(private val repository: Repository) {

    fun deleteShopItem(shopItem: ShopItem) {
        repository.deleteShopItem(shopItem)
    }
}