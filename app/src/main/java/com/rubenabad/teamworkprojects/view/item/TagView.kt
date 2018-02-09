package com.rubenabad.teamworkprojects.view.item

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import com.rubenabad.teamworkprojects.R
import com.rubenabad.teamworkprojects.utils.extensions.backgroundCompat
import com.rubenabad.teamworkprojects.utils.extensions.px

class TagView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : TextView(context, attrs, defStyleAttr) {

    var color: Int = Color.WHITE
        set(value) {
            field = value
            DrawableCompat.setTint(backgroundCompat, value)
        }

    init {
        setPadding(12.px, 4.px, 12.px, 4.px)
        layoutParams = ViewGroup.MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
            rightMargin = 8.px
            bottomMargin = 8.px
        }
        ContextCompat
                .getDrawable(context, R.drawable.bg_tag)
                ?.let { drawable ->
                    val backgroundTag = DrawableCompat.wrap(drawable)
                    backgroundCompat = backgroundTag
                }
    }

}