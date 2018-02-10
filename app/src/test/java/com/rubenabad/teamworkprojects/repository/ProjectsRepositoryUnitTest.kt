package com.rubenabad.teamworkprojects.repository

import com.rubenabad.teamworkprojects.data.Company
import com.rubenabad.teamworkprojects.data.Project
import com.rubenabad.teamworkprojects.data.ProjectsResponse
import com.rubenabad.teamworkprojects.data.Tag
import io.reactivex.Single
import org.junit.Test


/**
 * This class is meant to test the possibles scenarios that the @see ProjectsRepository may face
 */
class ProjectsRepositoryUnitTest {

    private val okCall = Single.just(
            ProjectsResponse("OK",
                    listOf(
                            Project("Project 1", "Description 1", Company("Company 1"),
                                    "URL 1", listOf(Tag("Tag 1", "Color 1"),
                                    Tag("Tag 2", "Color 2"))),
                            Project("Project 2", "Description 2", Company("Company 2"),
                                    "URL 2", listOf(Tag("Tag 3", "Color 3"),
                                    Tag("Tag 4", "Color 4")))
                    )
            )
    )

    private val secondOkCall = Single.just(
            ProjectsResponse("OK", listOf(
                    Project("Project 3", "Description 3", Company("Company 3"),
                            "URL 3", listOf(Tag("Tag 5", "Color 5"),
                            Tag("Tag 6", "Color 6"))),
                    Project("Project 4", "Description 4", Company("Company 4"),
                            "URL 4", listOf(Tag("Tag 7", "Color 7"),
                            Tag("Tag 8", "Color 8")))
            )
            )
    )

//    @Test
//    fun getProjectsFromWebService() {
//        val projectsMock = mock<WebserviceDatasource> {
//            on { getProjects() } doReturn okCall
//        }
//
//        val testSubscriber: TestSubscriber<List<Project>> = TestSubscriber()
//        val repository = ProjectsRepositoryImpl(projectsMock)
//        repository.getProjects().subscribe(testSubscriber)
//
//        testSubscriber.assertComplete()
//        testSubscriber.assertNoErrors()
//        testSubscriber.assertValueCount(1)
//        val projectResponse = testSubscriber.values()[0]
//        assertThat(projectResponse.size, `is`(2))
//        assertThat(projectResponse[0].name, `is`("Project 1"))
//        assertThat(projectResponse[0].logo, `is`("URL 1"))
//        assertThat(projectResponse[1].name, `is`("Project 2"))
//        assertThat(projectResponse[1].logo, `is`("URL 2"))
//    }
//
//    @Test
//    fun getProjectsFromCacheAndUpdateFromWebservice() {
//        val projectsMock = mock<WebserviceDatasource> {
//            on { getProjects() } doReturn (listOf(okCall, secondOkCall))
//        }
//
//        val testSubscriber: TestSubscriber<List<Project>> = TestSubscriber()
//        val repository = ProjectsRepositoryImpl(projectsMock)
//        repository.getProjects().subscribe(testSubscriber)
//
//        testSubscriber.assertComplete()
//        testSubscriber.assertNoErrors()
//        testSubscriber.assertValueCount(1)
//        val projectResponse = testSubscriber.values()[0]
//        assertThat(projectResponse.size, `is`(2))
//        assertThat(projectResponse[0].name, `is`("Project 1"))
//        assertThat(projectResponse[0].logo, `is`("URL 1"))
//        assertThat(projectResponse[1].name, `is`("Project 2"))
//        assertThat(projectResponse[1].logo, `is`("URL 2"))
//
//        val testSubscriber2: TestSubscriber<List<Project>> = TestSubscriber()
//        repository.getProjects().subscribe(testSubscriber2)
//
//        testSubscriber2.awaitTerminalEvent()
//
//        testSubscriber2.assertValueCount(2)
//        var projectResponse2 = testSubscriber2.values()[0]
//        assertThat(projectResponse2.size, `is`(2))
//        assertThat(projectResponse2[0].name, `is`("Project 1"))
//        assertThat(projectResponse2[0].logo, `is`("URL 1"))
//        assertThat(projectResponse2[1].name, `is`("Project 2"))
//        assertThat(projectResponse2[1].logo, `is`("URL 2"))
//        projectResponse2 = testSubscriber2.values()[1]
//        assertThat(projectResponse2.size, `is`(2))
//        assertThat(projectResponse2[0].name, `is`("Project 3"))
//        assertThat(projectResponse2[0].logo, `is`("URL 3"))
//        assertThat(projectResponse2[1].name, `is`("Project 4"))
//        assertThat(projectResponse2[1].logo, `is`("URL 4"))
//    }
//
//    @Test
//    fun failIfThereIsNoCachedProjectsAndFirstCallFail() {
//        val exception = RuntimeException("What a terrible failure! We could not properly get projects!")
//
//        val projectsMock = mock<WebserviceDatasource> {
//            on { getProjects() } doReturn Single.error(exception)
//        }
//
//        val testSubscriber: TestSubscriber<List<Project>> = TestSubscriber()
//        val repository = ProjectsRepositoryImpl(projectsMock)
//        repository.getProjects().subscribe(testSubscriber)
//
//        testSubscriber.assertError(exception)
//    }

    @Test
    fun singles() {
        Single.merge(Single.just("1"), Single.just("2")).subscribe { println(it) }
    }
}
