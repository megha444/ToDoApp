package com.meghaagarwal.todoapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;

public class AddTask extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;
    private EditText mETaddTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        mDatabaseReference= FirebaseDatabase.getInstance().getReference();
    }
    public void addButtonClicked (View view)
    {
        mETaddTask = (EditText) findViewById(R.id.etAddTask);

        String name = mETaddTask.getText().toString();
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM MMM dd, yyyy hh:mm a");
        String dateString = sdf.format(date);

        mDatabaseReference=FirebaseDatabase.getInstance().getReference().child("Tasks");
        DatabaseReference newTask = mDatabaseReference.push();
        newTask.child("name").setValue(name);
        newTask.child("time").setValue(dateString);

    }
}