package com.rubenabad.teamworkprojects.repository

import android.util.Log
import com.rubenabad.teamworkprojects.api.WebserviceDataSource
import com.rubenabad.teamworkprojects.data.Task
import io.reactivex.Single

class TaskRepositoryImpl(private val webserviceDataSource: WebserviceDataSource) : TaskRepository {

    override fun getTasks(projectId: Long): Single<List<Task>> = webserviceDataSource.getTasks(projectId).map {
        Log.d("TAG", it.toString())
        it.tasks ?: emptyList() }

}

interface TaskRepository {

    fun getTasks(projectId: Long): Single<List<Task>>

}