package com.wasusi.projectman.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wasusi.projectman.R


class ProjectDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_project_details, container, false)
        return root
    }

    companion object {
        fun newInstance() = ProjectDetailsFragment()
    }
}
