package com.meghaagarwal.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    private RecyclerView mTaskList;
    FirebaseRecyclerAdapter mAdapter;
    TextView mBannerDay;
    TextView mBannerDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTaskList = (RecyclerView) findViewById(R.id.TaskList);
        mTaskList.setHasFixedSize(true);
        mTaskList.setLayoutManager(new LinearLayoutManager(this));

        mBannerDay= (TextView) findViewById(R.id.bannerDay);
        mBannerDate= (TextView) findViewById(R.id.bannerDate);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        mBannerDay.setText(dayOfTheWeek);

        long date = System.currentTimeMillis();
        SimpleDateFormat sdff = new SimpleDateFormat("dd MMMM yyyy");
        //SimpleDateFormat sdff = new SimpleDateFormat("MMM MMM dd yyyy, hh::mm a");
        String dateString = sdff.format(date);
        mBannerDate.setText(dateString);


        fetch();

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        public TaskViewHolder (View itemview)
        {
            super (itemview);
            mView = itemview;
        }

        public void setName(String name)
        {
            TextView task_name = (TextView) mView.findViewById(R.id.taskName);
            task_name.setText(name);
        }

        public void setTime(String time)
        {
            TextView task_time = (TextView) mView.findViewById(R.id.taskTime);
            task_time.setText(time);
        }

        public void setStatus(String status)
        {
            TextView task_status = (TextView) mView.findViewById(R.id.taskStatus);
            task_status.setText(status);
        }
    }


    private void fetch()
    {
        Query query = FirebaseDatabase.getInstance().getReference().child("Tasks");

        FirebaseRecyclerOptions<Task> options = new FirebaseRecyclerOptions.Builder<Task>()
                .setQuery(query, new SnapshotParser<Task>() {
                    @NonNull
                    @Override
                    public Task parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new Task(snapshot.child("name").getValue().toString(),
                                snapshot.child("time").getValue().toString(),
                                snapshot.child("status").getValue().toString());
                    }
                }) .build();

        mAdapter = new FirebaseRecyclerAdapter <Task, TaskViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i, @NonNull Task task) {
                taskViewHolder.setName(task.getName());
                taskViewHolder.setTime(task.getTime());
                taskViewHolder.setStatus(task.getStatus());

                final String task_key = getRef(i).getKey().toString();
                taskViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent singleTaskActivity = new Intent(MainActivity.this, SingleActivityA.class);
                        singleTaskActivity.putExtra("Task ID", task_key);
                        startActivity(singleTaskActivity);
                    }
                });

            }
/*

            @Override
            protected void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i, @NonNull Tasknew task) {
                taskViewHolder.setName(task.getName());
                taskViewHolder.setTime(task.getTime());
                taskViewHolder.setStatus(task.getStatus());

                final String task_key = getRef(i).getKey().toString();
                taskViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent singleTaskActivity = new Intent(MainActivity.this, SingleActivityA.class);
                        singleTaskActivity.putExtra("Task ID", task_key);
                        startActivity(singleTaskActivity);
                    }
                });

            }
*/


            @NonNull
            @Override
            public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.task_row, parent, false);
                return new TaskViewHolder(view);
            }
        };

        mTaskList.setAdapter(mAdapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.addTask)
        {
            Intent intent = new Intent(MainActivity.this, AddTask.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}