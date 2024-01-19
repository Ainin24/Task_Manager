package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditTaskActivity extends AppCompatActivity {

    private EditText taskNameEditText, dateEditText, timeEditText, noteEditText;
    private Button saveTaskButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        // Initialize views
        taskNameEditText = findViewById(R.id.taskNameEditText);
        dateEditText = findViewById(R.id.dateEditText);
        timeEditText = findViewById(R.id.timeEditText);
        noteEditText = findViewById(R.id.noteEditText);

        saveTaskButton = findViewById(R.id.saveTaskButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Retrieve existing task details from intent or any other data source
        // and populate the EditText fields for editing
        Task existingTask = (Task) getIntent().getSerializableExtra("taskDetails");
        if (existingTask != null) {
            taskNameEditText.setText(existingTask.getTaskName());
            dateEditText.setText(existingTask.getDate());
            timeEditText.setText(existingTask.getTime());
            noteEditText.setText(existingTask.getNote());
        }

        // Set click listeners
        saveTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement save edited task functionality
                saveEditedTask();
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

    private void saveEditedTask() {
        // Retrieve edited task details from EditText views
        String editedTaskName = taskNameEditText.getText().toString().trim();
        String editedDate = dateEditText.getText().toString().trim();
        String editedTime = timeEditText.getText().toString().trim();
        String editedNote = noteEditText.getText().toString().trim();

        // Validate if edited task name is not empty
        if (!editedTaskName.isEmpty()) {
            // Retrieve existing task details from intent
            Task existingTask = (Task) getIntent().getSerializableExtra("taskDetails");

            // Update existing task details with edited values
            if (existingTask != null) {
                existingTask.setTaskName(editedTaskName);
                existingTask.setDate(editedDate);
                existingTask.setTime(editedTime);
                existingTask.setNote(editedNote);

                setResult(RESULT_OK);

                // Navigate back to MainActivity
                Intent mainIntent = new Intent(EditTaskActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish(); // Close the EditTaskActivity
            }
        } else {
            // Show an error message if the edited task name is empty
            taskNameEditText.setError("Task name is required");
        }
    }
}
