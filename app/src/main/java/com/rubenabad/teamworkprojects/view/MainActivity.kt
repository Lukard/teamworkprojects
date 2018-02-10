package com.rubenabad.teamworkprojects.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.rubenabad.teamworkprojects.R
import com.rubenabad.teamworkprojects.data.State
import com.rubenabad.teamworkprojects.utils.extensions.endlessPlay
import com.rubenabad.teamworkprojects.utils.extensions.stop
import com.rubenabad.teamworkprojects.view.adapter.ProjectAdapter
import com.rubenabad.teamworkprojects.viewmodel.ProjectListViewModel
import jp.wasabeef.recyclerview.animators.FadeInAnimator
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.architecture.ext.viewModel


class MainActivity : AppCompatActivity() {

    private val viewModel: ProjectListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = ProjectAdapter()
        recycler.itemAnimator = FadeInAnimator().apply {
            addDuration = 500L
            removeDuration = 500L
            moveDuration = 500L
        }

        pullToRefresh.setOnRefreshListener { viewModel.refresh() }

        retry.setOnClickListener { viewModel.refresh() }

        viewModel.getAllProjects().observe(this, Observer { stateData ->
            when (stateData!!.state) {
                State.START_LOADING -> showLoading()
                State.END_LOADING -> hideLoading()
                State.SUCCESS -> (recycler.adapter as ProjectAdapter).items = stateData.data!!
                State.ERROR -> showError()
            }
        })
    }

    private fun showError() {
        if (recycler.adapter.itemCount == 0) {
            showFirstTimeError()
        } else {
            Snackbar.make(recycler, R.string.error_fetch_projects, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun showLoading() {
        hideFirstTimeError()
        if (recycler.adapter.itemCount == 0) {
            downloadIcon.visibility = View.VISIBLE
            downloadText.visibility = View.VISIBLE
            downloadIcon.endlessPlay(R.drawable.animated_ic_download)
        }
    }

    private fun hideLoading() {
        pullToRefresh.isRefreshing = false
        downloadIcon.stop()
        downloadIcon.visibility = View.GONE
        downloadText.visibility = View.GONE
    }

    private fun showFirstTimeError() {
        hideLoading()
        errorIcon.visibility = View.VISIBLE
        errorText.visibility = View.VISIBLE
        retry.visibility = View.VISIBLE
    }

    private fun hideFirstTimeError() {
        errorIcon.visibility = View.GONE
        errorText.visibility = View.GONE
        retry.visibility = View.GONE
    }

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
}
