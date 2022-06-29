package com.paises.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.paises.model.Pais

@Database(entities = [Pais::class], version = 1, exportSchema = false)
abstract class PaisDatabase : RoomDatabase(){
    abstract fun paisDao() : PaisDao

    companion object {

        @Volatile
        private  var INSTANCE: PaisDatabase? = null

        fun getDatabase(context: android.content.Context) : PaisDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                PaisDatabase::class.java,
                "pais_database"
            ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}