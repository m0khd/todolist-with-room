package com.todo.todolistwithfragmentandrecyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.todo.todolistwithfragmentandrecyclerview.db.AppDatabase
import com.todo.todolistwithfragmentandrecyclerview.db.TodoDao
import com.todo.todolistwithfragmentandrecyclerview.db.TodoModel


class TodoAdapter (private var todos:MutableList<TodoModel>, private val context: Context)
    : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    var todoDao: TodoDao = AppDatabase.getInstance(context)?.todoDao()!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view:View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo = todos[position]
        holder.bindItems(curTodo)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    fun addTodo(todo: TodoModel) {
        todoDao.addTodo(todo)
        todos = todoDao.getTodos().asReversed()
        notifyItemInserted(0)
    }

    private fun toggleStrikeThrough(title:TextView, isChecked:Boolean) {
        if(isChecked) {
            title.paintFlags = title.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            title.paintFlags = title.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val tvTitle:TextView = itemView.findViewById(R.id.tvTodoTitle)
        private val tvDate:TextView = itemView.findViewById(R.id.tvTodoDate)
        private val cbDone:CheckBox = itemView.findViewById(R.id.cbDone)
        private val ivDelete = itemView.findViewById<ImageView>(R.id.ivDelete)


        fun bindItems(todo:TodoModel) {

            tvTitle.text = todo.title
            tvDate.text = todo.date
            cbDone.isChecked = todo.isChecked

            toggleStrikeThrough(tvTitle, cbDone.isChecked)

            cbDone.setOnClickListener {
                toggleStrikeThrough(tvTitle, cbDone.isChecked)
                todoDao.isChecked(todo.id, cbDone.isChecked)
                todos = todoDao.getTodos().asReversed()
            }

            ivDelete.setOnClickListener {
                deleteTodo(todo.id)
            }

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteTodo(position: Int) {
        todoDao.deleteTodo(position)
        todos = todoDao.getTodos().asReversed()
        notifyDataSetChanged()
    }

}