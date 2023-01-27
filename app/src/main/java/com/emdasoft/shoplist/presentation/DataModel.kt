package com.emdasoft.shoplist.presentation

import androidx.lifecycle.ViewModel
import com.emdasoft.shoplist.data.RepositoryImpl
import com.emdasoft.shoplist.domain.DeleteShopItemUseCase
import com.emdasoft.shoplist.domain.EditShopItemUseCase
import com.emdasoft.shoplist.domain.GetShopListUseCase
import com.emdasoft.shoplist.domain.ShopItem

class DataModel : ViewModel() {

    private val repository = RepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeEnabled(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newItem)
    }

}