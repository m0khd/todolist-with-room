package com.todo.todolistwithfragmentandrecyclerview.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDao {

    @Insert
    fun addTodo(todo: TodoModel)

    @Query("SELECT * FROM todo_list")
    fun getTodos():MutableList<TodoModel>

    @Query("DELETE FROM todo_list WHERE id=:id")
    fun deleteTodo(id:Int)

    @Query("UPDATE todo_list SET isChecked = :isChecked WHERE id= :id")
    fun isChecked(id:Int, isChecked:Boolean)
}