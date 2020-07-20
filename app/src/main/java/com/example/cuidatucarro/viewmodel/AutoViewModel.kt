package com.example.cuidatucarro.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cuidatucarro.data.repo.RepoAuto
import com.example.cuidatucarro.domain.FirestoreUseCase
import com.example.cuidatucarro.objetos.Autos

class AutoViewModel:ViewModel() {

    val firestoreUseCase = FirestoreUseCase()
    private val repo = RepoAuto()

    fun agregarNuevoVehocilo(idUsuario:String, patente:String, marca:String, modelo:String, km:Long, trasmi:String, image:Uri){
        firestoreUseCase.agregarNuevoVehiculo(idUsuario,patente,marca,modelo,km,trasmi,image)

    }


    fun traerDatosVehiculos(idUsuario: String):LiveData<MutableList<Autos>>{
        val mutableData = MutableLiveData<MutableList<Autos>>()

        repo.getDataAutos(idUsuario).observeForever(){ autosList->
            mutableData.value = autosList
        }

        return mutableData
    }

}