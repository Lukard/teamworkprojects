package com.rubenabad.teamworkprojects.db.dao

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.rubenabad.teamworkprojects.db.DatabaseDataSource
import com.rubenabad.teamworkprojects.db.entity.ProjectEntity
import com.rubenabad.teamworkprojects.db.entity.ProjectTagEntity
import com.rubenabad.teamworkprojects.db.entity.TagEntity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * This class is meant to test the possibles scenarios that the ProjectDao may face
 */
@RunWith(AndroidJUnit4::class)
class ProjectDaoTest {

    // We have to ensure that Rooms executes all database operations instantly
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: DatabaseDataSource

    private val projects = (1..2).map {
        ProjectEntity(
                "Project $it",
                "Description $it",
                "Company $it",
                "URL $it")
    }

    private val tags = (1..2).map {
        TagEntity(
                "Tag $it",
                "Color $it")
    }

    private val projectsAndTags: List<ProjectTagEntity> by lazy {
        (0..1).map {
            listOf(
                    ProjectTagEntity(projects[it].id, tags[0].id),
                    ProjectTagEntity(projects[it].id, tags[1].id)
            )
        }.flatten()
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
    fun insertAndGetAllProjects() {
        database.projectDao()
                .insertAll(projects)
                .forEachIndexed { index, id ->
                    projects[index].id = id
                }

        database.tagDao()
                .insertAll(tags)
                .forEachIndexed { index, id ->
                    tags[index].id = id
                }

        database.projectTagDao()
                .insertAll(projectsAndTags)


        database.projectDao()
                .getAllProjects()
                .test()
                .assertValue {
                    var sameProjects = true
                    it.forEachIndexed { index, project ->
                        sameProjects =
                                project.name == projects[index].name &&
                                project.description == projects[index].description &&
                                project.company == projects[index].company &&
                                project.logo == projects[index].logo
                        if (!sameProjects) return@forEachIndexed
                    }
                    sameProjects
                }



    }

}