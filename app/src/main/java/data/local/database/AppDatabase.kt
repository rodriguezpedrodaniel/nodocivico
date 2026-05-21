package com.rodriguez.nodocivico.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rodriguez.nodocivico.data.local.dao.ReporteDao
import com.rodriguez.nodocivico.data.local.entity.Reporte

@Database(
    entities = [Reporte::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun reporteDao(): ReporteDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context
        ): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "nodo_civico_db"
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }
}