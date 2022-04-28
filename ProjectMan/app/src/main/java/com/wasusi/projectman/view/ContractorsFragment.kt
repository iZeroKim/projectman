package com.wasusi.projectman.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.wasusi.projectman.R
import com.wasusi.projectman.model.Contractor
import com.wasusi.projectman.network.ApiClient
import com.wasusi.projectman.network.requests.ContractorRequest
import com.wasusi.projectman.utils.ContractorsAdapter
import com.wasusi.projectman.viewModel.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ContractorsFragment : Fragment() {
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var contractorsList: List<Contractor>
    private lateinit var rvContractors: RecyclerView
    private lateinit var contractorsAdapter: ContractorsAdapter
    private lateinit var apiClient: ApiClient
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_contractors, container, false)

        root.findViewById<FloatingActionButton>(R.id.fabAddContractor).setOnClickListener {
            displayDialog()
        }
        apiClient = ApiClient()
        rvContractors = root.findViewById(R.id.rvContractors)

        return root
    }

    companion object {

        fun newInstance() =
            ContractorsFragment()
    }

    private fun displayDialog() {

        val dialog = BottomSheetDialog(requireContext())
        val btmSheetView = layoutInflater.inflate(R.layout.add_contractor_layout, null, false)
        dialog.setContentView(btmSheetView)
        dialog.setCancelable(true)

        val btnCancel = btmSheetView.findViewById<ImageView>(R.id.btnCancel)
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        val btn_save_item = btmSheetView.findViewById<Button>(R.id.btnAddContractor)
        btn_save_item.setOnClickListener {
            val name = btmSheetView.findViewById<EditText>(R.id.etPName).text.toString()
            var email = btmSheetView.findViewById<EditText>(R.id.etPDescription).text.toString()
            if(!name.isEmpty() && name.isNotBlank() ){
                //Create Contractor
                var contractor = ContractorRequest(email, name)
                postContractor(contractor, dialog)

                dialog.dismiss()
//                btmSheetView.findViewById<EditText>(R.id.etName).text.clear()
//                btmSheetView.findViewById<EditText>(R.id.etEmail).text.clear()


            }
            else{
                Toast.makeText(requireContext(), "Please enter the contractor details", Toast.LENGTH_LONG).show()
            }


        }

        dialog.show()


    }

    private fun postContractor(contractor: ContractorRequest, dialog: BottomSheetDialog) {
        apiClient.getApiService().postContractor("Bearer $token", contractor).enqueue(
            object : Callback<List<Contractor>>{
                override fun onResponse(
                    call: Call<List<Contractor>>,
                    response: Response<List<Contractor>>
                ) {
                    if(response.code() == 200 && !response.body().isNullOrEmpty()){
                        sharedViewModel!!.setContractors(response.body()!!)
                        Toast.makeText(requireContext(), "Contractor added successfully", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    } else{
                        Log.i("add-contractor", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<List<Contractor>>, t: Throwable) {
                    Log.i("add-contractor", t.message.toString())
                }

            }
        )

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel!!.getContractors().observe(viewLifecycleOwner, Observer {
            contractorsList = it
            Toast.makeText(requireActivity()!!, contractorsList.size.toString(), Toast.LENGTH_SHORT).show()
            contractorsAdapter = ContractorsAdapter(contractorsList, requireActivity(), requireActivity().supportFragmentManager, sharedViewModel!!)
            rvContractors.adapter = contractorsAdapter
        })
        sharedViewModel!!.getToken().observe(viewLifecycleOwner, Observer {
            token = it
        })

    }
}


