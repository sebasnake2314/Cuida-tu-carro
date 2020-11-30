package com.example.cuidatucarro.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cuidatucarro.objetos.Autos
import com.example.cuidatucarro.objetos.tipoMatenimientoMotor
import com.google.firebase.firestore.FirebaseFirestore

class RepoMantenimiento {

    fun traerMantenimientosMotor(): LiveData<MutableList<tipoMatenimientoMotor>>{
        val mutableData = MutableLiveData<MutableList<tipoMatenimientoMotor>>()
        FirebaseFirestore.getInstance().collection("tb_mant_motor").get().addOnSuccessListener { result ->
            val listData = mutableListOf<tipoMatenimientoMotor>()
            for(document in result ){
                val tipoMantenimiento = document.id.toString()

                val mantenimiento = tipoMatenimientoMotor(tipoMantenimiento!!)
                listData.add(mantenimiento)
            }
            mutableData.value = listData
        }
        return mutableData
    }


}