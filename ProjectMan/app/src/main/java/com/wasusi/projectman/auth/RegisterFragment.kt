package com.wasusi.projectman.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.wasusi.projectman.R
import com.wasusi.projectman.network.ApiClient
import com.wasusi.projectman.network.requests.RegisterRequest
import com.wasusi.projectman.network.responses.LoginResponse
import com.wasusi.projectman.view.DashboardFragment
import com.wasusi.projectman.viewModel.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterFragment : Fragment() {

    companion object {

        fun newInstance() = RegisterFragment()
    }
    private lateinit var apiClient:ApiClient
    private lateinit var sharedViewModel: SharedViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_register, container, false)
        apiClient = ApiClient()
        var registerButton = root.findViewById<Button>(R.id.btnRegister)
        var loginLink = root.findViewById<TextView>(R.id.tvLoginLink)

        registerButton.setOnClickListener {
            var name = root.findViewById<EditText>(R.id.etPName).text.toString()
            var email = root.findViewById<EditText>(R.id.etPDescription).text.toString()
            var phone = root.findViewById<EditText>(R.id.etPhone).text.toString()
            var password = root.findViewById<EditText>(R.id.etPCost).text.toString()
            var password_conf = root.findViewById<EditText>(R.id.etConfirmPassword).text.toString()

            if(name.isNotEmpty()){
                if(email.isNotEmpty()){
                    if(phone.isNotEmpty()){
                        if(password.isNotEmpty()){
                            if(password_conf.isNotEmpty()){
                                if(password.equals(password_conf)){
                                    register(name,email,phone,password, password_conf)
                                }else{
                                    Toast.makeText(requireActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show()
                                }
                            }else{
                                Toast.makeText(requireActivity(), "Please re enter your password", Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            Toast.makeText(requireActivity(), "Please enter a password", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(requireActivity(), "Please enter a phone number", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(requireActivity(), "Please enter an email address", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireActivity(), "Please enter name", Toast.LENGTH_SHORT).show()
            }
        }
        loginLink.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commitNow()
        }
        return root
    }



    private fun register(name: String, email: String, phone: String, password: String, password_conf: String) {
        apiClient.getApiService().register(RegisterRequest(email, name, password, password_conf, phone))
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val loginResponse = response.body()
                    Log.i("Registerresponse", loginResponse.toString())

                    if(loginResponse!!.success && loginResponse.userData != null){
                        sharedViewModel!!.setUserId(response.body()!!.userData.id)
                        sharedViewModel!!.setToken(response.body()!!.token.token)
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.container, DashboardFragment.newInstance())
                            .commitNow()
                    } else{
                        Log.i("register", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.i("register", t.message.toString())
                    Toast.makeText(requireActivity(), "Kindly try to register again", Toast.LENGTH_SHORT).show()
                }

            })
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)


    }
}
