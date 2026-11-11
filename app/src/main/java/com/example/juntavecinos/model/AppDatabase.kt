package com.example.juntavecinos.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ReservaEntity::class, MovimientoEntity::class, Vecino::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun reservaDao(): ReservaDao
    abstract fun movimientoDao(): MovimientoDao
    abstract fun vecinoDao(): VecinoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "junta_vecinos_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}