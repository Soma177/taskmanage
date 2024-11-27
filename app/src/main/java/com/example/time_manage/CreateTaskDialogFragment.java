package com.example.time_manage;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.example.time_manage.R;
import com.example.time_manage.Task;
import com.example.time_manage.TaskViewModel;

import java.util.Date;

public class CreateTaskDialogFragment extends DialogFragment {
    private EditText titleEditText;
    private EditText descriptionEditText;
    private Spinner prioritySpinner;
    private Spinner categorySpinner;
    private EditText deadlineEditText;

    private TaskViewModel viewModel;
    private Date selectedDeadline;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_create_task, null);
        initializeViews(view);
        setupSpinners();

        builder.setView(view)
                .setTitle("Создать задачу")
                .setPositiveButton("Сохранить", (dialog, id) -> saveTask())
                .setNegativeButton("Отмена", (dialog, id) -> dismiss());

        viewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);

        return builder.create();
    }

    private void initializeViews(View view) {
        titleEditText = view.findViewById(R.id.edit_task_title);
        descriptionEditText = view.findViewById(R.id.edit_task_description);
        prioritySpinner = view.findViewById(R.id.spinner_priority);
        categorySpinner = view.findViewById(R.id.spinner_category);
        deadlineEditText = view.findViewById(R.id.edit_task_deadline);

        deadlineEditText.setOnClickListener(v -> showDatePicker());
    }

    private void setupSpinners() {
        // Настройка спиннера приоритетов
        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                new String[]{"Низкий", "Средний", "Высокий"}
        );
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(priorityAdapter);

        // Настройка спиннера категорий
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                new String[]{"Работа", "Учеба", "Личное", "Другое"}
        );
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
    }

    private void showDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder
                .datePicker()
                .setTitleText("Выберите дедлайн")
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            selectedDeadline = new Date(selection);
            deadlineEditText.setText(android.text.format.DateFormat
                    .getDateFormat(requireContext())
                    .format(selectedDeadline));
        });

        datePicker.show(getParentFragmentManager(), "deadline_picker");
    }

    private void saveTask() {
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setDeadline(selectedDeadline);

        // Преобразование выбранных значений в enum
        task.setPriority(convertPriority(prioritySpinner.getSelectedItemPosition()));
        task.setCategory(convertCategory(categorySpinner.getSelectedItemPosition()));

        // Сохранение задачи через ViewModel
        viewModel.insertTask(task)
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe();
    }

    private Task.Priority convertPriority(int position) {
        switch (position) {
            case 0: return Task.Priority.LOW;
            case 1: return Task.Priority.MEDIUM;
            case 2: return Task.Priority.HIGH;
            default: return Task.Priority.MEDIUM;
        }
    }

    private Task.Category convertCategory(int position) {
        switch (position) {
            case 0: return Task.Category.WORK;
            case 1: return Task.Category.STUDY;
            case 2: return Task.Category.PERSONAL;
            case 3: return Task.Category.OTHER;
            default: return Task.Category.OTHER;
        }
    }
}
