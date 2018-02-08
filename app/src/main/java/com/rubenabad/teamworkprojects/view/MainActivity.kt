package com.rubenabad.teamworkprojects.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.rubenabad.teamworkprojects.R
import com.rubenabad.teamworkprojects.view.adapter.ProjectAdapter
import com.rubenabad.teamworkprojects.viewmodel.ProjectListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.architecture.ext.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: ProjectListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = ProjectAdapter()

        viewModel.getAllProjects().observe(this, Observer { projects ->
            projects?.let { (recycler.adapter as ProjectAdapter).items = it }
        })
    }
}
