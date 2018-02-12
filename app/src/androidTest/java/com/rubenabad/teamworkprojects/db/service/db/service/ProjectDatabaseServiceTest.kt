package com.rubenabad.teamworkprojects.db.service.db.service

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.rubenabad.teamworkprojects.data.Company
import com.rubenabad.teamworkprojects.data.Project
import com.rubenabad.teamworkprojects.data.Tag
import com.rubenabad.teamworkprojects.db.DatabaseDataSource
import com.rubenabad.teamworkprojects.db.service.ProjectDatabaseService
import com.rubenabad.teamworkprojects.db.service.ProjectDatabaseServiceImpl
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This class is meant to test the possibles scenarios that the ProjectDao, TagDao and ProjectTagDao may face
 */
@RunWith(AndroidJUnit4::class)
class ProjectDatabaseServiceTest {

    // We have to ensure that Rooms executes all database operations instantly
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: DatabaseDataSource
    private val projectDatabaseService: ProjectDatabaseService by lazy { ProjectDatabaseServiceImpl(database) }

    private val projects = (1..2).map {
        Project(
                it.toLong(),
                "Project $it",
                "Description $it",
                Company("Company $it"),
                "URL $it",
                (1..2).map { Tag("Tag $it", "Color $it") }
        )
    }

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                DatabaseDataSource::class.java)
                .allowMainThreadQueries() // allowing main thread queries, just for testing
                .build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun refreshProjectsAndGetAllProjects() {
        projectDatabaseService.refreshProjects(projects)

        projectDatabaseService
                .getAllProjects()
                .test()
                .assertValue { it == projects }
    }

}