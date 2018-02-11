package com.rubenabad.teamworkprojects.repository

import com.rubenabad.teamworkprojects.api.ProjectsResponse
import com.rubenabad.teamworkprojects.api.WebserviceDataSource
import com.rubenabad.teamworkprojects.data.Project
import com.rubenabad.teamworkprojects.db.service.ProjectDatabaseService
import io.reactivex.Flowable
import io.reactivex.Single

class ProjectsRepositoryImpl(
        private val webserviceDataSource: WebserviceDataSource,
        private val databaseDataSource: ProjectDatabaseService) : ProjectsRepository {

    override fun getProjects(): Flowable<List<Project>> =
            Single.concatArray(getProjectsFromDB(), getProjectsFromWebservice())

    private fun getProjectsFromWebservice(): Single<List<Project>> =
            webserviceDataSource.getProjects().map { refreshDatabase(it) }

    private fun getProjectsFromDB(): Single<List<Project>> = databaseDataSource.getAllProjects()

    private fun refreshDatabase(response: ProjectsResponse): List<Project> {
        response.projects?.let { databaseDataSource.refreshProjects(it) }
        return response.projects ?: emptyList()
    }

}

interface ProjectsRepository {

    fun getProjects(): Flowable<List<Project>>

}