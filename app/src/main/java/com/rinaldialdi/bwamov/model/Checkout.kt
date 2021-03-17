package com.rinaldialdi.bwamov.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Checkout (
        val kursi : String ? = "",
        val harga : String ? = ""
        ) : Parcelable