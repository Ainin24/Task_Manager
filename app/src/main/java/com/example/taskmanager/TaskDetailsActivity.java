package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TaskDetailsActivity extends AppCompatActivity {

    private TextView taskDetailsTextView;
    private Button editTaskButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        // Initialize views
        taskDetailsTextView = findViewById(R.id.taskDetailsTextView);
        editTaskButton = findViewById(R.id.editTaskButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Retrieve task details from intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("taskDetails")) {
            // Get the task details from the intent
            Task selectedTask = (Task) intent.getSerializableExtra("taskDetails");

            // Display the task details
            if (selectedTask != null) {
                String taskDetails = "Task ID: " + selectedTask.getTaskId() + "\n"
                        + "Task Name: " + selectedTask.getTaskName() + "\n"
                        + "Date: " + selectedTask.getDate() + "\n"
                        + "Time: " + selectedTask.getTime() + "\n"
                        + "Note: " + selectedTask.getNote();

                taskDetailsTextView.setText(taskDetails);
            }
        }

        // Set click listeners
        editTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to EditTaskActivity
                navigateToEditTask();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement cancel functionality
                finish(); // Close the activity
            }
        });
    }

    private void navigateToEditTask() {
        // Retrieve task details from intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("taskDetails")) {
            // Get the task details from the intent
            Task selectedTask = (Task) intent.getSerializableExtra("taskDetails");

            // Open EditTaskActivity and pass the task details
            Intent editTaskIntent = new Intent(TaskDetailsActivity.this, EditTaskActivity.class);
            editTaskIntent.putExtra("taskDetails", selectedTask);
            startActivity(editTaskIntent);
        }
    }
}
