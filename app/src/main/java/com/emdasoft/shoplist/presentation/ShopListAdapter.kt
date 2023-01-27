package com.emdasoft.shoplist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.emdasoft.shoplist.R
import com.emdasoft.shoplist.databinding.ShopItemBinding
import com.emdasoft.shoplist.domain.ShopItem

class ShopListAdapter(private val listener: SetOnClickListener) :
    ListAdapter<ShopItem, ShopListAdapter.ShopItemViewHolder>(ShopItemDiffCallback()) {

    var count = 0

    class ShopItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ShopItemBinding.bind(itemView)
        fun bind(shopItem: ShopItem, listener: SetOnClickListener) = with(binding) {
            tvTitle.text = shopItem.title
            tvCount.text = shopItem.count.toString()
            itemView.setOnLongClickListener {
                listener.setOnLongClickListener(shopItem)
                true
            }
            itemView.setOnClickListener {
                listener.setOnClickListener(shopItem)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layoutId = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.shop_item
            VIEW_TYPE_DISABLED -> R.layout.shop_item_disabled
            else -> throw RuntimeException("Unknown View Type: $viewType")
        }

        val view = LayoutInflater.from(parent.context).inflate(
            layoutId,
            parent,
            false
        )
        return ShopItemViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enabled) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        Log.d("onBindViewHolder", "${++count}")
        val shopItem = getItem(position)
        holder.bind(shopItem, listener)
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101
        const val MAX_POOL_SIZE = 15
    }

    interface SetOnClickListener {

        fun setOnLongClickListener(shopItem: ShopItem)

        fun setOnClickListener(shopItem: ShopItem)
    }
}