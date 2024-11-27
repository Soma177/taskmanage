package com.example.time_manage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.time_manage.AppDatabase;
import com.example.time_manage.Task;
import com.example.time_manage.TaskRepository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository taskRepository;
    private MutableLiveData<List<Task>> allTasks = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public TaskViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(application);
        taskRepository = new TaskRepository(database.taskDao());
        loadTasks();
    }

    // Остальной код остается без изменений
    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void loadTasks() {
        isLoading.setValue(true);
        taskRepository.getAllTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        tasks -> {
                            allTasks.setValue(tasks);
                            isLoading.setValue(false);
                        },
                        error -> {
                            isLoading.setValue(false);
                        }
                );
    }

    public Completable insertTask(Task task) {
        return taskRepository.insertTask(task)
                .doOnComplete(this::loadTasks);
    }

    public Completable updateTask(Task task) {
        return taskRepository.updateTask(task)
                .doOnComplete(this::loadTasks);
    }

    public Completable deleteTask(Task task) {
        return taskRepository.deleteTask(task)
                .doOnComplete(this::loadTasks);
    }
}
