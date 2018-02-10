package com.rubenabad.teamworkprojects.utils.extensions

import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.DrawableRes
import android.support.graphics.drawable.Animatable2Compat
import android.support.graphics.drawable.AnimatedVectorDrawableCompat
import android.view.View
import android.widget.ImageView

/**
 * Property extension of View to set a drawable as its background
 */
@Suppress("DEPRECATION")
var View.backgroundCompat: Drawable
    get() = background
    set(value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            background = value
        } else {
            setBackgroundDrawable(value)
        }
    }

/**
 * Function extension of ImageView to set an animated vector drawable and play it indefinitely. It works since API 14.
 */
fun ImageView.endlessPlay(@DrawableRes drawableId: Int) {
    val animatedVectorDrawableCompat = AnimatedVectorDrawableCompat.create(context, drawableId)
    animatedVectorDrawableCompat?.let {
        setImageDrawable(it)
        tag = true
        it.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                if (tag is Boolean && tag as Boolean) {
                    this@endlessPlay.postDelayed({ it.start() }, 250L)
                }
            }
        })
        it.start()
    }


}

/**
 * Function extension of ImageView to stop an animation vector drawable. It works since API 14.
 */
fun ImageView.stop() {
    if (drawable is Animatable) {
        tag = false
        (drawable as Animatable).stop()
    }
}