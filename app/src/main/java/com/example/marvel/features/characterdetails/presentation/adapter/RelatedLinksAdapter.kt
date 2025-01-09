package com.example.marvel.features.characterdetails.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel.databinding.ItemRelatedLinkBinding
import com.example.marvel.features.characterslist.domain.entity.Section
import kotlin.let

class RelatedLinksAdapter(private val onLinkClicked: (String) -> Unit) :
    ListAdapter<Section, RecyclerView.ViewHolder>(CategoryDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemRelatedLinkBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CategoryViewHolder) {
            getItem(position)?.let { holder.bind(it) }
        }
    }

    inner class CategoryViewHolder(val binding: ItemRelatedLinkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(section: Section) {
            binding.tvLinkTitle.text = section.title
            binding.root.setOnClickListener {
                onLinkClicked.invoke(section.url)
            }

        }
    }

    class CategoryDiffCallBack : DiffUtil.ItemCallback<Section>() {
        override fun areItemsTheSame(
            oldItem: Section,
            newItem: Section
        ): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(
            oldItem: Section,
            newItem: Section
        ): Boolean =
            oldItem == newItem
    }
}