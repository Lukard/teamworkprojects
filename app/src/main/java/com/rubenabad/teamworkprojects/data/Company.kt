package com.rubenabad.teamworkprojects.data

import android.os.Parcel
import com.rubenabad.teamworkprojects.utils.KParcelable
import com.rubenabad.teamworkprojects.utils.parcelableCreator

data class Company(val name: String?) : KParcelable {

    constructor(parcel: Parcel) : this(parcel.readString())

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) { writeString(name) }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Company)
    }

}