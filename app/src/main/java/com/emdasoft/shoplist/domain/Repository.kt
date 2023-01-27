package com.emdasoft.shoplist.domain

import androidx.lifecycle.LiveData

interface Repository {

    fun getShopList(): LiveData<List<ShopItem>>

    fun addShopItem(shopItem: ShopItem)

    fun deleteShopItem(shopItem: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopItem(itemId: Int): ShopItem

}