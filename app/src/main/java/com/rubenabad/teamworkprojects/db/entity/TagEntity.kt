package com.rubenabad.teamworkprojects.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.rubenabad.teamworkprojects.db.RoomContract

@Entity(tableName = RoomContract.TABLE_TAG)
data class TagEntity(
        val name: String?,
        val color: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}