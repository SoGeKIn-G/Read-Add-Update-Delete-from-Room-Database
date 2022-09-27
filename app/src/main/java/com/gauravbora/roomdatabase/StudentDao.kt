package com.gauravbora.roomdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface StudentDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun insertStudent(student: Student)


    @Query("UPDATE student_table SET name=:name,rollNo=:rollNo WHERE id LIKE  :id")
    suspend fun updateStudent(name: String,rollNo:Long,id:Long)

    @Query("DELETE FROM student_table")
    suspend fun deleteAllStudents()

    @Delete
    suspend  fun deleteStudent(student: Student)

    @Query("SELECT * FROM student_table")
     fun getAll():List<Student>


     @Query("SELECT * FROM student_table WHERE id LIKE:std_id LIMIT 1")
    suspend fun  findById(std_id:Long):Student

}