package com.example.carlosespejo.wguapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CoursesList extends AppCompatActivity {

    DatabaseManager dbMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_list);


        ListView listView = (ListView) findViewById(
                R.id.listView);
        dbMgr = new DatabaseManager(this, null, null, 2);

        Cursor cursor = dbMgr.getCoursesCursor();
        startManagingCursor(cursor);

        ListAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursor,
                new String[] {DatabaseManager.COURSE_TITLE_FIELD,
                        DatabaseManager.COURSE_START_DATE_FIELD, DatabaseManager.COURSE_END_DATE_FIELD},
                new int[] {android.R.id.text1, android.R.id.text2},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


    }//end of onCreate


}
