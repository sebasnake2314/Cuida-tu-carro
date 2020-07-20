package com.example.cuidatucarro.domain

import com.example.cuidatucarro.data.network.IRepo
import com.example.cuidatucarro.objetos.Autos
import com.example.cuidatucarro.vo.Resource

class UseCaseImpl(private val repo:IRepo):IUseCase {

    override suspend fun getDatosUsuario(idUsuario:String): Resource<String> = repo.getDatosUsuarioRepo(idUsuario)



}