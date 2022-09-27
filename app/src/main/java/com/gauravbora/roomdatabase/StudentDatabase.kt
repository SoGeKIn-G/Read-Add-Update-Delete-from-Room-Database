package com.gauravbora.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Student::class], version = 1)
abstract class StudentDatabase : RoomDatabase() {

    abstract fun studentDao():StudentDao

    companion object {

        @Volatile
        private var instance: StudentDatabase? = null

        fun getDatabase(context: Context): StudentDatabase {
            val tempinstance = instance
            if (tempinstance != null) {
                return tempinstance
            }
            synchronized(this) {
                val instanceval = Room.databaseBuilder(
                    context.applicationContext, StudentDatabase::class.java, "Student Database"
                ).build()
                instance = instanceval
                return instance as StudentDatabase
            }
        }

    }

}