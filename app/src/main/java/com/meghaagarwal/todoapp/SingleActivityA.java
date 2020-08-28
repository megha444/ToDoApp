package com.meghaagarwal.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private TextView msingleNotes;
    private TextView msingleStatus;
    private Button mTaskComplete;
    private Button mBack;

    private DatabaseReference mDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_activity);

        task_key = getIntent().getExtras().getString("Task ID");
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Tasks");
        msingleTask = (TextView) findViewById(R.id.singleTask);
        msingleTime = (TextView) findViewById(R.id.singleTime);
        msingleNotes = (TextView) findViewById(R.id.singleNotes);
        msingleStatus = (TextView) findViewById(R.id.singleStatus);

        mTaskComplete = (Button) findViewById(R.id.bTaskComplete);
        mBack = (Button) findViewById(R.id.backButton);
        mTaskComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseReference.child(task_key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("status").setValue("Done");
                        Intent intent = new Intent(SingleActivityA.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseReference.child(task_key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().removeValue();
                        Intent intent = new Intent(SingleActivityA.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        mDatabaseReference.child(task_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String task_title = (String) snapshot.child("name").getValue();
                String task_time = (String) snapshot.child("time").getValue();
                String task_notes = (String) snapshot.child("notes").getValue();
                String task_status= (String) snapshot.child("status").getValue();

                msingleTask.setText(task_title);
                msingleTime.setText(task_time);
                msingleNotes.setText(task_notes);
                msingleStatus.setText(task_status);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    /*private void taskComplete(final View view)
    {
        task_key= getIntent().getExtras().getString("Task ID");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Tasks");
        mDatabaseReference.child(task_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getRef().child("status").setValue("Done");
                Log.d("TODO", "Edited");
                Intent intent = new Intent(SingleActivityA.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void backButton(View view)
    {
        Intent intent = new Intent(SingleActivityA.this, MainActivity.class);
    }*/
}