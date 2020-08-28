package com.meghaagarwal.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;

public class AddTask extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;
    private EditText mETaddTask;
    private EditText mNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        mDatabaseReference= FirebaseDatabase.getInstance().getReference();
    }
    public void addButtonClicked (View view)
    {
        mETaddTask = (EditText) findViewById(R.id.etAddTask);
        mNotes = (EditText) findViewById(R.id.notes);

        String name = mETaddTask.getText().toString();
        String notes = mNotes.getText().toString();
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        //SimpleDateFormat sdf = new SimpleDateFormat("MMM MMM dd, yyyy hh:mm a");
        String dateString = sdf.format(date);

        mDatabaseReference=FirebaseDatabase.getInstance().getReference().child("Tasks");
        DatabaseReference newTask = mDatabaseReference.push();
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(dateString))
        {
            newTask.child("name").setValue(name);
            newTask.child("time").setValue(dateString);
            newTask.child("notes").setValue(notes);
            newTask.child("status").setValue("To do");

            Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show();
            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            Intent intent= new Intent (AddTask.this, MainActivity.class);
            startActivity(intent);

        }
        else
        {
            Toast.makeText(AddTask.this, "Enter Taskname", Toast.LENGTH_LONG).show();
        }
    }
}