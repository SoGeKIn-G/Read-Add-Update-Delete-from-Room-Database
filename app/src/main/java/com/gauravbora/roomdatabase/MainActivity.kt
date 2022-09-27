package com.gauravbora.roomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import androidx.room.util.DBUtil
import com.gauravbora.roomdatabase.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var studentDB: StudentDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        studentDB=StudentDatabase.getDatabase(this)

        binding.btnReadData.setOnClickListener{
            readData()
        }

        binding.btnWriteData.setOnClickListener{
            writeData()
        }

        binding.btnDeleteAll.setOnClickListener{
            GlobalScope.launch {
                studentDB.studentDao().deleteAllStudents()
            }
        }

        binding.btnUpdate.setOnClickListener{
         updateData()
        }

    }







    private fun updateData() {


        val std_id=binding.stId.text.toString()
        val name=binding.stName.text.toString()
        val uRoll=binding.etRollNo.text.toString()

        if(std_id.isNotEmpty() && name.isNotEmpty() && uRoll.isNotEmpty()){

            GlobalScope.launch(Dispatchers.IO) {
                studentDB.studentDao().updateStudent(name,uRoll.toLong(),std_id.toLong())
            }
            binding.etRollNo.text.clear()
            binding.stName.text.clear()
            binding.stId.text.clear()

            Toast.makeText(this,"Successfully Updated",Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this,"Please Fill Empty Entries",Toast.LENGTH_SHORT).show()
        }



    }


    private suspend fun displayData(student: Student){

        withContext(Dispatchers.Main){

            binding.rollNo.text=student.rollNo.toString()
            binding.id.text=student.id.toString()
            binding.name.text=student.name

        }

    }



    private fun readData(){

        val edText_id=binding.readDataByRollNo.text.toString()

        if(edText_id.isNotEmpty()){

            lateinit var student:Student
            GlobalScope.launch {
                student=studentDB.studentDao().findById(edText_id.toLong())
                displayData(student)
            }
        }

    }






    private fun writeData(){

        val std_id=binding.stId.text.toString()
        val name=binding.stName.text.toString()
        val uRoll=binding.etRollNo.text.toString()

        if(std_id.isNotEmpty() && name.isNotEmpty() && uRoll.isNotEmpty()){

            val student=Student(std_id.toLong(),name,uRoll.toLong())
            GlobalScope.launch(Dispatchers.IO) {
              studentDB.studentDao().insertStudent(student)
            }
            binding.etRollNo.text.clear()
            binding.stName.text.clear()
            binding.stId.text.clear()

            Toast.makeText(this,"Successfully added to the database",Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this,"Please Fill Empty Entries",Toast.LENGTH_SHORT).show()
        }

    }
}

