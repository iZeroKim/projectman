package com.wasusi.projectman.utils

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.wasusi.projectman.R
import com.wasusi.projectman.model.Contractor
import com.wasusi.projectman.model.Project
import com.wasusi.projectman.view.ContractorDetailsFragment
import com.wasusi.projectman.view.ProjectsFragment
import com.wasusi.projectman.viewModel.SharedViewModel

class ContractorsAdapter(val contractorList: List<Contractor>, val activity: Activity, val fm: FragmentManager, val viewModel: SharedViewModel) : RecyclerView.Adapter<ContractorsAdapter.ViewHolder>() {



    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContractorsAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_item, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ContractorsAdapter.ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.bindItems(contractorList[position])

        holder.itemView.setOnClickListener {
            Toast.makeText(activity, "Clicked ${contractorList[position].id}", Toast.LENGTH_SHORT).show()

            viewModel.setClickedContractor(contractorList[position])
            fm.beginTransaction()
                .replace(R.id.container, ContractorDetailsFragment.newInstance()).commitNow()


        }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return contractorList.size
    }

    //the class is holding the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindItems(contractor: Contractor) {
            val tvName = itemView.findViewById<TextView>(R.id.tvName)
            val tvDesc = itemView.findViewById<TextView>(R.id.tvDesc)

            tvName.text = contractor.name
            tvDesc.text = contractor.email

        }

    }


}
