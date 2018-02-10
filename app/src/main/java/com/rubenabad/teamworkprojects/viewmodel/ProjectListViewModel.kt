package com.rubenabad.teamworkprojects.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rubenabad.teamworkprojects.data.Project
import com.rubenabad.teamworkprojects.data.State
import com.rubenabad.teamworkprojects.data.StatefulData
import com.rubenabad.teamworkprojects.repository.ProjectsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProjectListViewModel(private val repository: ProjectsRepository) : ViewModel() {

    private val projects = MutableLiveData<StatefulData<List<Project>>>()

    fun getAllProjects(): LiveData<StatefulData<List<Project>>> = projects.apply {
        refresh()
    }

    fun refresh() {
        repository
                .getProjects()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    projects.value = StatefulData(State.START_LOADING)
                }
                .doOnTerminate {
                    projects.value = StatefulData(State.END_LOADING)
                }
                .subscribe({
                    projects.value = StatefulData(State.SUCCESS, data = it)
                }, {
                    projects.value = StatefulData(State.ERROR, error =  it)
                })
    }
}