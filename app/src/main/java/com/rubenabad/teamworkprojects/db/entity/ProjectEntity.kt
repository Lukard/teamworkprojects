package com.rubenabad.teamworkprojects.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.rubenabad.teamworkprojects.db.RoomContract

@Entity(tableName = RoomContract.TABLE_PROJECT)
data class ProjectEntity(
        val name: String?,
        val description: String?,
        val company: String?,
        val logo: String?,
        val webId: Long
) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}