
package edu.cs477.fall2021.project2_abinaiek;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
    Button doWorkoutButton;
    Button setupEditModeButton;


    protected static DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this);
    }

    public void onDoWorkoutButtonClicked(View view){
        doWorkoutButton = findViewById(R.id.doWorkoutButton);
        doWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DoWorkoutActivity.class);
                startActivityForResult(intent,2);
            }
        });
    }
    public void onSetupEditModeClicked(View view){
        setupEditModeButton = findViewById(R.id.setupEditModeButton);
        setupEditModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivityForResult(intent,3);
            }
        });
    }
}