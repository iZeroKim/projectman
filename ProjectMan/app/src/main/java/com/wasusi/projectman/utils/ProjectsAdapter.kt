package com.wasusi.projectman.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.wasusi.projectman.R
import com.wasusi.projectman.model.Project
import com.wasusi.projectman.view.ProjectDetailsFragment
import com.wasusi.projectman.viewModel.SharedViewModel


class ProjectsAdapter(val projectList: List<Project>, val context: Context, val fm: FragmentManager, val viewModel: SharedViewModel) : RecyclerView.Adapter<ProjectsAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_item, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ProjectsAdapter.ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.bindItems(projectList[position])

        holder.itemView.setOnClickListener {
            Toast.makeText(context, "Clicked ${projectList[position].id}", Toast.LENGTH_SHORT).show()

            viewModel.setClickedProject(projectList[position])
            fm.beginTransaction().replace(R.id.container, ProjectDetailsFragment.newInstance())
                .commitNow()
        }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return projectList.size
    }

    //the class is holding the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindItems(project : Project) {
            val tvName = itemView.findViewById<TextView>(R.id.tvName)
            val tvDesc = itemView.findViewById<TextView>(R.id.tvDesc)

            tvName.text = project.name
            tvDesc.text = project.description

        }

    }


}
