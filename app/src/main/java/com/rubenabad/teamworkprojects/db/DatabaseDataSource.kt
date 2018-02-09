package com.rubenabad.teamworkprojects.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.rubenabad.teamworkprojects.db.dao.ProjectDao
import com.rubenabad.teamworkprojects.db.dao.ProjectTagDao
import com.rubenabad.teamworkprojects.db.dao.TagDao
import com.rubenabad.teamworkprojects.db.entity.ProjectEntity
import com.rubenabad.teamworkprojects.db.entity.ProjectTagEntity
import com.rubenabad.teamworkprojects.db.entity.TagEntity

/**
 * This class indicates the entity and DAOs that will work with the database
 */
@Database(entities = [ProjectEntity::class, ProjectTagEntity::class, TagEntity::class], version = 1)
abstract class DatabaseDataSource : RoomDatabase() {

    abstract fun projectDao(): ProjectDao
    abstract fun tagDao(): TagDao
    abstract fun projectTagDao(): ProjectTagDao

    companion object {

        fun build(context: Context): DatabaseDataSource =
                Room.databaseBuilder(
                        context,
                        DatabaseDataSource::class.java,
                        RoomContract.DATABASE_NAME
                ).build()

    }

}