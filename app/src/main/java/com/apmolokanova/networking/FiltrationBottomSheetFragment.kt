package com.apmolokanova.networking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FiltrationBottomSheetFragment : BottomSheetDialogFragment() {
    //TODO: параметр - viewmodel (???)
    var viewModel: CharactersViewModel? = null;
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_filtration_bottomsheet,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[CharactersViewModel::class.java]

        view.findViewById<RadioGroup>(R.id.gender_group).setOnCheckedChangeListener { group, checkedId ->
            viewModel?.filtration_gender?.value = checkedId
        }
        view.findViewById<RadioGroup>(R.id.status_group).setOnCheckedChangeListener { group, checkedId ->
            viewModel?.filtration_status?.value = checkedId
        }
    }



}