package com.emdasoft.shoplist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.emdasoft.shoplist.domain.Repository
import com.emdasoft.shoplist.domain.ShopItem
import kotlin.random.Random

object RepositoryImpl : Repository {

    private var shopList = mutableListOf<ShopItem>()
    private var shopListLD = MutableLiveData<List<ShopItem>>()
    private var autoIncrementId = 0

    init {
        for (item in 0..20) {
            addShopItem(ShopItem("$item", item.toDouble(), Random.nextBoolean()))
        }
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
        updateShopList()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateShopList()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldItem = getShopItem(shopItem.id)
        deleteShopItem(oldItem)
        addShopItem(shopItem)
    }

    override fun getShopItem(itemId: Int): ShopItem {
        return shopList.find { it.id == itemId }!!
    }

    private fun updateShopList() {
        shopListLD.value = shopList.toList().sortedBy { it.id }
    }
}