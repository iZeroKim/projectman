package com.wasusi.projectman.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wasusi.projectman.R
import com.wasusi.projectman.model.Contractor
import com.wasusi.projectman.viewModel.SharedViewModel


class ContractorDetailsFragment : Fragment() {
   private lateinit var sharedViewModel: SharedViewModel
   private lateinit var currentContractor: Contractor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_contractor_details, container, false)

        return root
    }

    companion object {
        fun newInstance() = ContractorDetailsFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel!!.getClickedContractor().observe(viewLifecycleOwner, Observer {
            currentContractor = it
        })

    }
}

