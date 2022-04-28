package com.wasusi.projectman.auth

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.wasusi.projectman.R
import com.wasusi.projectman.network.ApiClient
import com.wasusi.projectman.network.requests.LoginRequest
import com.wasusi.projectman.network.responses.LoginResponse
import com.wasusi.projectman.view.DashboardFragment
import com.wasusi.projectman.viewModel.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment : Fragment() {
    companion object {
        fun newInstance() = LoginFragment()
    }

    private var sharedViewModel: SharedViewModel? = null
    private lateinit var apiClient: ApiClient
    private var mContext: Context? = null

    override fun onAttach(mycontext: Context) {
        super.onAttach(mycontext)
        mContext = mycontext
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var root = inflater.inflate(R.layout.login_fragment, container, false)

        apiClient = ApiClient()
        var loginButton = root.findViewById<Button>(R.id.btnLogin)
        var registerLink = root.findViewById<TextView>(R.id.tvRegisterLink)

        loginButton.setOnClickListener {
            var email = root.findViewById<EditText>(R.id.etPDescription).text.toString()
            var password = root.findViewById<EditText>(R.id.etPCost).text.toString()

            if(email.isNotEmpty()){
                if(password.isNotEmpty()){
                    //Hide keyboard
                    hideKeyboard(requireActivity())
                    login(email, password)

                } else{
                    Toast.makeText(requireContext(), "Please enter your password", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Please enter email", Toast.LENGTH_SHORT).show()
            }
        }

        registerLink.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, RegisterFragment.newInstance())
                .commitNow()
        }


        return root
    }

    private fun login(email: String, password: String) {
        apiClient.getApiService().login(LoginRequest(email, password))
            .enqueue(object : Callback<LoginResponse>{
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val loginResponse = response.body()
                    Log.i("loginresponse", loginResponse.toString())

                    if(loginResponse?.success == true){
                        sharedViewModel!!.setUserId(response.body()!!.userData.id)
                        sharedViewModel!!.setToken(response.body()!!.token.token)
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.container, DashboardFragment.newInstance())
                            .commitNow()
                    } else{
                        Log.i("login", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.i("login", t.message.toString())
                    Toast.makeText(requireActivity(), "Kindly try to login again", Toast.LENGTH_SHORT).show()
                }

            })
    }

    fun hideKeyboard(context: Context) {
        try {
            (context as Activity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            if (context.currentFocus != null && context.currentFocus!!
                    .windowToken != null
            ) {
                (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    context.currentFocus!!.windowToken, 0
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)


    }

}
