package com.emdasoft.shoplist.domain

data class ShopItem(
    val title: String,
    val count: Double,
    var enabled: Boolean,
    var id: Int = UNDEFINED_ID,
) {

    companion object {
        const val UNDEFINED_ID = -1
    }
}
