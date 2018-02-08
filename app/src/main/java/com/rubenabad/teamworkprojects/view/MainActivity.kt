package com.rubenabad.teamworkprojects.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.rubenabad.teamworkprojects.R
import com.rubenabad.teamworkprojects.viewmodel.ProjectListViewModel
import org.koin.android.architecture.ext.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: ProjectListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getAllProjects().observe(this, Observer {
            it?.let { it.joinToString { it.logo }.run { Log.d("TAG", this) } }
        })

    }
}
