package com.rubenabad.teamworkprojects.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.rubenabad.teamworkprojects.db.RoomContract
import com.rubenabad.teamworkprojects.db.entity.ProjectEntity
import io.reactivex.Single

/**
 * This DAO defines the database operations around the Project
 */
@Dao
interface ProjectDao {

    @Insert
    fun insert(project: ProjectEntity): Long

    @Insert
    fun insertAll(projects: List<ProjectEntity>): List<Long>

    @Query(RoomContract.SELECT_ALL_PROJECTS)
    fun getAllProjects(): Single<List<ProjectEntity>>

    @Query("DELETE FROM project")
    fun deleteAll()

}