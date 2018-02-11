package com.rubenabad.teamworkprojects.view

import android.arch.lifecycle.Observer
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.rubenabad.teamworkprojects.R
import com.rubenabad.teamworkprojects.data.Project
import com.rubenabad.teamworkprojects.data.State.*
import com.rubenabad.teamworkprojects.view.adapter.TaskAdapter
import com.rubenabad.teamworkprojects.view.item.TagView
import com.rubenabad.teamworkprojects.viewmodel.ProjectDetailViewModel
import kotlinx.android.synthetic.main.activity_project_detail.*
import org.koin.android.architecture.ext.viewModel

class ProjectDetailActivity : AppCompatActivity() {

    private val viewModel: ProjectDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val project = intent.getParcelableExtra<Project>("project")

        supportActionBar?.title = project.name
        company.text = project.company?.name
        project.tags?.forEach { tag ->
            val tagView = TagView(this)
            tagView.apply {
                text = tag.name
                color = Color.parseColor(tag.color)
            }
            tags.addView(tagView)
        }
        description.text = project.description
        Glide.with(this)
                .load(project.logo)
                .into(logo)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = TaskAdapter()

        viewModel.getTasks(project.id).observe(this, Observer {
            it?.apply {
                when (state) {
                    START_LOADING -> {}
                    END_LOADING -> {}
                    SUCCESS -> (recycler.adapter as TaskAdapter).items = data!!
                    ERROR -> error?.printStackTrace()
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}