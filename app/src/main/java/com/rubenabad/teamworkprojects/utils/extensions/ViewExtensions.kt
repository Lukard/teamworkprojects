package com.rubenabad.teamworkprojects.utils.extensions

import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View

var View.backgroundCompat: Drawable
    get() = background
    set(value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            background = value
        } else {
            setBackgroundDrawable(value)
        }
    }