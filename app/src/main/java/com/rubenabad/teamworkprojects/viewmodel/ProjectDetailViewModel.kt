package com.rubenabad.teamworkprojects.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rubenabad.teamworkprojects.data.State
import com.rubenabad.teamworkprojects.data.StatefulData
import com.rubenabad.teamworkprojects.data.Task
import com.rubenabad.teamworkprojects.repository.TaskRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProjectDetailViewModel(private val repository: TaskRepository) : ViewModel() {

    private val tasks = MutableLiveData<StatefulData<List<Task>>>()

    fun getTasks(projectId: Long): LiveData<StatefulData<List<Task>>> = tasks.apply {
            repository
                    .getTasks(projectId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .toFlowable()
                    .doOnSubscribe {
                        tasks.value = StatefulData(State.START_LOADING)
                    }
                    .doOnTerminate {
                        tasks.value = StatefulData(State.END_LOADING)
                    }
                    .subscribe({
                        tasks.value = StatefulData(State.SUCCESS, data = it)
                    }, {
                        tasks.value = StatefulData(State.ERROR, error =  it)
                    })
    }

}