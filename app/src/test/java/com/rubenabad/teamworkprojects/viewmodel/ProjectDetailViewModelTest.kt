package com.rubenabad.teamworkprojects.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.rubenabad.teamworkprojects.data.State
import com.rubenabad.teamworkprojects.data.StatefulData
import com.rubenabad.teamworkprojects.data.Task
import com.rubenabad.teamworkprojects.repository.TaskRepository
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertNotNull
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch

/**
 * This class is meant to test the possibles scenarios that the ProjectDetailViewModel may
 * face
 */
class ProjectDetailViewModelTest {

    val tasks = Single.just((1..5).map { Task("Task $it") })

    // We need this to mock the main looper and let live data post its values to the main thread
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        RxJavaPlugins.reset()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun getTasks() {
        val mockRepository = mock<TaskRepository> {
            on { getTasks(any()) } doReturn tasks
        }

        val latch = CountDownLatch(1)
        val testObserver = Observer<StatefulData<List<Task>>> { tasks ->
            assertNotNull(tasks)
            if (tasks!!.state == State.SUCCESS) {
                tasks.data!!.forEachIndexed { index, task ->
                    assertThat(task.content, `is`("Task ${index+1}"))
                }
            }
            latch.countDown()
        }
        ProjectDetailViewModel(mockRepository).getTasks(any()).observeForever(testObserver)
        latch.await()
    }

}