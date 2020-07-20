package com.example.cuidatucarro.domain

import com.example.cuidatucarro.vo.Resource

interface IUseCase {

    suspend fun getDatosUsuario(idUsuario:String):Resource<String>

}