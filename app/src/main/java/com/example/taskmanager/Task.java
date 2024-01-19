package com.example.taskmanager;

import java.io.Serializable;

public class Task implements Serializable {

    private String taskId;
    private String taskName;
    private String date;
    private String time;
    private String note;

    public Task() {
    }

    public Task(String taskId, String taskName, String date, String time, String note) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.date = date;
        this.time = time;
        this.note = note;
    }

    public String getTaskId() {

        return taskId;
    }

    public void setTaskId(String taskId) {

        this.taskId = taskId;
    }

    public String getTaskName() {

        return taskName;
    }

    public void setTaskName(String taskName) {

        this.taskName = taskName;
    }

    public String getDate() {

        return date;
    }

    public void setDate(String date) {

        this.date = date;
    }

    public String getTime() {

        return time;
    }

    public void setTime(String time) {

        this.time = time;
    }

    public String getNote() {

        return note;
    }

    public void setNote(String note) {

        this.note = note;
    }
}
