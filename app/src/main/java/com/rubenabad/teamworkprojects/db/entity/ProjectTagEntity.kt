package com.rubenabad.teamworkprojects.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import com.rubenabad.teamworkprojects.db.RoomContract

@Entity(tableName = RoomContract.TABLE_PROJECT_TAG,
        foreignKeys = [
            ForeignKey(entity = ProjectEntity::class, parentColumns = ["id"], childColumns = ["project"]),
            ForeignKey(entity = TagEntity::class, parentColumns = ["id"], childColumns = ["tag"])
        ]
)
data class ProjectTagEntity(
        val project: Long,
        val tag: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

}