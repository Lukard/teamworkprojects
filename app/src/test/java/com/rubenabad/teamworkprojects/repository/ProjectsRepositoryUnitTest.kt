package com.rubenabad.teamworkprojects.repository

import com.nhaarman.mockito_kotlin.anyArray
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.rubenabad.teamworkprojects.api.ProjectsResponse
import com.rubenabad.teamworkprojects.api.WebserviceDataSource
import com.rubenabad.teamworkprojects.data.Company
import com.rubenabad.teamworkprojects.data.Project
import com.rubenabad.teamworkprojects.data.Tag
import com.rubenabad.teamworkprojects.db.service.ProjectDatabaseService
import io.reactivex.Single
import io.reactivex.subscribers.TestSubscriber
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

/**
 * This class is meant to test the possibles scenarios that the @see ProjectsRepository may face
 */
class ProjectsRepositoryUnitTest {

    private val projects = listOf(
            Project(1, "Project 1", "Description 1", Company("Company 1"),
                    "URL 1", listOf(Tag("Tag 1", "Color 1"),
                    Tag("Tag 2", "Color 2"))),
            Project(2, "Project 2", "Description 2", Company("Company 2"),
                    "URL 2", listOf(Tag("Tag 3", "Color 3"),
                    Tag("Tag 4", "Color 4")))
    )

    @Test
    fun getProjectsFromWebService() {
        val webserviceMock = mock<WebserviceDataSource> {
            on { getProjects() } doReturn Single.just(ProjectsResponse("OK", projects))
        }

        val databaseMock = mock<ProjectDatabaseService> {
            on { getAllProjects() } doReturn Single.just(projects)
            val anylist = anyArray<Project>().toList()
            on { refreshProjects(anylist) } doAnswer {}
        }

        val testSubscriber: TestSubscriber<List<Project>> = TestSubscriber()
        val repository = ProjectsRepositoryImpl(webserviceMock, databaseMock)
        repository.getProjects().subscribe(testSubscriber)

        testSubscriber.assertComplete()
        testSubscriber.assertNoErrors()
        testSubscriber.assertValueCount(2)
        val projectResponse = testSubscriber.values()[0]
        assertThat(projectResponse.size, `is`(2))
        projectResponse.forEachIndexed { index, project ->
            assertThat(project.id, `is`((index+1).toLong()))
            assertThat(project.name, `is`("Project ${index+1}"))
            assertThat(project.description, `is`("Description ${index+1}"))
            assertThat(project.company!!.name, `is`("Company ${index+1}"))
            assertThat(project.logo, `is`("URL ${index+1}"))
            assertThat(project.tags, `is`((1..2).map { Tag("Tag ${index*2 + it}", "Color ${index*2 + it}") }))
        }
    }

}
