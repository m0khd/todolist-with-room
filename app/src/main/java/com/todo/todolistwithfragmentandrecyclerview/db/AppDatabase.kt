package com.todo.todolistwithfragmentandrecyclerview.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TodoModel::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun todoDao():TodoDao

    companion object {
        private lateinit var instance : AppDatabase

        fun getInstance(context: Context) : AppDatabase? {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "todo_app"
                    )
                        .allowMainThreadQueries()
                        .build()
                }

            return instance
        }
    }
}