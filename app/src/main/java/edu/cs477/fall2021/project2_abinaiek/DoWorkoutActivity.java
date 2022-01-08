package edu.cs477.fall2021.project2_abinaiek;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DoWorkoutActivity extends AppCompatActivity {
    final static String[] columns = {DatabaseHelper._ID, DatabaseHelper.NAME, DatabaseHelper.SETS, DatabaseHelper.REPS, DatabaseHelper.WEIGHTS, DatabaseHelper.NOTES };//{ DatabaseOpenHelper._ID, DatabaseOpenHelper.ITEM };///////
    private SQLiteDatabase db = null;
    //protected static DatabaseHelper dbHelper;
    Cursor c;
    public ListView listView;
    public SimpleCursorAdapter myAdapter;
    List<Long> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doworkout);
        listView = findViewById(R.id.list);
        //dbHelper = new DatabaseHelper(this);
        db = MainActivity.dbHelper.getWritableDatabase();

        list = new ArrayList();
        //db = dbHelper.getWritableDatabase();

        c = readTodo();
        myAdapter = new SimpleCursorAdapter(this, R.layout.line, c, new String[] { DatabaseHelper._ID, DatabaseHelper.NAME}, new int[] { R.id._id, R.id.Name },1);//new int[] { R.id._id, R.id.Name, R.id.Sets, R.id.Reps , R.id.Weight, R.id.Notes }
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor row = db.query(DatabaseHelper.TABLE_NAME, null, DatabaseHelper._ID+ "=" + l, null, null, null, null);
                row.moveToFirst();
                @SuppressLint("Range") String info = "REPS: "+ row.getString(row.getColumnIndex(DatabaseHelper.REPS)) + "\nSETS: "+ row.getString(row.getColumnIndex(DatabaseHelper.SETS)) + "\nWEIGHT: "+ row.getString(row.getColumnIndex(DatabaseHelper.WEIGHTS)) + "\nNOTES: "+ row.getString(row.getColumnIndex(DatabaseHelper.NOTES)); ///<<<<<<<<<
                Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG).show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //c = db.query(DatabaseHelper.TABLE_NAME,null, " not "+DatabaseHelper._ID+ "=" + id,null, null, null, null);


                list.add(id);
                String commaSeparatedIds = toCommaSeparatedString(list);
                String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper._ID + " NOT IN (" + commaSeparatedIds + ")" ;
                c = db.rawQuery( selectQuery, null);


                myAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.line, c, columns, new int[] { R.id._id, R.id.Name },1);///\\\
                listView.setAdapter(myAdapter);
                return true;
            }
        });



    }
    private Cursor readTodo() {
        return db.query(DatabaseHelper.TABLE_NAME, columns, null, new String[] {}, null, null, null);
    }

    private String toCommaSeparatedString(List<Long> list) {
        if (list.size() > 0) {
            StringBuilder nameBuilder = new StringBuilder();
            for (Long item : list) {
                nameBuilder.append(item).append(", ");
            }
            nameBuilder.deleteCharAt(nameBuilder.length() - 1);
            nameBuilder.deleteCharAt(nameBuilder.length() - 1);
            return nameBuilder.toString();
        } else {
            return "";
        }
    }

}