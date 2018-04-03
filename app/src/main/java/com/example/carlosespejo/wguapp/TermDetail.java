package com.example.carlosespejo.wguapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;

public class TermDetail extends AppCompatActivity {

    long termId;
    DatabaseManager dbMgr;
    private TextView selectDate1;
    private TextView selectDate2;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private DatePickerDialog.OnDateSetListener onDateSetListener2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_term_detail);

        /**
         * get detail info from DB
         */
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            termId =  extras.getLong("id");
            Log.d("dannyProblem", "long: " + Long.toString(termId));
            DatabaseManager dbMgr = new DatabaseManager(this, null, null, 2);
            Term term = new Term();
            try {

                term = dbMgr.getTerm(termId);


            } catch (ParseException e) {


                e.printStackTrace();
            }
            if (term != null) {

                Log.d("dannyProblem", "hi");
                ((TextView) findViewById(R.id.termDTitleInput))
                        .setText(term.getTitle());
                ((TextView) findViewById(R.id.termDStartDateInput))
                        .setText(term.getStartDateFormatted());
                ((TextView) findViewById(R.id.termDEndDateInput))
                        .setText(term.getEndDateFormatted());
            } else {
                Log.d("db", "contact null");
            }
        }


        ListView listView = (ListView) findViewById(
                R.id.listViewTermDetail);
        dbMgr = new DatabaseManager(this, null, null, 2);

        Cursor cursor = dbMgr.getCoursesCursorForTerm(termId);
        //Cursor cursor = dbMgr.getCoursesCursor();
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
        listView.setOnItemClickListener(
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView,
                                            View view, int position, long id) {
                        Intent intent = new Intent(
                                getApplicationContext(),
                                CourseDetail.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                });

        //show date pickers
        //show date picker
        selectDate1 = (TextView) findViewById(R.id.termDStartDateInput);
        selectDate2 = (TextView) findViewById(R.id.termDEndDateInput);


        selectDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);



                DatePickerDialog dialog = new DatePickerDialog(
                        TermDetail.this,
                        android.R.style.Theme_Light,
                        onDateSetListener,
                        year, month, day);

                //background transparant

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();

            }
        });

        selectDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);



                DatePickerDialog dialog = new DatePickerDialog(
                        TermDetail.this,
                        android.R.style.Theme_Light,
                        onDateSetListener2,
                        year, month, day);

                //background transparant

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();

            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;


                String date = month+ "/" + day + "/" + year;


                //set Textviews to selected date
                selectDate1.setText(date);


            }
        }; // end of onCreate

        onDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;


                String date = month+ "/" + day + "/" + year;

                //set Textviews to selected date
                selectDate2.setText(date);


            }
        };


    }//onCreate

    public void goToAddCourseScreen(View v){
        Intent intent = new Intent(TermDetail.this, AddCourseScreen.class);
        intent.putExtra("id", termId);
        startActivity(intent);
    }

    public void deleteTerm(View view) throws ParseException {

        Log.d("dannyProblem", Integer.toString(dbMgr.getCourseCountForTerm(termId)));



        if(dbMgr.getCourseCountForTerm(termId) > 0){

            new AlertDialog.Builder(this)
                    .setTitle("Changes Needed")
                    .setMessage(
                            "Please delete all courses in this Term before deleting Term").create().show();

        }
        else {

            new AlertDialog.Builder(this)
                    .setTitle("Please confirm")
                    .setMessage(
                            "Are you sure you want to delete " +
                                    "this term?")
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog,
                                        int whichButton) {
                                    DatabaseManager dbMgr =
                                            new DatabaseManager(
                                                    getApplicationContext(), null, null, 2);
                                    dbMgr.deleteTerm(termId);

                                    dialog.dismiss();
                                    finish();
                                }
                            })
                    .setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog,
                                        int which) {
                                    dialog.dismiss();
                                }
                            })
                    .create()
                    .show();
        }//end of if else
    }

    public void updateMentor(View view) throws ParseException {

        if(datesAreCompliant()) {
            DatabaseManager dbMgr = new DatabaseManager(this, null, null, 2);
            String title = ((TextView) findViewById(
                    R.id.termDTitleInput)).getText().toString();
            String startDate = ((TextView) findViewById(
                    R.id.termDStartDateInput)).getText().toString();
            String endDate = ((TextView) findViewById(
                    R.id.termDEndDateInput)).getText().toString();


            Term term = new Term(title, startDate, endDate);
            term.setId(termId);
            dbMgr.updateTerm(term);


            finish();
        }
    }

    private Boolean datesAreCompliant(){
        String startDate = ((TextView) findViewById(
                R.id.termDStartDateInput)).getText().toString();
        String endDate = ((TextView) findViewById(
                R.id.termDEndDateInput)).getText().toString();

        if(startDate.equals("Select Date") || endDate.equals("Select Date")){
            Toast.makeText(this, "Please select all dates before saving",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }
}
