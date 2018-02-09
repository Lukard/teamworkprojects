package com.rubenabad.teamworkprojects.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.rubenabad.teamworkprojects.db.RoomContract
import com.rubenabad.teamworkprojects.db.entity.TagEntity
import io.reactivex.Single

/**
 * This DAO defines the database operations around the Tag
 */
@Dao
interface TagDao {

    @Insert
    fun insertAll(projects: List<TagEntity>): List<Long>

    @Query(RoomContract.SELECT_ALL_TAGS + " WHERE id IN (:ids)")
    fun getTagsById(ids: List<Long>): Single<List<TagEntity>>

}