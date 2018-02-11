package com.rubenabad.teamworkprojects.repository

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.rubenabad.teamworkprojects.api.TaskResponse
import com.rubenabad.teamworkprojects.api.WebserviceDataSource
import com.rubenabad.teamworkprojects.data.Task
import io.reactivex.Single
import io.reactivex.subscribers.TestSubscriber
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Test

/**
 * This class is meant to test the possibles scenarios that the TaskRepository may face
 */
class TaskRepositoryTest {

    private val tasks = (1..5).map { Task("Task $it") }

    @Test
    fun getTasks() {
        val webserviceMock = mock<WebserviceDataSource> {
            on { getTasks(any()) } doReturn Single.just(TaskResponse("OK", tasks))
        }

        val testSubscriber: TestSubscriber<List<Task>> = TestSubscriber()
        val repository = TaskRepositoryImpl(webserviceMock)
        repository.getTasks(any()).toFlowable().subscribe(testSubscriber)

        testSubscriber.assertComplete()
        testSubscriber.assertNoErrors()
        testSubscriber.assertValueCount(1)
        val test = testSubscriber.values()[0]

        test.forEachIndexed { index, task ->
            MatcherAssert.assertThat(task.content, `is`("Task ${index + 1}"))
        }
    }

}