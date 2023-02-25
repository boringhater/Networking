package com.apmolokanova.networking

import androidx.recyclerview.widget.DiffUtil

class CharactersListCallback : DiffUtil.ItemCallback<Character>(){
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean = oldItem == newItem
}