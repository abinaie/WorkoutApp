package edu.cs477.fall2021.project2_abinaiek;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "MyDatabase";
    public static final String TABLE_NAME = "Workout";
    private static final int DB_VERSION = 1;
    final private Context context;

    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String REPS = "reps";
    public static final String SETS = "sets";
    public static final String WEIGHTS = "weight";
    public static final String NOTES = "notes";

    private static final String CREATE_CMD = "CREATE TABLE " + TABLE_NAME + "(" +_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME +" TEXT NOT NULL,"  + REPS +" TEXT NOT NULL,"  + SETS +" TEXT NOT NULL,"  + WEIGHTS +" TEXT NOT NULL,"  + NOTES +" TEXT NOT NULL)";
    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CMD);
        ContentValues values = new ContentValues();

        values.put(NAME, "Sit ups");
        values.put(REPS, "20");
        values.put(SETS, "3");
        values.put(WEIGHTS, "N/A");
        values.put(NOTES, "Lifting is Life!");
        db.insert(TABLE_NAME,null, values);
        values.clear();
        values.put(NAME, "Push ups");
        values.put(REPS, "10");
        values.put(SETS, "3");
        values.put(WEIGHTS, "N/A");
        values.put(NOTES, "Just Lift!");
        db.insert(TABLE_NAME,null, values);
        values.clear();
        values.put(NAME, "Running");
        values.put(REPS, "N/A");
        values.put(SETS, "N/A");
        values.put(WEIGHTS, "N/A");
        values.put(NOTES, "3 Miles");
        db.insert(TABLE_NAME,null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public void addData(String name, String reps, String sets, String weight, String notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (name.length() == 0) name = "N/A";
        if (reps.length() == 0) reps = "N/A";
        if (sets.length() == 0) sets = "N/A";
        if (weight.length() == 0) weight = "N/A";
        if (notes.length() == 0) notes = "N/A";

        values.put(NAME, name);
        values.put(REPS, reps);
        values.put(SETS, sets);
        values.put(WEIGHTS, weight);
        values.put(NOTES, notes);



        //see if there is identical exercise name so we could update the old one
        Cursor cur = null;
        cur = db.query(TABLE_NAME, null, null, null, null, null, null, null);
        if (cur != null) {
            //Log.d("length", "cur size: " + cur.getCount());
            cur.moveToFirst();
            //Log.d("TAG", cur.getString((cur.getColumnIndex(DatabaseOpenHelper.ITEM))).split(" ")[0]);
            while (!cur.isAfterLast()) {
                //Log.d("while", "clearEdit: " + cur.getString((cur.getColumnIndex(DatabaseOpenHelper.ITEM))).split(" ")[0]);
                if (cur.getString((cur.getColumnIndex(NAME))).toLowerCase().contains(name.toLowerCase())) {
                    //Log.d("Duplicate>>>>", "at index "+ cur.getInt(0));
                    editData(reps, sets, weight, notes, cur.getInt(0));
                    return;
                }
                cur.moveToNext();
            }
        }

        db.insert(TABLE_NAME, null, values);
    }
    public void editData(String reps, String sets, String weight, String notes, long id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(REPS, reps);
        values.put(SETS, sets);
        values.put(WEIGHTS, weight);
        values.put(NOTES, notes);
        db.update(TABLE_NAME, values, _ID + "=?", new String[]{id+""});

    }
    public String getName(){
        return NAME;
    }
}
