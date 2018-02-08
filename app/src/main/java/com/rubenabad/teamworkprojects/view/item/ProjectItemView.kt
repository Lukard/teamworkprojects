package com.rubenabad.teamworkprojects.view.item

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.rubenabad.teamworkprojects.R
import com.rubenabad.teamworkprojects.data.Project
import com.rubenabad.teamworkprojects.utils.extensions.px
import kotlinx.android.synthetic.main.item_project.view.*

class ProjectItemView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.item_project, this, true)
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        useCompatPadding = true
        radius = 10f.px
        cardElevation = 16f.px
    }

    fun bind(project: Project) {
        Glide.with(this)
                .load(project.logo)
                .listener(
                        GlidePalette.with(project.logo)
                        .use(BitmapPalette.Profile.VIBRANT_DARK)
                                .intoBackground(titleCompanyBackground)
                                .intoBackground(descriptionBackground)
                                .intoTextColor(title)
                                .intoTextColor(company)
                                .intoTextColor(description)
                ).into(logo)
        title.text = project.name
        company.text = project.company.name
        description.text = project.description

        invalidate()
    }
}