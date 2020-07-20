package com.example.cuidatucarro.data.network


import com.example.cuidatucarro.objetos.Autos
import com.example.cuidatucarro.vo.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class RepoImplement:IRepo {

    override suspend fun getDatosUsuarioRepo(idUsuario:String): Resource<String>{
     val db = FirebaseFirestore.getInstance()
         .collection("usuarios").document(idUsuario).get().await()
       val nombre = db.getString("Nombre")
       val apellido = db.getString("Apellido")

        return Resource.Success("$nombre $apellido" )
    }

    override suspend fun getDatosVehículo(): Resource<List<Autos>> {
        TODO("Not yet implemented")
    }

}

