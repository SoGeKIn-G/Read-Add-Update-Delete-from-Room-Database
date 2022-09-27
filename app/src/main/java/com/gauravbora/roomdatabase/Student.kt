package com.gauravbora.roomdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_table")
data class Student (

    @PrimaryKey(autoGenerate = true) val id:Long,
    val name:String,
   val rollNo:Long

        )