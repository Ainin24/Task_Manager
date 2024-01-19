package com.example.taskmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTaskActivity extends AppCompatActivity {

    private EditText taskNameEditText, dateEditText, timeEditText, noteEditText;
    private Button saveTaskButton, cancelButton;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Initialize Firebase Database with the specific URL
        String databaseUrl = "https://taskmanager-521f8-default-rtdb.asia-southeast1.firebasedatabase.app/";
        FirebaseDatabase database = FirebaseDatabase.getInstance(databaseUrl);
        databaseReference = database.getReference("tasks");

        // Initialize views
        taskNameEditText = findViewById(R.id.taskNameEditText);
        dateEditText = findViewById(R.id.dateEditText);
        timeEditText = findViewById(R.id.timeEditText);
        noteEditText = findViewById(R.id.noteEditText);

        saveTaskButton = findViewById(R.id.saveTaskButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Set click listeners
        saveTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask();
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

    private void saveTask() {
        // Get task details from EditText views
        String taskName = taskNameEditText.getText().toString().trim();
        String date = dateEditText.getText().toString().trim();
        String time = timeEditText.getText().toString().trim();
        String note = noteEditText.getText().toString().trim();

        // Validate if task name is not empty
        if (!taskName.isEmpty()) {
            // Create a unique key for the task using push()
            String taskId = databaseReference.push().getKey();

            // Create a Task object
            Task task = new Task(taskId, taskName, date, time, note);

            // Save the task to Firebase
            databaseReference.child(taskId).setValue(task, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        // There was an error saving the data
                        Toast.makeText(AddTaskActivity.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        // Data saved successfully
                        Toast.makeText(AddTaskActivity.this, "Task saved successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Finish the activity
                    }
                }
            });
        } else {
            // Show an error message if the task name is empty
            taskNameEditText.setError("Task name is required");
        }
    }
}
