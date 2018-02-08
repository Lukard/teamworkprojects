package com.rubenabad.teamworkprojects.repository

import com.rubenabad.teamworkprojects.api.ProjectsApi
import com.rubenabad.teamworkprojects.data.Project
import io.reactivex.Flowable
import io.reactivex.Single

class ProjectsRepository(private val projectsApi: ProjectsApi) {

    private var cache = emptyList<Project>()

    fun getProjects(): Flowable<List<Project>> {
        return if (cache.isEmpty()) {
            projectsApi
                    .getProjects()
                    .flatMap {
                        cache = it.projects
                        Single.just(it.projects)
                    }.toFlowable()

        } else {
            Single
                    .just(cache)
                    .mergeWith(
                            projectsApi
                                    .getProjects()
                                    .flatMap {
                                        cache = it.projects
                                        Single.just(it.projects)
                                    }
                    )
        }
    }
}