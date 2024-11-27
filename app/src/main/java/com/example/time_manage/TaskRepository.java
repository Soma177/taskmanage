package com.example.time_manage;

import com.example.time_manage.Task;
import com.example.time_manage.TaskDao;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class TaskRepository {
    private TaskDao taskDao;

    public TaskRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public Single<List<Task>> getAllTasks() {
        return taskDao.getAllTasks();
    }

    public Completable insertTask(Task task) {
        return taskDao.insert(task);
    }

    public Completable updateTask(Task task) {
        return taskDao.update(task);
    }

    public Completable deleteTask(Task task) {
        return taskDao.delete(task);
    }

    public Single<List<Task>> getTasksByCategory(Task.Category category) {
        return taskDao.getTasksByCategory(category);
    }
}
