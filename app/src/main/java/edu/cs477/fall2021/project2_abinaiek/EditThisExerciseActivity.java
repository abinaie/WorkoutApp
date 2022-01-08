package edu.cs477.fall2021.project2_abinaiek;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditThisExerciseActivity extends AppCompatActivity {
    long id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_this_exercise);
        TextView name = findViewById(R.id.Name);
        Intent intent = getIntent();
        //id = intent.getExtras().getInt("id");
        id = intent.getLongExtra("id", 0);
        String exercise_name = intent.getExtras().getString("Exercise Name");
        name.setText(exercise_name);

        EditText rep = findViewById(R.id.reps);
        rep.setHint(intent.getExtras().getString("Exercise Rep"));
        EditText set = findViewById(R.id.sets);
        set.setHint(intent.getExtras().getString("Exercise Set"));
        EditText weight = findViewById(R.id.weight);
        weight.setHint(intent.getExtras().getString("Exercise Weight"));
        EditText notes = findViewById(R.id.notes);
        notes.setHint(intent.getExtras().getString("Exercise Note"));


    }
    public void onEditThisWorkoutClicked(View view){
        Button addToWorkoutButton = findViewById(R.id.editExercise);
        addToWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //store to database
                EditText reps = findViewById(R.id.reps);
                EditText sets = findViewById(R.id.sets);
                EditText weight = findViewById(R.id.weight);
                EditText notes = findViewById(R.id.notes);

                String r = reps.getText().toString();
                if(r.length() == 0) r = reps.getHint().toString();

                String s = sets.getText().toString();
                if(s.length() == 0) s = sets.getHint().toString();

                String w = weight.getText().toString();
                if(w.length() == 0) w = weight.getHint().toString();

                String n = notes.getText().toString();
                if(n.length() == 0) n = notes.getHint().toString();

                MainActivity.dbHelper.editData(r, s, w, n, id);
                //if(!t) Toast.makeText(getApplicationContext(), "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }
    public void onCanceledClicked(View view){
        Button cancelButton = findViewById(R.id.canceled);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}