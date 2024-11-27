package com.example.time_manage;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.time_manage.DateConverter;

import java.util.Date;
import java.util.Objects;

@Entity(tableName = "tasks")
public class Task {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String title;
    private String description;

    @TypeConverters(DateConverter.class)
    private Date deadline;

    private Priority priority;
    private Category category;
    private boolean isCompleted;

    @TypeConverters(DateConverter.class)
    private Date completedAt;

    // Перечисления остаются без изменений
    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    public enum Category {
        WORK, STUDY, PERSONAL, OTHER
    }

    // Конструкторы
    public Task() {
        // Пустой конструктор для Room
    }
    @Ignore
    public Task(String title, String description, Date deadline,
                Priority priority, Category category) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.category = category;
        this.isCompleted = false;
    }

    // Геттеры и сеттеры
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
        this.completedAt = completed ? new Date() : null;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }


    // Методы equals и hashCode для корректной работы с коллекциями
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                isCompleted == task.isCompleted &&
                Objects.equals(title, task.title) &&
                Objects.equals(description, task.description) &&
                Objects.equals(deadline, task.deadline) &&
                priority == task.priority &&
                category == task.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, deadline, priority, category, isCompleted);
    }

    // Метод toString для удобладения отладки
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", priority=" + priority +
                ", category=" + category +
                ", isCompleted=" + isCompleted +
                '}';
    }
}