package com.example.marvel.features.characterdetails.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvel.R
import com.example.marvel.databinding.ItemCategoryBinding
import com.example.marvel.features.characterdetails.domain.entity.Category
import kotlin.let

class CategoryAdapter(private val onItemClick: (Category) -> Unit) :
    PagingDataAdapter<Category, RecyclerView.ViewHolder>(CategoryDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemCategoryBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CategoryViewHolder) {
            getItem(position)?.let { holder.bind(it) }
        }
    }

    inner class CategoryViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.tvCategoryName.text = category.title
            Glide.with(binding.root.context).load(category.thumbnail).centerCrop()
                .placeholder(R.drawable.ic_place_holder).into(binding.ivCategory)
            itemView.setOnClickListener {
                onItemClick(category)
            }
        }
    }

    class CategoryDiffCallBack : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(
            oldItem: Category,
            newItem: Category
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Category,
            newItem: Category
        ): Boolean =
            oldItem == newItem
    }
}