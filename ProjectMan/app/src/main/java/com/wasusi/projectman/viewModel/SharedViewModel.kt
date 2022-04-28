package com.wasusi.projectman.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wasusi.projectman.model.Contractor
import com.wasusi.projectman.model.Project

class SharedViewModel : ViewModel(){

    //User id
    private lateinit var _userid : MutableLiveData<Int>

    fun setUserId(id: Int){
        _userid = MutableLiveData()
        _userid.value = id
    }

    fun getUserId(): MutableLiveData<Int> = _userid

    //Token
    private lateinit var _token : MutableLiveData<String>

    fun setToken(token: String){
        _token = MutableLiveData()
        _token.value = token
    }

    fun getToken(): MutableLiveData<String> = _token

    //Projects
    private lateinit var _projects : MutableLiveData<List<Project>>

    fun setProjects(projects: List<Project>){
        _projects = MutableLiveData()
        _projects.value = projects
    }

    fun getProjects(): MutableLiveData<List<Project>> = _projects

    //Contractors
    private lateinit var _contractors : MutableLiveData<List<Contractor>>

    fun setContractors(contractors: List<Contractor>){
        _contractors = MutableLiveData()
        _contractors.value = contractors
    }

    fun getContractors(): MutableLiveData<List<Contractor>> = _contractors

    //Clicked Contractor
    private lateinit var _clickedCont: MutableLiveData<Contractor>

    fun setClickedContractor(contractor: Contractor){
        _clickedCont = MutableLiveData()
        _clickedCont.value = contractor
    }

    fun getClickedContractor(): MutableLiveData<Contractor> = _clickedCont

    //Clicked Contractor
    private lateinit var _clickedProject: MutableLiveData<Project>

    fun setClickedProject(project: Project){
        _clickedProject= MutableLiveData()
        _clickedProject.value = project
    }

    fun getClickedProject(): MutableLiveData<Project> = _clickedProject
}
