package com.example.cuidatucarro.objetos

data class Autos(
    val aut_image: String = "DEFAULT URL",
    val aut_patente_c: String = "",
    val aut_marca_c: String = "",
    val aut_modelo_c: String = "",
    val aut_kilometraje_i: Long = 0,
    val aut_tipo_trasmision_c: String = ""
)