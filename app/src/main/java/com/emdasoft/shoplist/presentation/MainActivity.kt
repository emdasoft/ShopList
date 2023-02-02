package com.emdasoft.shoplist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emdasoft.shoplist.databinding.ActivityMainBinding
import com.emdasoft.shoplist.domain.ShopItem


class MainActivity : AppCompatActivity(), ShopListAdapter.SetOnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: DataModel
    private lateinit var rvAdapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[DataModel::class.java]

        setupRecyclerView()

        viewModel.shopList.observe(this) {
            rvAdapter.submitList(it)
        }

        binding.btnAddShopItem.setOnClickListener {
            val intent = ShopItemActivity.newIntentAddItem(this)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        binding.apply {
            with(rvShopList) {
                layoutManager =
                    LinearLayoutManager(
                        this@MainActivity,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                rvAdapter = ShopListAdapter(this@MainActivity)
                adapter = rvAdapter
                recycledViewPool.setMaxRecycledViews(
                    ShopListAdapter.VIEW_TYPE_ENABLED,
                    ShopListAdapter.MAX_POOL_SIZE
                )
                recycledViewPool.setMaxRecycledViews(
                    ShopListAdapter.VIEW_TYPE_DISABLED,
                    ShopListAdapter.MAX_POOL_SIZE
                )

                setupSwipeListener()
            }
        }
    }

    private fun setupSwipeListener() {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = rvAdapter.currentList[viewHolder.absoluteAdapterPosition]
                viewModel.deleteShopItem(item)
            }

        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rvShopList)
    }

    override fun setOnLongClickListener(shopItem: ShopItem) {
        viewModel.changeEnabled(shopItem)
    }

    override fun setOnClickListener(shopItem: ShopItem) {
        val intent = ShopItemActivity.newIntentEditItem(this, shopItem.id)
        startActivity(intent)
        Toast.makeText(this, "$shopItem", Toast.LENGTH_SHORT).show()
    }

}