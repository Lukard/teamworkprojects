package com.rubenabad.teamworkprojects.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.rubenabad.teamworkprojects.db.RoomContract
import com.rubenabad.teamworkprojects.db.entity.ProjectTagEntity
import io.reactivex.Single

/**
 * This DAO defines the database operations around the relation many-to-many between Project and TAG
 */
@Dao
interface ProjectTagDao {

    @Insert
    fun insert(projectTag: ProjectTagEntity): Long

    @Insert
    fun insertAll(projectTags: List<ProjectTagEntity>): List<Long>

    @Query(RoomContract.SELECT_ALL_PROJECT_TAG + " WHERE project = :projectId")
    fun getProjectTagsById(projectId: Long): Single<List<ProjectTagEntity>>

    @Query("DELETE FROM project_tag")
    fun deleteAll()

}