package com.example.simpletkb.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.simpletkb.R
import com.example.simpletkb.utils.SharedPrefHelper
import com.example.simpletkb.data.Subject

class AddEditActivity : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtRoom: EditText
    private lateinit var edtTeacher: EditText
    private lateinit var edtDay: EditText
    private lateinit var edtTime: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        edtName = findViewById(R.id.edtName)
        edtRoom = findViewById(R.id.edtRoom)
        edtTeacher = findViewById(R.id.edtTeacher)
        edtDay = findViewById(R.id.edtDay)
        edtTime = findViewById(R.id.edtTime)
        btnSave = findViewById(R.id.btnSave)

        btnSave.setOnClickListener {
            val name = edtName.text.toString().trim()
            val room = edtRoom.text.toString().trim()
            val teacher = edtTeacher.text.toString().trim()
            val day = edtDay.text.toString().trim()
            val time = edtTime.text.toString().trim()

            if (name.isEmpty() || day.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên môn và thứ!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Tạo đối tượng môn học mới
            val newSubject = Subject(
                name = name,
                room = room,
                teacher = teacher,
                dayOfWeek = day,
                time = time
            )

            // Lưu vào danh sách cũ thông qua SharedPrefHelper
            val currentList = SharedPrefHelper.getSubjects(this)
            currentList.add(newSubject)
            SharedPrefHelper.saveSubjects(this, currentList)

            Toast.makeText(this, "Đã thêm môn học thành công!", Toast.LENGTH_SHORT).show()
            finish() // Đóng màn hình này để quay về màn hình chính
        }
    }
}