package com.example.time_manage;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.time_manage.Task;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface TaskDao {
    @Insert
    Completable insert(Task task);

    @Query("SELECT * FROM tasks")
    Single<List<Task>> getAllTasks();

    @Update
    Completable update(Task task);

    @Delete
    Completable delete(Task task);

    @Query("SELECT * FROM tasks WHERE category = :category")
    Single<List<Task>> getTasksByCategory(Task.Category category);
}
