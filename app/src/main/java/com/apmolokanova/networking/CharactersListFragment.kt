package com.apmolokanova.networking

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView

class CharactersListFragment() : Fragment() {
    var charactersViewModel: CharactersViewModel? = null
    val filtrationBottomSheetFragment: FiltrationBottomSheetFragment = FiltrationBottomSheetFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = requireActivity()
        super.onViewCreated(view, savedInstanceState)
        charactersViewModel = ViewModelProvider(activity)[CharactersViewModel::class.java]

        val adapter = CharactersAdapter(requireContext())
        adapter.submitList(charactersViewModel?.charactersList?.value)
        adapter.setOnItemClickListener(object : OnItemClickListener<Character> {
            override fun onItemClicked(item: Character, view: View) {
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragments_frame, CharacterFragment.newInstance(item as Character))
                    .addToBackStack("charactersList")
                    .commit()
            }
        })
        val charactersRecyclerView: RecyclerView = this.requireView().findViewById<RecyclerView>(R.id.characters_recycler_view)
        charactersRecyclerView.setHasFixedSize(true)
        charactersRecyclerView.adapter = adapter
        charactersViewModel?.charactersList?.observe(activity) {
            adapter.submitList(charactersViewModel?.charactersList?.value!!)
        }

        //todo: подписка на viewmodel filtrations -> менять recyclerview под фильтр

        activity.findViewById<View>(R.id.filterFAB).setOnClickListener {
            filtrationBottomSheetFragment.show(activity.supportFragmentManager,null)
        }

    }

    companion object {
        fun newInstance() = CharactersListFragment();
    }
}