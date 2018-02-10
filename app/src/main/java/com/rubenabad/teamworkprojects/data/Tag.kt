package com.rubenabad.teamworkprojects.data

import android.os.Parcel
import com.rubenabad.teamworkprojects.utils.KParcelable
import com.rubenabad.teamworkprojects.utils.parcelableCreator

data class Tag(val name: String?, val color: String?) : KParcelable {

    constructor(parcel: Parcel) : this(parcel.readString(), parcel.readString())

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(color)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Tag)
    }

}