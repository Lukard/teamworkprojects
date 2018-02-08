package com.rubenabad.teamworkprojects.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.rubenabad.teamworkprojects.data.Project
import com.rubenabad.teamworkprojects.repository.ProjectsRepository
import io.reactivex.Flowable
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.concurrent.CountDownLatch



/**
 * This class is meant to test the possibles scenarios that the @see ProjectLisViewModelTest may
 * face
 */
class ProjectListViewModelTest {

    private val flowableProjects = Flowable
            .just(
                    listOf(
                            Project("Project 1", "URL 1"),
                            Project("Project 2", "URL 2")
                    )
            )

    // We need this to mock the main looper and let live data post its values to the main thread
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun getAllProjects() {
        val mockRepository = mock<ProjectsRepository> {
            on { getProjects() } doReturn flowableProjects
        }

        val latch = CountDownLatch(1)
        val testObserver = Observer<List<Project>> { projects ->
            run {
                assertNotNull(projects)
                assertThat(projects!![0].name, `is`("Project 1"))
                assertThat(projects[0].logo, `is`("URL 1"))
                assertThat(projects[1].name, `is`("Project 2"))
                assertThat(projects[1].logo, `is`("URL 2"))
                latch.countDown()
            }
        }
        ProjectListViewModel(mockRepository).getAllProjects().observeForever(testObserver)
        latch.await()
    }

}
