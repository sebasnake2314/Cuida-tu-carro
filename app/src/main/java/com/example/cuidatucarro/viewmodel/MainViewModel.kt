package com.example.cuidatucarro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.cuidatucarro.data.model.Usuario
import com.example.cuidatucarro.domain.FirestoreUseCase
import com.example.cuidatucarro.domain.IUseCase
import com.example.cuidatucarro.domain.Repo
import com.example.cuidatucarro.vo.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MainViewModel(useCase:IUseCase):ViewModel() {

    var idUsuario:String = ""

    val fetchdatosusuario = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            var nombre = useCase.getDatosUsuario(idUsuario = idUsuario)
            emit(nombre)
        }catch (e:Exception){
            emit(Resource.Failure(e))
        }
    }
}
