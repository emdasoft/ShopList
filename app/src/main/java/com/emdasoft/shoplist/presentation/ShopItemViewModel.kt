package com.emdasoft.shoplist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emdasoft.shoplist.data.RepositoryImpl
import com.emdasoft.shoplist.domain.AddShopItemUseCase
import com.emdasoft.shoplist.domain.EditShopItemUseCase
import com.emdasoft.shoplist.domain.GetShopItemUseCase
import com.emdasoft.shoplist.domain.ShopItem

class ShopItemViewModel : ViewModel() {

    private val repository = RepositoryImpl

    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val getShopItemUseCase = GetShopItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen


    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseInputName(inputName)
        val count = parseInputCount(inputCount)
        val validate = validateInput(name, count)
        if (validate) {
            val shopItem = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(shopItem)
            finishWork()
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseInputName(inputName)
        val count = parseInputCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            _shopItem.value?.let {
                val item = it.copy(title = name, count = count)
                editShopItemUseCase.editShopItem(item)
                finishWork()
            }
        }
    }

    fun getShopItem(itemId: Int) {
        val item = getShopItemUseCase.getShopItem(itemId)
        _shopItem.value = item
    }

    private fun parseInputName(inputName: String?): String {
        return inputName?.trim() ?: INCORRECT_INPUT_NAME
    }

    private fun parseInputCount(inputCount: String?): Double {
        return try {
            inputCount?.trim()?.toDouble() ?: INCORRECT_INPUT_COUNT
        } catch (e: Exception) {
            INCORRECT_INPUT_COUNT
        }
    }

    private fun validateInput(name: String, count: Double): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= INCORRECT_INPUT_COUNT) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }

    companion object {
        private const val INCORRECT_INPUT_COUNT = 0.0
        private const val INCORRECT_INPUT_NAME = ""
    }
}