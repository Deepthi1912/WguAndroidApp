package com.example.carlosespejo.wguapp;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddCourseScreen extends Activity {

    long termId;
    DatabaseManager dbMgr;
    private TextView selectDate1;
    private TextView selectDate2;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private DatePickerDialog.OnDateSetListener onDateSetListener2;
    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_screen);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            termId = extras.getLong("id");
        }

        Log.d("dannyproblem", "termId: " + termId);

        //show date picker
        selectDate1 = (TextView) findViewById(R.id.courseDateTextView1);
        selectDate2 = (TextView) findViewById(R.id.courseDateTextView2);


        selectDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);



                DatePickerDialog dialog = new DatePickerDialog(
                        AddCourseScreen.this,
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
                        AddCourseScreen.this,
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


    }//end of on create

    public void cancel(View view) {
        finish();
    }

    public void addCourse(View view) throws ParseException {

        if(datesAreCompliant()) {
            DatabaseManager dbMgr = new DatabaseManager(this, null, null, 2);
            String title = ((TextView) findViewById(
                    R.id.courseTitleInput)).getText().toString();
            String startDate = ((TextView) findViewById(
                    R.id.courseDateTextView1)).getText().toString();
            String endDate = ((TextView) findViewById(
                    R.id.courseDateTextView2)).getText().toString();
            String status = getCourseStatusFromSpinner();
            String notes = ((TextView) findViewById(
                    R.id.courseNotesInput)).getText().toString();
            Course course = new Course(title, startDate, endDate, status, notes);
            course.setTermId(termId);
            dbMgr.addCourse(course);


            finish();
        }
    }

    private Boolean datesAreCompliant(){
        String startDate = ((TextView) findViewById(
                R.id.courseDateTextView1)).getText().toString();
        String endDate = ((TextView) findViewById(
                R.id.courseDateTextView2)).getText().toString();

        if(startDate.equals("Select Date") || endDate.equals("Select Date")){
            Toast.makeText(this, "Please select all dates before saving",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }

    private String getCourseStatusFromSpinner(){

        Log.d("dannyproblem", "Entering get course status method");

        Spinner type = (Spinner) findViewById(R.id.addCourseStatusDropdown);
        String status = type.getSelectedItem().toString();
        Log.d("dannyproblem", "whats the type being added? : " + status);


        if(status.equals("Not Started")){
            Log.d("dannyproblem", "Not Started");
            return "Not Started";

        }

        else if(status.equals("In Progress")){
            Log.d("dannyproblem", "In Progress");

            return "In Progress";

        }

        else{
            Log.d("dannyproblem" , "Passed");
            return "Passed";
        }


    }
}
