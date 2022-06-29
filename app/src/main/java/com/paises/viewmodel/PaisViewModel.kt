package com.paises.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.paises.data.PaisDatabase
import com.paises.model.Pais
import com.paises.repository.PaisesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaisViewModel(application: Application) : AndroidViewModel(application) {
    val getAllData: LiveData<List<Pais>>
    //Esta es la manera como accedo al repositorio desde el viewModel
    private val repository: PaisesRepository
    //Se procede a inicializar los atributos de arriba de esta clase PaisViewModel
    init {
        val paisDao = PaisDatabase.getDatabase(application).paisDao()
        repository = PaisesRepository(paisDao)
        getAllData = repository.getAllData
    }
    //Esta función de alto nivel llama al subproceso de I/O para grabar un registro Pais
    fun addPais (pais: Pais) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPais(pais)
        }
    }
    //Esta función de alto nivel llama al subproceso de I/O para actualizar un registro Pais
    fun updatePais (pais: Pais) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePais(pais)
        }
    }
    //Esta función de alto nivel llama al subproceso de I/O para eliminar un registro Pais
    fun deletePais (pais: Pais) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePais(pais)
        }
    }
}
