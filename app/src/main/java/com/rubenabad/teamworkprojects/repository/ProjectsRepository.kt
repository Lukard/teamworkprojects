package com.rubenabad.teamworkprojects.repository

import com.rubenabad.teamworkprojects.api.WebserviceDatasource
import com.rubenabad.teamworkprojects.data.Company
import com.rubenabad.teamworkprojects.data.Project
import com.rubenabad.teamworkprojects.data.ProjectsResponse
import com.rubenabad.teamworkprojects.data.Tag
import com.rubenabad.teamworkprojects.db.DatabaseDataSource
import com.rubenabad.teamworkprojects.db.entity.ProjectEntity
import com.rubenabad.teamworkprojects.db.entity.ProjectTagEntity
import com.rubenabad.teamworkprojects.db.entity.TagEntity
import io.reactivex.Flowable
import io.reactivex.Single

class ProjectsRepositoryImpl(
        private val webserviceDatasource: WebserviceDatasource,
        private val databaseDataSource: DatabaseDataSource) : ProjectsRepository {

    override fun getProjects(): Flowable<List<Project>> =
            Single.concatArray(getProjectsFromDB(), getProjectsFromWebservice())

    private fun getProjectsFromWebservice(): Single<List<Project>> =
            webserviceDatasource
                    .getProjects()
                    .map {
                        refreshDatabase(it)
                    }

    private fun getProjectsFromDB() =
            databaseDataSource
                    .projectDao()
                    .getAllProjects()
                    .flattenAsObservable { projects -> projects }
                    .flatMap { project ->
                        databaseDataSource
                                .projectTagDao()
                                .getProjectTagsById(project.id)
                                .map { projectTags -> Pair(project, projectTags) }
                                .flatMap { projectProjectTags ->
                                    databaseDataSource
                                            .tagDao()
                                            .getTagsById(projectProjectTags.second.map { it.tag })
                                            .map { tags ->
                                                Project(
                                                        projectProjectTags.first.name,
                                                        projectProjectTags.first.description,
                                                        Company(projectProjectTags.first.company),
                                                        projectProjectTags.first.logo,
                                                        tags.map { Tag(it.name, it.color) }
                                                )
                                            }
                                }
                                .toObservable()
                    }
                    .toList()

    private fun refreshDatabase(response: ProjectsResponse): List<Project> {
        databaseDataSource.projectTagDao().deleteAll()
        databaseDataSource.projectDao().deleteAll()
        databaseDataSource.tagDao().deleteAll()
        response.projects?.let { projects ->
            projects.forEach { project ->
                val projectId = databaseDataSource
                        .projectDao()
                        .insert(ProjectEntity(project.name, project.description, project.company?.name, project.logo))

                project.tags?.forEach { tag ->
                    val tagId = databaseDataSource
                            .tagDao()
                            .insert(TagEntity(tag.name, tag.color))

                    databaseDataSource
                            .projectTagDao()
                            .insert(ProjectTagEntity(projectId, tagId))
                }
            }
        }
        return response.projects ?: emptyList()
    }

}

interface ProjectsRepository {

    fun getProjects(): Flowable<List<Project>>

}