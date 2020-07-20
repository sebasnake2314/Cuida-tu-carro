package com.example.cuidatucarro.data.network

import com.example.cuidatucarro.objetos.Autos
import com.example.cuidatucarro.vo.Resource

interface IRepo {
    suspend fun getDatosUsuarioRepo(idUsuario:String): Resource<String>

    suspend fun getDatosVehículo():Resource<List<Autos>>
}

