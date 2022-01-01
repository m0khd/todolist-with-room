package com.todo.todolistwithfragmentandrecyclerview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.todo.todolistwithfragmentandrecyclerview.db.AppDatabase
import com.todo.todolistwithfragmentandrecyclerview.db.TodoDao
import com.todo.todolistwithfragmentandrecyclerview.db.TodoModel

class MainActivity : AppCompatActivity() {

    private lateinit var todoDao:TodoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todoDao = AppDatabase.getInstance(this)?.todoDao()!!

        getTodoList()

        val fab:View = findViewById(R.id.gotoAddTodoFragment)
        fab.setOnClickListener {

            // show dialog fragment
            AddTodoDialogFragment.newInstance().show(supportFragmentManager, null)

        }

    }

    fun getTodoList() {
        //val db = DBHelper(this, null)

        val recyclerView: RecyclerView = findViewById(R.id.rvTodoItems)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val todos:MutableList<TodoModel> = todoDao.getTodos().asReversed()
        val adapter:TodoAdapter =TodoAdapter(todos, this)
        recyclerView.adapter = adapter
    }

}