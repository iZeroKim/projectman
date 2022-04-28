package com.wasusi.projectman.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.wasusi.projectman.R
import com.wasusi.projectman.model.Contractor
import com.wasusi.projectman.model.Project
import com.wasusi.projectman.network.ApiClient
import com.wasusi.projectman.network.requests.ContractorRequest
import com.wasusi.projectman.network.requests.CreateProjectRequest
import com.wasusi.projectman.utils.ProjectsAdapter
import com.wasusi.projectman.viewModel.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProjectsFragment : Fragment() {
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var projectsList: List<Project>
    private lateinit var rvProjects: RecyclerView
    private lateinit var projectsAdapter: ProjectsAdapter
    private lateinit var apiClient: ApiClient
    private var userId: Int = 0
    private lateinit var token: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_projects, container, false)

        apiClient = ApiClient()
        rvProjects = root.findViewById<RecyclerView>(R.id.rvProjects)
        var fab = root.findViewById<FloatingActionButton>(R.id.fabAddProject)
        fab.setOnClickListener {
            displayDialog()
        }

        return root
    }

    companion object {
        fun newInstance() = ProjectsFragment()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel!!.getProjects().observe(viewLifecycleOwner, Observer {
            projectsList = it
            Toast.makeText(requireActivity()!!, projectsList.size.toString(), Toast.LENGTH_SHORT).show()
            projectsAdapter = ProjectsAdapter(projectsList, requireActivity(), requireActivity().supportFragmentManager, sharedViewModel!!)
            rvProjects.adapter = projectsAdapter
        })
        sharedViewModel!!.getUserId().observe(viewLifecycleOwner, Observer {
            userId = it
        })
        sharedViewModel!!.getToken().observe(viewLifecycleOwner, Observer {
            token = it
        })

    }


    private fun displayDialog() {

        val dialog = BottomSheetDialog(requireContext())
        val btmSheetView = layoutInflater.inflate(R.layout.add_project_layout, null, false)
        dialog.setContentView(btmSheetView)
        dialog.setCancelable(true)

        val btnCancel = btmSheetView.findViewById<ImageView>(R.id.btnCancel)
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        val btn_create_project = btmSheetView.findViewById<Button>(R.id.btnCreateProject)

        btn_create_project.setOnClickListener {
            val name = btmSheetView.findViewById<EditText>(R.id.etPName).text.toString()
            val description = btmSheetView.findViewById<EditText>(R.id.etPDescription).text.toString()
            val cost = btmSheetView.findViewById<EditText>(R.id.etPCost).text.toString().toInt()

            //Create project
            var project = CreateProjectRequest(userId, description, name, cost)
            createProject(project)
            dialog.dismiss()
        }

        dialog.show()


    }

    private fun createProject(project: CreateProjectRequest) {
        apiClient.getApiService().postProject("Bearer $token", project).enqueue(
            object : Callback<List<Project>> {
                override fun onResponse(
                    call: Call<List<Project>>,
                    response: Response<List<Project>>
                ) {
                    if(response.code() == 200 && !response.body().isNullOrEmpty()){
                        sharedViewModel!!.setProjects(response.body()!!)
                        Toast.makeText(requireContext(), "Project created successfully", Toast.LENGTH_SHORT).show()

                    } else{
                        Log.i("add-project", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<List<Project>>, t: Throwable) {
                    Log.i("add-project", t.message.toString())
                }

            }
        )
    }
}

