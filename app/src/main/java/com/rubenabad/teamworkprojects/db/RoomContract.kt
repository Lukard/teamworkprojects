package com.rubenabad.teamworkprojects.db

/**
 * This class provides Room common data to be used in Room related classes
 */
class RoomContract {

    companion object {
        const val DATABASE_NAME = "yak.db"

        const val TABLE_PROJECT = "project"
        const val TABLE_TAG = "tag"
        const val TABLE_PROJECT_TAG = "project_tag"

        private const val SELECT_ALL_FROM = "SELECT * FROM "

        const val SELECT_ALL_PROJECTS = SELECT_ALL_FROM + TABLE_PROJECT
        const val SELECT_ALL_PROJECT_TAG = SELECT_ALL_FROM + TABLE_PROJECT_TAG
        const val SELECT_ALL_TAGS = SELECT_ALL_FROM + TABLE_TAG
    }

}