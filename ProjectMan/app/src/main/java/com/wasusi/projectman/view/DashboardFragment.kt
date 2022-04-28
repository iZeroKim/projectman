package com.wasusi.projectman.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.wasusi.projectman.R
import com.wasusi.projectman.auth.LoginFragment
import com.wasusi.projectman.model.Contractor
import com.wasusi.projectman.model.Project
import com.wasusi.projectman.network.ApiClient
import com.wasusi.projectman.viewModel.SharedViewModel
import org.jetbrains.annotations.Contract
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DashboardFragment : Fragment() {
    private var sharedViewModel: SharedViewModel? =null
    private var projectCount: TextView? = null
    private var contractorCount: TextView? = null
    private lateinit var apiClient: ApiClient
    private lateinit var pieChart: PieChart
    companion object {
        fun newInstance() = DashboardFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        apiClient = ApiClient()
        root.findViewById<CardView>(R.id.cardContractors).setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, ContractorsFragment.newInstance()).addToBackStack(null).commit()
        }
        root.findViewById<CardView>(R.id.cardProjects).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, ProjectsFragment.newInstance()).addToBackStack(null).commit()
        }
        contractorCount = root.findViewById(R.id.tvContractorCount)
        projectCount = root.findViewById(R.id.tvProjectCount)
        pieChart = root.findViewById(R.id.activity_main_piechart);
        setupPieChart();
        loadPieChartData();

        return root
    }
    private fun setupPieChart() {
        pieChart.setDrawHoleEnabled(true)
        pieChart.setUsePercentValues(true)
        pieChart.setEntryLabelTextSize(12F)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.setCenterText("Projects")
        pieChart.getDescription().setEnabled(false)
        val l: Legend = pieChart.getLegend()
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(true)
        l.isEnabled = false
    }

    private fun loadPieChartData() {
        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(0.65f, "Completed"))
        entries.add(PieEntry(0.35f, "Incomplete"))
        val colors: ArrayList<Int> = ArrayList()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }
        for (color in ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color)
        }
        val dataSet = PieDataSet(entries, "Project progress")
        dataSet.colors = colors
        val data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)
        pieChart.setData(data)
        pieChart.invalidate()
        pieChart.animateY(1600, Easing.EaseInOutQuad)
    }

    private fun fetchProjects(token: String){

        apiClient.getApiService().getProjects("Bearer " + token).enqueue(
            object: Callback<List<Project>>{
                override fun onResponse(
                    call: Call<List<Project>>,
                    response: Response<List<Project>>
                ) {
                    if(response.body()!!.size > 0){
                        sharedViewModel!!.setProjects(response.body()!!)
                        projectCount!!.text = response.body()!!.size.toString()
                        Log.i("data", response.body()!!.get(0).name)
                    } else {
                        projectCount!!.text = "0"
                        Log.i("data", response.errorBody().toString())
                    }

                }

                override fun onFailure(call: Call<List<Project>>, t: Throwable) {
                    Log.i("data", t.message.toString())
                    projectCount!!.text = "0"
                    Toast.makeText(requireActivity(), t.message.toString(), Toast.LENGTH_LONG).show()
                }

            }
        )

    }

    private fun fetchContractors(token: String){

        apiClient.getApiService().getContractors("Bearer " + token).enqueue(
            object: Callback<List<Contractor>>{
                override fun onResponse(
                    call: Call<List<Contractor>>,
                    response: Response<List<Contractor>>
                ) {
                    if(response.body()!!.size > 0){
                        sharedViewModel!!.setContractors(response.body()!!)
                        contractorCount!!.text = response.body()!!.size.toString()
                        Log.i("data-contractor", response.body()!!.get(0).name)
                    } else {
                        contractorCount!!.text = "0"
                        Log.i("data-contractor", response.errorBody().toString())
                    }

                }

                override fun onFailure(call: Call<List<Contractor>>, t: Throwable) {
                    Log.i("data-contractor", t.message.toString())
                    contractorCount!!.text = "0"
                    Toast.makeText(requireActivity(), t.message.toString(), Toast.LENGTH_LONG).show()
                }

            }
        )

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel!!.getToken().observe(viewLifecycleOwner, Observer {
            fetchProjects(it)
            fetchContractors(it)
        })

    }
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

}
