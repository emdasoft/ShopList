package com.emdasoft.shoplist.presentation

import androidx.lifecycle.ViewModel
import com.emdasoft.shoplist.data.RepositoryImpl
import com.emdasoft.shoplist.domain.AddShopItemUseCase
import com.emdasoft.shoplist.domain.EditShopItemUseCase
import com.emdasoft.shoplist.domain.GetShopItemUseCase
import com.emdasoft.shoplist.domain.ShopItem

class ShopItemViewModel : ViewModel() {

    private val repository = RepositoryImpl

    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val getShopItemUseCase = GetShopItemUseCase(repository)

    fun editShopItem(shopItem: ShopItem) {

        editShopItemUseCase.editShopItem(shopItem)
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            val shopItem = ShopItem(name, count.toDouble(), true)
            addShopItemUseCase.addShopItem(shopItem)
        }
    }

    fun getShopItem(id: Int) {
        val item = getShopItemUseCase.getShopItem(id)
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            //TODO: show error input name
            result = false
        }
        if (count <= 0) {
            //TODO: show error input count
            result = false
        }
        return result
    }
}