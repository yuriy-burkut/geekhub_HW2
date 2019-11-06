package com.yuriy.fragmentexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class ItemsAdapter(private val itemsList: List<Item>, private val callback: AdapterCallback) :
    RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder>() {

    interface AdapterCallback {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ItemsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    inner class ItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private lateinit var item: Item

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: Item) = with(itemView) {
            item_title.text = item.title
            item_date.text = item.date?.take(11)
            this@ItemsViewHolder.item = item
        }

        override fun onClick(v: View?) {
            callback.onItemClick(adapterPosition)
        }
    }
}