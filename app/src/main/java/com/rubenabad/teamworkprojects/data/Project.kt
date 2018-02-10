package com.rubenabad.teamworkprojects.data

import android.os.Parcel
import com.rubenabad.teamworkprojects.utils.KParcelable
import com.rubenabad.teamworkprojects.utils.parcelableCreator
import com.rubenabad.teamworkprojects.utils.readTypedObjectCompat
import com.rubenabad.teamworkprojects.utils.writeTypedObjectCompat


data class Project(val name: String?, val description: String?, val company: Company?, val logo: String?,
                   val tags: List<Tag>?) : KParcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readTypedObjectCompat(Company.CREATOR),
            parcel.readString(),
            arrayListOf<Tag>().apply { parcel.readTypedList(this, Tag.CREATOR) })

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(description)
        writeTypedObjectCompat(company, flags)
        writeString(logo)
        writeTypedList(tags)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Project)
    }

}