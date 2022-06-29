package com.paises.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.paises.model.Pais


@Dao
interface PaisDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPais(pais: Pais)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun  updatePais(pais: Pais)

    @Delete
    suspend fun deletePais(pais: Pais)

    @Query ("SELECT * FROM PAIS")
    fun getAllData() : LiveData<List<Pais>>
}