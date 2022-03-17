package com.wasusi.projectman

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wasusi.projectman.auth.LoginFragment
import com.wasusi.projectman.view.DashboardFragment
import com.wasusi.projectman.viewModel.SharedViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var sharedViewModel: SharedViewModel
    private var userId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        supportFragmentManager.beginTransaction()
            .replace(R.id.container, LoginFragment.newInstance())
            .commitNow()
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

//            if(::sharedViewModel.getUserId().isInitialized){
//
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.container, DashboardFragment.newInstance())
//                    .commitNow()
//            } else{
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.container, LoginFragment.newInstance())
//                    .commitNow()
//            }


    }




}
