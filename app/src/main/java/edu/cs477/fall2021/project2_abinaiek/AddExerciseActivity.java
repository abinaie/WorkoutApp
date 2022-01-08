package edu.cs477.fall2021.project2_abinaiek;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddExerciseActivity extends AppCompatActivity {
    //private SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        //db = MainActivity.dbHelper.getWritableDatabase();

    }
    public void onAddToWorkoutClicked(View view){
        Button addToWorkoutButton = findViewById(R.id.add);
        addToWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //store to database
                EditText name = findViewById(R.id.Name);
                EditText reps = findViewById(R.id.reps);
                EditText sets = findViewById(R.id.sets);
                EditText weight = findViewById(R.id.weight);
                EditText notes = findViewById(R.id.notes);
                if(name.getText().toString().length()==0) {
                    Toast.makeText(getApplicationContext(), "We need at least the exercise name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                MainActivity.dbHelper.addData(name.getText().toString(), reps.getText().toString(),sets.getText().toString(),weight.getText().toString(),notes.getText().toString());
                finish();
            }
        });
    }
    public void onCancelClicked(View view){
        Button cancelButton = findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}