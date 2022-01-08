package edu.cs477.fall2021.project2_abinaiek;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    final static String[] columns = {DatabaseHelper._ID, DatabaseHelper.NAME, DatabaseHelper.SETS, DatabaseHelper.REPS, DatabaseHelper.WEIGHTS, DatabaseHelper.NOTES };//{ DatabaseOpenHelper._ID, DatabaseOpenHelper.ITEM };///////
    Cursor c;
    private SQLiteDatabase db = null;
    public ListView listView;
    public SimpleCursorAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exercise);
        //Toast.makeText(getApplicationContext(), "I am hereeeee", Toast.LENGTH_SHORT).show();

        listView = findViewById(R.id.list);
        db = MainActivity.dbHelper.getWritableDatabase();
        c = readTodo();
        myAdapter = new SimpleCursorAdapter(this, R.layout.line, c, new String[] { DatabaseHelper._ID, DatabaseHelper.NAME}, new int[] { R.id._id, R.id.Name },1);//new int[] { R.id._id, R.id.Name, R.id.Sets, R.id.Reps , R.id.Weight, R.id.Notes }
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor row = db.query(DatabaseHelper.TABLE_NAME, null, DatabaseHelper._ID + "=" + l, null, null, null, null);
                row.moveToFirst();

                //Toast.makeText(EditActivity.this, row.getString((row.getColumnIndex(DatabaseHelper.NAME))),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditActivity.this, EditThisExerciseActivity.class);
                intent.putExtra("Exercise Name", row.getString((row.getColumnIndex(DatabaseHelper.NAME))) );
                intent.putExtra("Exercise Rep", row.getString((row.getColumnIndex(DatabaseHelper.REPS))) );
                intent.putExtra("Exercise Set", row.getString((row.getColumnIndex(DatabaseHelper.SETS))) );
                intent.putExtra("Exercise Weight", row.getString((row.getColumnIndex(DatabaseHelper.WEIGHTS))) );
                intent.putExtra("Exercise Note", row.getString((row.getColumnIndex(DatabaseHelper.NOTES))) );
                //intent.putExtra("id", myAdapter.getItemId(i));
                intent.putExtra("id", l);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                alertView("Single Item Deletion", position);
                return true;
            }
        });
    }
    protected void onResume(){
        super.onResume();
        //Toast.makeText(getApplicationContext(), "I am here", Toast.LENGTH_SHORT).show();

        c = readTodo();
        myAdapter = new SimpleCursorAdapter(this, R.layout.line, c, new String[] { DatabaseHelper._ID, DatabaseHelper.NAME}, new int[] { R.id._id, R.id.Name },1);//new int[] { R.id._id, R.id.Name, R.id.Sets, R.id.Reps , R.id.Weight, R.id.Notes }
        listView.setAdapter(myAdapter);

    }
    public void onPlusClicked(View view){
        Button plusButton = findViewById(R.id.addExercise);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditActivity.this, AddExerciseActivity.class);
                //startActivityForResult(intent,4);
                startActivity(intent);
            }
        });
    }


    private Cursor readTodo() {
        return db.query(DatabaseHelper.TABLE_NAME, columns, null, new String[] {}, null, null, null);
    }
    private void alertView(String message, final int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);//MainActivity.this
        dialog.setTitle(message)
                .setIcon(R.drawable.ic_launcher_background)
                .setMessage("Are you sure you want to do this?")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.cancel();
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        db.delete(DatabaseHelper.TABLE_NAME,  DatabaseHelper._ID+ "=?", new String[]{myAdapter.getItemId(position)+""});//new String[]{myAdapter.getItem(position).toString()}
                        c = new GetNotes().doInBackground();
                        myAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.line, c, columns, new int[] { R.id._id, R.id.Name },1);///\\\
                        listView.setAdapter(myAdapter);
                    }
                }).show();
    }

    private final class GetNotes extends AsyncTask<String, Void, Cursor> {
        // runs on the UI thread
        @Override protected void onPostExecute(Cursor data) {
            //SimpleCursorAdapter(Context context, int layout, Cursor c, String[] columnNameFromDB, int[] to, int flags)
            myAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.line, c, columns, new int[] { R.id._id, R.id.Name },1);
            c = data;
            listView.setAdapter(myAdapter);
        }
        // runs on its own thread
        @Override
        protected Cursor doInBackground(String... args) {
            db = MainActivity.dbHelper.getReadableDatabase();
            return db.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        }
    }
}