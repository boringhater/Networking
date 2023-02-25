package com.apmolokanova.networking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val ARG_CHARACTER = "character"

class CharacterFragment : Fragment() {
    var charactersViewModel: CharactersViewModel? = null
    private var character: Character? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var input: String? = readLine()
        val retList: MutableList<String> = mutableListOf()
        while(input != null) {
            val id: Int = input.filter { char -> char.isDigit() }.toInt() - 1
            retList.add(id,input.replace("[0-9]".toRegex(), ""))
            input = readLine()
        }
        for(i in retList.indices) {
            println(retList[i])
        }


        arguments?.let {
            character = it.getParcelable(ARG_CHARACTER) as Character?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        charactersViewModel = ViewModelProvider(requireActivity())[CharactersViewModel::class.java]
        with(character!!) {
            view.findViewById<TextView>(R.id.character_name).text = this.name
            view.findViewById<TextView>(R.id.species_gender_status).text =
                getString(R.string.species_gender_status, this.species, this.gender, this.status)
            view.findViewById<TextView>(R.id.origin).text = this.origin?.name
            view.findViewById<TextView>(R.id.location).text = this.location?.name
            CoroutineScope(Dispatchers.IO).launch {
                val charDrawable = charactersViewModel?.getCharacterImage(this@with)
                withContext(Dispatchers.Main) {
                    view.findViewById<ImageView>(R.id.character_image)
                        .setImageDrawable(charDrawable)
                }
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(character: Character) =
            CharacterFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CHARACTER, character)
                }
            }
    }
}