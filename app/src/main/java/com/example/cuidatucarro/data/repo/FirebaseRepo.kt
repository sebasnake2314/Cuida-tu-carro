package com.example.cuidatucarro.data.repo

import android.net.Uri
import android.util.Log
import com.example.cuidatucarro.data.model.Usuario
import com.example.cuidatucarro.vo.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FirebaseRepo {

    val db = FirebaseFirestore.getInstance()

    val generateUsuarioList = Resource.Success(
        listOf(
    Usuario("Solid","Snake")
    ))

    fun setUserData(nombre:String,apellido:String,idUser:String){
        val userHashMap = hashMapOf(
            "Nombre" to nombre,
            "Apellido" to apellido
        )
        db.document("usuarios/"+idUser)
            .set(userHashMap).addOnCompleteListener{
                if(it.isSuccessful){
                    //Mensaje Exitoso
                }else{
                    //Mensaje Error
                }
            }
    }

    fun obtenerUserData(idUser:String){

        var listaUsuario = mutableListOf<Usuario>()

        db.collection("usuarios").document(idUser).get().addOnSuccessListener {documento ->

            if(documento.exists()){
                val usuario = documento.toObject(Usuario::class.java)
                if (usuario != null) {
                    listaUsuario.add(usuario)
                    Log.d("Datos: " , "$listaUsuario"  )
                }

            }else{
                Log.d("Datos doc: " ,"No existe")
            }
        }
    }


    fun agregarNuevoVehiculo(idUsuario:String, patente:String, marca:String, modelo:String, km:Long, trasmi:String, image:Uri){

        val userHashMap = hashMapOf(
            "usu_id_usuario_i" to idUsuario,
            "aut_image" to image.toString(),
            "aut_patente_c" to patente,
            "aut_marca_c" to marca,
            "aut_modelo_c" to modelo,
            "aut_kilometraje_i" to km,
            "aut_fecha_alta_f" to Timestamp(System.currentTimeMillis()),
            "aut_tipo_trasmision_c" to trasmi)

        db.collection("tb_vehiculo_usuario")
            .add(userHashMap).addOnCompleteListener{
                if(it.isSuccessful){
                    //Mensaje Exitoso
                }else{
                    //Mensaje Error
                }
            }


    }



}