package com.emdasoft.shoplist.domain

class EditShopItemUseCase(private val repository: Repository) {

    fun editShopItem(shopItem: ShopItem) {
        repository.editShopItem(shopItem)
    }
}