package com.rinaldialdi.bwamov.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Plays (
        val nama : String ? = "",
        val url : String ? = ""
        ) : Parcelable