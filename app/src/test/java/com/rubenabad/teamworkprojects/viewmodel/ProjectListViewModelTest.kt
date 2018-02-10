package com.rubenabad.teamworkprojects.viewmodel

import android.arch.lifecycle.Observer
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.rubenabad.teamworkprojects.data.Company
import com.rubenabad.teamworkprojects.data.Project
import com.rubenabad.teamworkprojects.data.StatefulData
import com.rubenabad.teamworkprojects.data.Tag
import com.rubenabad.teamworkprojects.repository.ProjectsRepository
import io.reactivex.Flowable
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Test
import java.util.concurrent.CountDownLatch



/**
 * This class is meant to test the possibles scenarios that the @see ProjectLisViewModelTest may
 * face
 */
class ProjectListViewModelTest {

    private val flowableProjects = Flowable
            .just(
                    listOf(
                            Project("Project 1", "Description 1", Company("Company 1"),
                                    "URL 1", listOf(Tag("Tag 1", "Color 1"),
                                    Tag("Tag 2", "Color 2"))),
                            Project("Project 2", "Description 2", Company("Company 2"),
                                    "URL 2", listOf(Tag("Tag 3", "Color 3"),
                                    Tag("Tag 4", "Color 4")))
                    )
            )

    // We need this to mock the main looper and let live data post its values to the main thread
//    @get:Rule
//    val rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun getAllProjects() {
        val mockRepository = mock<ProjectsRepository> {
            on { getProjects() } doReturn flowableProjects
        }

        val latch = CountDownLatch(1)
        val testObserver = Observer<StatefulData<List<Project>>> { projects ->
            run {
                assertNotNull(projects)
                assertThat(projects!!.data!![0].name, `is`("Project 1"))
                assertThat(projects.data!![0].description, `is`("Description 1"))
                assertThat(projects.data!![0].company!!.name, `is`("Company 1"))
                assertThat(projects.data!![0].logo, `is`("URL 1"))
                assertThat(projects.data!![0].tags!![0].name, `is`("Tag 1"))
                assertThat(projects.data!![0].tags!![0].color, `is`("Color 1"))
                assertThat(projects.data!![0].tags!![1].name, `is`("Tag 2"))
                assertThat(projects.data!![0].tags!![1].color, `is`("Color 2"))
                assertThat(projects.data!![1].name, `is`("Project 2"))
                assertThat(projects.data!![1].description, `is`("Description 2"))
                assertThat(projects.data!![1].company!!.name, `is`("Company 2"))
                assertThat(projects.data!![1].logo, `is`("URL 2"))
                assertThat(projects.data!![1].tags!![0].name, `is`("Tag 3"))
                assertThat(projects.data!![1].tags!![0].color, `is`("Color 3"))
                assertThat(projects.data!![1].tags!![1].name, `is`("Tag 4"))
                assertThat(projects.data!![1].tags!![1].color, `is`("Color 4"))
                latch.countDown()
            }
        }
        ProjectListViewModel(mockRepository).getAllProjects().observeForever(testObserver)
        latch.await()
    }

}
