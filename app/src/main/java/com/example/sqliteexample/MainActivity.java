package com.example.sqliteexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private ListView eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, CreateEventActivity.class);
        FloatingActionButton fab = findViewById(R.id.add_event_btn);
        fab.setOnClickListener((view) ->startActivity(intent));
        eventList = findViewById(R.id.event_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper db = new DBHelper(this, DBHelper.DATABASE_NAME, null, 1);
        SQLiteDatabase reader = db.getReadableDatabase();
        String[] columns = {"_id", DBHelper.TITLE_COL, DBHelper.DATE_COL, DBHelper.IMAGE_ID_COL};
        Cursor cursor = reader.query(DBHelper.TABLE_NAME, columns, null,null,null,null,null);
        EventCursorAdapater cursorAdapter = new EventCursorAdapater(this,cursor, true);
        eventList.setAdapter(cursorAdapter);

        if (cursor.moveToFirst()) {
            do {
                Log.i("INFO", String.format("%s|%s| %s",
                        cursor.getString(cursor.getColumnIndex(DBHelper.TITLE_COL)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.DATE_COL)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.IMAGE_ID_COL))));
            }while (cursor.moveToNext());
        }
        //cursor.close();


    }
}