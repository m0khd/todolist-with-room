package com.todo.todolistwithfragmentandrecyclerview.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_list")
class TodoModel(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val title:String,
    val date:String,
    val isChecked: Boolean = false
) {
}