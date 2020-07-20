package com.example.cuidatucarro.domain

import android.net.Uri
import com.example.cuidatucarro.data.repo.FirebaseRepo

class FirestoreUseCase {

    //Instancia
    val repo = FirebaseRepo()

    //Clase para setear datos
    fun setearUsuarioFirestore(nombre:String,apellido:String,idUser:String){
        repo.setUserData(nombre,apellido,idUser)
    }

    fun obtenerDatosUsuarioFirestore(idUser: String){
        repo.obtenerUserData(idUser)
    }

    fun agregarNuevoVehiculo(idUsuario:String, patente:String, marca:String, modelo:String, km:Long, trasmi:String, image:Uri){
        repo.agregarNuevoVehiculo(idUsuario,patente,marca,modelo,km,trasmi,image)
    }

}