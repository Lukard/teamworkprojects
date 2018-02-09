package com.rubenabad.teamworkprojects.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rubenabad.teamworkprojects.data.Project
import com.rubenabad.teamworkprojects.repository.ProjectsRepository
import io.reactivex.schedulers.Schedulers

class ProjectListViewModel(private val repository: ProjectsRepository) : ViewModel() {

    private val projects = MutableLiveData<List<Project>>()

    fun getAllProjects(): LiveData<List<Project>> = projects.apply {
        repository
                .getProjects()
                .subscribeOn(Schedulers.io())
                .subscribe(this::postValue)
    }

    fun refresh() {
        repository
                .getProjects()
                .subscribeOn(Schedulers.io())
                .subscribe(projects::postValue)
    }
}