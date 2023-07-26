package com.nandaadisaputra.databinding.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nandaadisaputra.databinding.R
import com.nandaadisaputra.databinding.databinding.ItemHobbyBinding
import com.nandaadisaputra.databinding.room.Hobby

class HobbyAdapter: ListAdapter<Hobby, HobbyAdapter.ItemViewHolder>(DiffUtilCallback()) {

    private var onItemClick: ((Hobby) -> Unit)? = null

    fun setOnClickItem(onClickItem: (Hobby) -> Unit) {
        this.onItemClick = onClickItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = DataBindingUtil.inflate<ItemHobbyBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_hobby,
            parent,
            false
        )
        return ItemViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let { hobby ->
            holder.bind(hobby)
            holder.itemView.setOnClickListener {
                onItemClick?.invoke(hobby)
            }
        }
    }

    inner class ItemViewHolder(private val binding: ItemHobbyBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(hobby: Hobby) {
            /*hobby diambil dari <variable name="hobby" yang berada di layout item_hobby.xml*/
            binding.hobby = hobby
            binding.executePendingBindings()
        }
    }

    class DiffUtilCallback<T: Any> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem === newItem
        }
    }
}