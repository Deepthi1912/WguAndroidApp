package com.example.carlosespejo.wguapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class TermsList extends AppCompatActivity {

    DatabaseManager dbMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_list);



        /**
         * list view
         */


        ListView listView = (ListView) findViewById(
                R.id.listView);
        dbMgr = new DatabaseManager(this, null, null, 2);

        Cursor cursor = dbMgr.getTermsCursor();
        startManagingCursor(cursor);

        ListAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursor,
                new String[] {DatabaseManager.TERM_TITLE_FIELD,
                        DatabaseManager.TERM_START_DATE_FIELD, DatabaseManager.TERM_END_DATE_FIELD},
                new int[] {android.R.id.text1, android.R.id.text2},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView,
                                            View view, int position, long id) {
                        Intent intent = new Intent(
                                getApplicationContext(),
                                TermDetail.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                });

    }// end of onCreate

    public void goToTermsScreen(View v){
        startActivity(new Intent(TermsList.this, AddTermScreen.class));
    }



}
