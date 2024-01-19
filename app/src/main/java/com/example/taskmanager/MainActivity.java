package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button addTaskButton;
    private ListView taskListView;
    private List<Task> taskList;
    private ArrayAdapter<String> taskAdapter;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        // Initialize views
        addTaskButton = findViewById(R.id.addTaskButton);
        taskListView = findViewById(R.id.listViewTask);

        // Initialize task list and adapter
        taskList = new ArrayList<>();
        taskAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        taskListView.setAdapter(taskAdapter);

        // Set click listener for the "Add Task" button
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the Add Task activity
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });

        // Initialize Firebase Database with the specific URL
        String databaseUrl = "https://taskmanager-521f8-default-rtdb.asia-southeast1.firebasedatabase.app/";
        FirebaseDatabase database = FirebaseDatabase.getInstance(databaseUrl);
        databaseReference = database.getReference("tasks");

        // Set up ValueEventListener to listen for changes in the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Clear the existing list
                taskList.clear();
                taskAdapter.clear();

                // Iterate through each task in the dataSnapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Task task = snapshot.getValue(Task.class);
                    if (task != null) {
                        // Add the task to the list and adapter
                        taskList.add(task);
                        taskAdapter.add(task.getTaskName());
                    }
                }

                // Notify the adapter that the data has changed
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors here
            }
        });

        // Set item click listener for the ListView
        taskListView.setOnItemClickListener((adapterView, view, position, id) -> {
            // Open TaskDetailsActivity and pass the task details
            Task selectedTask = taskList.get(position);
            Intent intent = new Intent(MainActivity.this, TaskDetailsActivity.class);
            intent.putExtra("taskDetails", selectedTask);
            startActivity(intent);
        });
    }
}