package com.example.marvel.features.characterslist.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvel.R
import com.example.marvel.databinding.ItemCharacterBinding
import com.example.marvel.features.characterslist.domain.entity.MarvelCharacter
import kotlin.let

class CharactersAdapter(private val onItemClick: (MarvelCharacter) -> Unit) :
    PagingDataAdapter<MarvelCharacter, RecyclerView.ViewHolder>(CharacterDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemCharacterBinding.inflate(inflater, parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CharacterViewHolder) {
            getItem(position)?.let { holder.bind(it) }
        }
    }

    inner class CharacterViewHolder(val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: MarvelCharacter) {
            binding.tvCharacterName.text = character.name
            Glide.with(binding.root.context).load(character.image)
                .centerCrop()
                .placeholder(R.drawable.ic_place_holder).into(binding.ivCharacterImage)
            binding.root.setOnClickListener {
                onItemClick(character)
            }
        }
    }

    class CharacterDiffCallBack : ItemCallback<MarvelCharacter>() {
        override fun areItemsTheSame(
            oldItem: MarvelCharacter,
            newItem: MarvelCharacter
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: MarvelCharacter,
            newItem: MarvelCharacter
        ): Boolean =
            oldItem == newItem
    }
}