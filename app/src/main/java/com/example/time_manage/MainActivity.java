package com.example.time_manage;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.time_manage.R;
import com.example.time_manage.TaskAdapter;
import com.example.time_manage.TaskViewModel;

public class MainActivity extends AppCompatActivity {
    private TaskViewModel taskViewModel;
    private RecyclerView tasksRecyclerView;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUI();
        setupViewModel();

        // Добавление обработчика для FAB
        findViewById(R.id.fab_add_task).setOnClickListener(v -> {
            CreateTaskDialogFragment dialogFragment = new CreateTaskDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "create_task");
        });
    }

    private void initializeUI() {
        tasksRecyclerView = findViewById(R.id.recycler_tasks);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapter();
        tasksRecyclerView.setAdapter(taskAdapter);
    }

    private void setupViewModel() {
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.getAllTasks().observe(this, tasks -> {
            taskAdapter.setTasks(tasks);
        });
    }
}