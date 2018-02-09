package com.rubenabad.teamworkprojects.repository

import com.rubenabad.teamworkprojects.api.ProjectsApi
import com.rubenabad.teamworkprojects.data.Project
import com.rubenabad.teamworkprojects.data.ProjectsResponse
import io.reactivex.Flowable
import io.reactivex.Single

class ProjectsRepositoryImpl(private val projectsApi: ProjectsApi) : ProjectsRepository {

    private var cache = emptyList<Project>()

    override fun getProjects(): Flowable<List<Project>> {
        return if (cache.isEmpty()) {
            getProjectsFromWebservice().toFlowable()
        } else {
            Single.just(cache).mergeWith( getProjectsFromWebservice() )
        }
    }

    private fun getProjectsFromWebservice() =
            projectsApi
                    .getProjects()
                    .flatMap {
                        refreshCache(it)
                    }

    private fun refreshCache(response: ProjectsResponse) =
            if (response.projects == null) {
                Single.just(emptyList())
            } else {
                cache = response.projects
                Single.just(response.projects)
            }
}

interface ProjectsRepository {

    fun getProjects(): Flowable<List<Project>>

}