package com.example.todoapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    ArrayAdapter<String> mAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        dbHelper = new DBHelper(this);
        loadTask();
    }

    //Add icon to menu bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    //Load All Task in ListView

    private void loadTask(){
    ArrayList<String> taskList = dbHelper.getTaskList();

    if (mAdapter == null) {
        mAdapter = new ArrayAdapter<>(this, R.layout.customizerow, R.id.task_Title, taskList);
        listView.setAdapter(mAdapter);
    } else {
        mAdapter.clear();
        mAdapter.addAll(taskList);
        mAdapter.notifyDataSetChanged();

    }

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.AddTask) {
            final EditText editText = new EditText(this);
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("Add Task")
                    .setMessage("Whats your task?")
                    .setView(editText)
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String task = String.valueOf(editText.getText());
                            dbHelper.insertTask(task);
                            loadTask();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            alertDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
}
    //To delete the task
    public void deleteTask(View view){
       try {
           int index = listView.getPositionForView(view);
           String task = mAdapter.getItem(index++);

           dbHelper.deleteTask(task);
           loadTask();
       }catch (Exception e){

           Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();


       }
    }

}
