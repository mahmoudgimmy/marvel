package com.example.marvel.features.characterdetails.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvel.R
import com.example.marvel.databinding.ItemCategoryExpandedBinding
import com.example.marvel.features.characterdetails.domain.entity.Category
import kotlin.let

class CategoryExtendedAdapter() :
    ListAdapter<String, RecyclerView.ViewHolder>(CategoryDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemCategoryExpandedBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CategoryViewHolder) {
            getItem(position)?.let { holder.bind(it) }
        }
    }

    inner class CategoryViewHolder(val binding: ItemCategoryExpandedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: String) {
            Glide.with(binding.root.context).load(category).centerCrop()
                .placeholder(R.drawable.ic_place_holder).into(binding.ivCategory)
        }
    }

    class CategoryDiffCallBack : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean =
            oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean =
            oldItem == newItem
    }
}