package com.rubenabad.teamworkprojects.utils.extensions

import android.content.res.Resources

val Float.px
    get() = this * Resources.getSystem().displayMetrics.density

val Int.px
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()