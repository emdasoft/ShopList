package com.emdasoft.shoplist.domain

class GetShopItemUseCase(private val repository: Repository) {

    fun getShopItem(itemId: Int): ShopItem {
        return repository.getShopItem(itemId)
    }
}