package com.rubenabad.teamworkprojects

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.rubenabad.teamworkprojects.api.ProjectsApi
import com.rubenabad.teamworkprojects.data.Project
import com.rubenabad.teamworkprojects.data.ProjectsResponse
import com.rubenabad.teamworkprojects.repository.ProjectsRepository
import io.reactivex.Single
import io.reactivex.subscribers.TestSubscriber
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private val okCall = Single.just(
            ProjectsResponse("OK", listOf(
                    Project("Project 1", "URL 1"),
                    Project("Project 2", "URL 2")
                    )
            )
    )

    private val secondOkCall = Single.just(
            ProjectsResponse("OK", listOf(
                    Project("Project 3", "URL 3"),
                    Project("Project 4", "URL 4")
            )
            )
    )

    @Test
    fun getProjectsFromWebService() {
        val projectsMock = mock<ProjectsApi> {
            on { getProjects() } doReturn okCall
        }

        val testSubscriber: TestSubscriber<List<Project>> = TestSubscriber()
        val repository = ProjectsRepository(projectsMock)
        repository.getProjects().subscribe(testSubscriber)

        testSubscriber.assertComplete()
        testSubscriber.assertNoErrors()
        testSubscriber.assertValueCount(1)
        val projectResponse = testSubscriber.values()[0]
        assertThat(projectResponse.size, `is`(2))
        assertThat(projectResponse[0].name, `is`("Project 1"))
        assertThat(projectResponse[0].logo, `is`("URL 1"))
        assertThat(projectResponse[1].name, `is`("Project 2"))
        assertThat(projectResponse[1].logo, `is`("URL 2"))
    }

    @Test
    fun getProjectsFromCacheAndUpdateFromWebservice() {
        val projectsMock = mock<ProjectsApi> {
            on { getProjects() } doReturn ( listOf(okCall, secondOkCall) )
        }

        val testSubscriber: TestSubscriber<List<Project>> = TestSubscriber()
        val repository = ProjectsRepository(projectsMock)
        repository.getProjects().subscribe(testSubscriber)

        testSubscriber.assertComplete()
        testSubscriber.assertNoErrors()
        testSubscriber.assertValueCount(1)
        val projectResponse = testSubscriber.values()[0]
        assertThat(projectResponse.size, `is`(2))
        assertThat(projectResponse[0].name, `is`("Project 1"))
        assertThat(projectResponse[0].logo, `is`("URL 1"))
        assertThat(projectResponse[1].name, `is`("Project 2"))
        assertThat(projectResponse[1].logo, `is`("URL 2"))

        val testSubscriber2: TestSubscriber<List<Project>> = TestSubscriber()
        repository.getProjects().subscribe(testSubscriber2)

        testSubscriber2.awaitTerminalEvent()

        testSubscriber2.assertValueCount(2)
        var projectResponse2 = testSubscriber2.values()[0]
        assertThat(projectResponse2.size, `is`(2))
        assertThat(projectResponse2[0].name, `is`("Project 1"))
        assertThat(projectResponse2[0].logo, `is`("URL 1"))
        assertThat(projectResponse2[1].name, `is`("Project 2"))
        assertThat(projectResponse2[1].logo, `is`("URL 2"))
        projectResponse2 = testSubscriber2.values()[1]
        assertThat(projectResponse2.size, `is`(2))
        assertThat(projectResponse2[0].name, `is`("Project 3"))
        assertThat(projectResponse2[0].logo, `is`("URL 3"))
        assertThat(projectResponse2[1].name, `is`("Project 4"))
        assertThat(projectResponse2[1].logo, `is`("URL 4"))
    }

    @Test
    fun failIfThereIsNoCachedProjectsAndFirstCallFail() {
        val exception = RuntimeException("What a terrible failure! We could not properly get projects!")

        val projectsMock = mock<ProjectsApi> {
            on { getProjects() } doReturn Single.error(exception)
        }

        val testSubscriber: TestSubscriber<List<Project>> = TestSubscriber()
        val repository = ProjectsRepository(projectsMock)
        repository.getProjects().subscribe(testSubscriber)

        testSubscriber.assertError(exception)
    }
}
