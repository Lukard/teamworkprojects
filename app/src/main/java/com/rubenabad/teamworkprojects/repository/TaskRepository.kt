package com.rubenabad.teamworkprojects.repository

import com.rubenabad.teamworkprojects.api.WebserviceDataSource
import com.rubenabad.teamworkprojects.data.Task
import io.reactivex.Single

class TaskRepositoryImpl(private val webserviceDataSource: WebserviceDataSource) : TaskRepository {

    override fun getTasks(projectId: Long): Single<List<Task>> =
            webserviceDataSource.getTasks(projectId).map {
                it.tasks ?: emptyList()
            }

}

interface TaskRepository {

    fun getTasks(projectId: Long): Single<List<Task>>

}