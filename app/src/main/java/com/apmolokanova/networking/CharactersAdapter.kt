package com.apmolokanova.networking

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharactersAdapter(context: Context) : ListAdapter<Character, RecyclerView.ViewHolder>(CharactersListCallback()) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val charactersViewModel : CharactersViewModel = ViewModelProvider(context as ViewModelStoreOwner)[CharactersViewModel::class.java]



    //todo: переписать на интерфейс и View.OnClick (???)
    private var onItemClickListener : OnItemClickListener<Character>? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<Character>){
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.character_list_item, parent, false)).apply {
            if(onItemClickListener != null){itemView.setOnClickListener{onItemClickListener?.onItemClicked(view=itemView,item=getItem(this.adapterPosition))}}
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(getItem(position))
    }


    /*fun filter(status: String?, gender: String?) {
        if(status == null && gender == null) return;


        status?.let {

        }
    } todo */

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.character_name)
        private val avatar: ImageView = view.findViewById(R.id.character_image)
        fun bind(character: Character) {
            name.text = character.name
            CoroutineScope(Dispatchers.IO).launch {
                var image = charactersViewModel.getCharacterImage(character)
                withContext(Dispatchers.Main) {avatar.setImageDrawable(image)}
            }
        }
    }
}