package com.rubenabad.teamworkprojects.view.item

import android.content.Context
import android.os.Build
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.rubenabad.teamworkprojects.R
import com.rubenabad.teamworkprojects.data.Task
import com.rubenabad.teamworkprojects.utils.extensions.px
import kotlinx.android.synthetic.main.item_task.view.*

class TaskItemView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.item_task, this, true)
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        useCompatPadding = true
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            preventCornerOverlap = false
        }
        radius = 10f.px
        cardElevation = 4f.px
    }

    fun bind(task: Task) {
        title.text = task.content
    }

}