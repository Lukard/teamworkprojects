package com.rubenabad.teamworkprojects.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.rubenabad.teamworkprojects.R
import com.rubenabad.teamworkprojects.data.Project

class ProjectDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_detail)

        val project = intent.getParcelableExtra<Project>("project")
        Log.d("TTTTAG", project.toString())
    }
}