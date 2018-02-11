package com.rubenabad.teamworkprojects.db.service

import com.rubenabad.teamworkprojects.data.Company
import com.rubenabad.teamworkprojects.data.Project
import com.rubenabad.teamworkprojects.data.Tag
import com.rubenabad.teamworkprojects.db.DatabaseDataSource
import com.rubenabad.teamworkprojects.db.entity.ProjectEntity
import com.rubenabad.teamworkprojects.db.entity.ProjectTagEntity
import com.rubenabad.teamworkprojects.db.entity.TagEntity
import io.reactivex.Single

class ProjectDatabaseServiceImpl(private val databaseDataSource: DatabaseDataSource) : ProjectDatabaseService {

    override fun getAllProjects(): Single<List<Project>> =
            databaseDataSource
                    .projectDao()
                    .getAllProjects()
                    .flattenAsObservable { projects -> projects }
                    .flatMap { projectEntitiesIntoProjects(it) }
                    .toList()

    private fun projectEntitiesIntoProjects(project: ProjectEntity) =
            databaseDataSource
                    .projectTagDao()
                    .getProjectTagsById(project.id)
                    .map { projectTags -> Pair(project, projectTags) }
                    .flatMap { associateProjectWithItsTags(it) }
                    .toObservable()

    private fun associateProjectWithItsTags(projectTags: Pair<ProjectEntity, List<ProjectTagEntity>>) =
            databaseDataSource
                    .tagDao()
                    .getTagsById(projectTags.second.map { it.tag })
                    .map { tags ->
                        createProject(projectTags.first, tags)
                    }

    private fun createProject(projectEntity: ProjectEntity, tags: List<TagEntity>) =
            Project(
                    projectEntity.webId,
                    projectEntity.name,
                    projectEntity.description,
                    Company(projectEntity.company),
                    projectEntity.logo,
                    tags.map { Tag(it.name, it.color) }

            )

    override fun refreshProjects(projects: List<Project>) {
        databaseDataSource.projectTagDao().deleteAll()
        databaseDataSource.projectDao().deleteAll()
        databaseDataSource.tagDao().deleteAll()

        projects.forEach { project ->
            val projectId = databaseDataSource.projectDao().insert(
                    ProjectEntity(
                            project.name,
                            project.description,
                            project.company?.name,
                            project.logo,
                            project.id)
            )

            project.tags?.forEach { tag ->
                val tagId = databaseDataSource.tagDao().insert(TagEntity(tag.name, tag.color))

                databaseDataSource.projectTagDao().insert(ProjectTagEntity(projectId, tagId))
            }
        }
    }
}

interface ProjectDatabaseService {

    fun getAllProjects(): Single<List<Project>>
    fun refreshProjects(projects: List<Project>)

}