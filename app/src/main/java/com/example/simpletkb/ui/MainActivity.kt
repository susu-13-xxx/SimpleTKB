package com.example.simpletkb.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletkb.R
import com.example.simpletkb.utils.SharedPrefHelper
import com.example.simpletkb.data.Subject

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutEmpty: LinearLayout
    private lateinit var btnAddSubject: Button
    private lateinit var adapter: SubjectAdapter

    private var selectedDay: String = "Tất cả"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        layoutEmpty = findViewById(R.id.layoutEmpty)
        btnAddSubject = findViewById(R.id.btnAddSubject)

        // 👉 ĐẶT ĐOẠN CODE TÌM KIẾM Ở ĐÂY (Bên trong hàm onCreate)
        val edtSearch = findViewById<EditText>(R.id.edtSearch)
        edtSearch.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val keyword = s.toString().trim().lowercase()
                val allSubjects = SharedPrefHelper.getSubjects(this@MainActivity)

                val searchList = allSubjects.filter {
                    it.name.lowercase().contains(keyword) || it.teacher.lowercase().contains(keyword)
                }.toMutableList()

                adapter.updateList(searchList)
            }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })

        recyclerView.layoutManager = LinearLayoutManager(this)

        // Bắt sự kiện bấm nút lọc theo Thứ
        setupDayFilterButtons()

        // Bấm nút chuyển sang màn hình Thêm môn học
        btnAddSubject.setOnClickListener {
            val intent = Intent(this, AddEditActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupDayFilterButtons() {
        val btnAll = findViewById<Button>(R.id.btnAll)
        val btnT2 = findViewById<Button>(R.id.btnT2)
        val btnT3 = findViewById<Button>(R.id.btnT3)
        val btnT4 = findViewById<Button>(R.id.btnT4)
        val btnT5 = findViewById<Button>(R.id.btnT5)
        val btnT6 = findViewById<Button>(R.id.btnT6)
        val btnT7 = findViewById<Button>(R.id.btnT7)
        val btnCN = findViewById<Button>(R.id.btnCN)

        val buttons = listOf(btnAll, btnT2, btnT3, btnT4, btnT5, btnT6, btnT7, btnCN)

        buttons.forEach { button ->
            button.setOnClickListener {
                selectedDay = button.text.toString()
                loadData()
            }
        }
    }

    private fun loadData() {
        val allSubjects = SharedPrefHelper.getSubjects(this)

        // Lọc danh sách theo Thứ được chọn
        val filteredList = if (selectedDay == "Tất cả") {
            allSubjects
        } else {
            allSubjects.filter { it.dayOfWeek.equals(selectedDay, ignoreCase = true) }.toMutableList()
        }

        // Khởi tạo Adapter kèm callback khi xóa
        adapter = SubjectAdapter(filteredList) {
            loadData() // Load lại dữ liệu ngay sau khi bấm xóa
        }
        recyclerView.adapter = adapter

        // Kiểm tra xem danh sách có trống không để ẩn/hiện thùng rỗng
        if (filteredList.isEmpty()) {
            layoutEmpty.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            layoutEmpty.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
}