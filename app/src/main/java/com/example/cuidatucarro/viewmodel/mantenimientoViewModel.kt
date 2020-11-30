package com.example.cuidatucarro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cuidatucarro.data.repo.RepoMantenimiento
import com.example.cuidatucarro.objetos.tipoMatenimientoMotor

class mantenimientoViewModel: ViewModel()  {
    private val repoMatenimiento = RepoMantenimiento()

    fun traerTipoMantenimientoMotor(): LiveData<MutableList<tipoMatenimientoMotor>> {
        val mutableData = MutableLiveData<MutableList<tipoMatenimientoMotor>>()
        repoMatenimiento.traerMantenimientosMotor().observeForever{listaMantenimiento->
            mutableData.value = listaMantenimiento
        }
        return mutableData
    }

}