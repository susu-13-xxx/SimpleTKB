package com.example.simpletkb.ui

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletkb.R
import com.example.simpletkb.utils.SharedPrefHelper
import com.example.simpletkb.data.Subject

class SubjectAdapter(
    private var subjectList: MutableList<Subject>,
    private val onItemDeleted: () -> Unit
) : RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {

    class SubjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvSubjectName)
        val tvRoom: TextView = itemView.findViewById(R.id.tvRoom)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val tvTeacher: TextView = itemView.findViewById(R.id.tvTeacher)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_subject, parent, false)
        return SubjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val subject = subjectList[position]
        holder.tvName.text = "📖 ${subject.name}" // Thêm icon cuốn sách cho sinh động
        holder.tvRoom.text = "📍 Phòng: ${subject.room}"
        holder.tvTeacher.text = "GV: ${subject.teacher}👨‍🏫"
        holder.tvTime.text = "⏰ ${subject.dayOfWeek} (${subject.time})"

        // Bấm giữ để Xóa môn học
        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Xóa môn học 🗑️")
                .setMessage("Cậu có chắc muốn xóa môn '${subject.name}' không?")
                .setPositiveButton("Xóa") { _, _ ->
                    val context = holder.itemView.context
                    val fullList = SharedPrefHelper.getSubjects(context)
                    // Lọc bỏ môn học hiện tại theo ID
                    val updatedList = fullList.filter { it.id != subject.id }.toMutableList()
                    SharedPrefHelper.saveSubjects(context, updatedList)

                    onItemDeleted() // Gọi callback để làm mới lại giao diện chính
                }
                .setNegativeButton("Hủy", null)
                .show()
            true
        }
    }

    override fun getItemCount(): Int = subjectList.size

    fun updateList(newList: List<Subject>) {
        subjectList.clear()
        subjectList.addAll(newList)
        notifyDataSetChanged()
    }
}