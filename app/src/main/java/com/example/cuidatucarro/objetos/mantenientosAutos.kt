package com.example.cuidatucarro.objetos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class mantenientosAutos(
    val fecha:String = "",
    val tipoMant:String = "",
    val desVehiculo:String = "",
    val patente:String
): Parcelable
