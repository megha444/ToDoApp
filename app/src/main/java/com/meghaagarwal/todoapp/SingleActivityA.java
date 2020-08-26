package com.meghaagarwal.todoapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SingleActivityA extends AppCompatActivity {

    private String task_key = null;
    private TextView msingleTask;
    private TextView msingleTime;
    private DatabaseReference mDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_activity);

        task_key = getIntent().getExtras().getString("Task ID");
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Tasks");
        msingleTask = (TextView) findViewById(R.id.singleTask);
        msingleTime = (TextView) findViewById(R.id.singleTime);

        mDatabaseReference.child(task_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String task_title = (String) snapshot.child("name").getValue();
                String task_time = (String) snapshot.child("time").getValue();

                msingleTask.setText(task_title);
                msingleTime.setText(task_time);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}