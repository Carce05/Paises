package com.paises.repository

import androidx.lifecycle.LiveData
import com.paises.data.PaisDao
import com.paises.model.Pais

class PaisesRepository(private val paisDao: PaisDao) {
    //Se implementan las funciones de la interface
    //Se crea un objeto que contiene el arrayListo de los registros de la tabla lugar... cubiertos por LiveData
    val getAllData: LiveData<List<Pais>> = paisDao.getAllData()

    //Se define la función para insertar un Pais en la tabla pais
    suspend fun addPais(pais: Pais) {
        paisDao.addPais(pais)
    }

    //Se define la función para actualizar un Pais en la tabla pais
    suspend fun updatePais(pais: Pais) {
        paisDao.updatePais(pais)
    }

    //Se define la función para eliminar un Pais en la tabla pais
    suspend fun deletePais(pais: Pais) {
        paisDao.deletePais(pais)
    }
}