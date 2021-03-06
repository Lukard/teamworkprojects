package com.rubenabad.teamworkprojects.view.item

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.rubenabad.teamworkprojects.R
import com.rubenabad.teamworkprojects.data.Project
import com.rubenabad.teamworkprojects.utils.extensions.px
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.item_project.view.*

class ProjectItemView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.item_project, this, true)
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        useCompatPadding = true
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            preventCornerOverlap = false
        }
        radius = 10f.px
        cardElevation = 4f.px
    }

    fun bind(project: Project) {
        setLogo(project)
        setTitle(project)
        setCompany(project)
        setTags(project)
        setDescription(project)
    }

    private fun setLogo(project: Project) {
        if (project.logo != null && logo != null) {
            Glide.with(this)
                    .load(project.logo)
                    .apply(bitmapTransform(BlurTransformation(25)))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(logo)
        }
    }

    private fun setTitle(project: Project) {
        title.text = project.name ?: context.getString(R.string.unknown_project_name)
    }

    private fun setCompany(project: Project) {
        if (project.company != null && !project.company.name.isNullOrBlank()) {
            company.text = project.company.name
        } else {
            company.visibility = GONE
        }
    }

    private fun setTags(project: Project) {
        if (project.tags != null) {
            tags.visibility = VISIBLE
            tags.removeAllViews()
            project.tags.forEach { tag ->
                val tagView = TagView(context)
                tagView.apply {
                    text = tag.name
                    color = Color.parseColor(tag.color)
                }
                tags.addView(tagView)
            }
        } else {
            tags.visibility = GONE
        }
    }

    private fun setDescription(project: Project) {
        description.text = project.description
    }
}