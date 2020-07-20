package com.example.cuidatucarro.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cuidatucarro.domain.Repo
import com.example.cuidatucarro.objetos.Autos
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.contracts.Returns
import kotlin.text.toLong as toLong
import com.example.cuidatucarro.vo.Resource
class RepoAuto {

    fun getDataAutos(idUsuario:String):LiveData<MutableList<Autos>>{
        val mutableData = MutableLiveData<MutableList<Autos>>()
        FirebaseFirestore.getInstance().collection("tb_vehiculo_usuario")
            .whereEqualTo("usu_id_usuario_i",idUsuario)
            .get().addOnSuccessListener {result->

            val listData = mutableListOf<Autos>()

            for(document in result ){

                val imageUrl = document.getString("aut_image")
                val patente = document.getString("aut_patente_c")
                val marca = document.getString("aut_marca_c")
                val modelo = document.getString("aut_modelo_c")
                val km = document.getLong("aut_kilometraje_i")?.toInt()
                val trasminision = document.getString("aut_tipo_trasmision_c")

                val autos = Autos(imageUrl!!, patente!!,marca!!,modelo!!,km!!.toLong(),trasminision!!)
                listData.add(autos)
            }
            mutableData.value = listData
        }
        return mutableData
    }

}