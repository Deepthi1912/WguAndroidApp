package com.example.carlosespejo.wguapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddAssessmentScreen extends AppCompatActivity {

    long courseId;
    DatabaseManager dbMgr;
    private TextView selectDate1;
    private TextView selectDate2;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private DatePickerDialog.OnDateSetListener onDateSetListener2;
    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    SharedPreferences asmPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment_screen);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            courseId = extras.getLong("id");
        }



        //show date picker
        selectDate1 = (TextView) findViewById(R.id.addAssessDueDateInput);
        selectDate2 = (TextView) findViewById(R.id.addAssessGoalDateInput);



        selectDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);



                DatePickerDialog dialog = new DatePickerDialog(
                        AddAssessmentScreen.this,
                        android.R.style.Theme_Light,
                        onDateSetListener,
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
        }; //end of date picker 1

        selectDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);



                DatePickerDialog dialog = new DatePickerDialog(
                        AddAssessmentScreen.this,
                        android.R.style.Theme_Light,
                        onDateSetListener2,
                        year, month, day);

                //background transparant

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();

            }
        });



        onDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;


                String date = month+ "/" + day + "/" + year;


                //set Textviews to selected date
                selectDate2.setText(date);


            }
        }; // end of onCreate


    }

    public void cancel(View view) {
        finish();
    }

    public void addAssessment(View view) throws ParseException {

        if(datesAreCompliant()) {
            DatabaseManager dbMgr = new DatabaseManager(this, null, null, 2);
            String title = ((TextView) findViewById(
                    R.id.addAssessTitleInput)).getText().toString();
            String dueDate = ((TextView) findViewById(
                    R.id.addAssessDueDateInput)).getText().toString();
            String goalDate = ((TextView) findViewById(
                    R.id.addAssessGoalDateInput)).getText().toString();

            int type = getAssessmentType();


            Assessment assessment = new Assessment(title, dueDate, goalDate, type);
            assessment.setCourseId(courseId);


            dbMgr.addAssessment(assessment);


            finish();
        }
    }

    private Boolean datesAreCompliant(){
        String startDate = ((TextView) findViewById(
                R.id.addAssessDueDateInput)).getText().toString();
        String endDate = ((TextView) findViewById(
                R.id.addAssessGoalDateInput)).getText().toString();

        if(startDate.equals("Select Date") || endDate.equals("Select Date")){
            Toast.makeText(this, "Please select all dates before saving",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }



    private int getAssessmentType(){

        Spinner type = (Spinner) findViewById(R.id.addAssessmentTypeDropdown);
        String dueDate = type.getSelectedItem().toString();
        Log.d("dannyproblem", "whats the type being added? : " + dueDate);


        if(dueDate.equals("Objective")){
            Log.d("dannyproblem", "1 is being returned");
            return 1;

        }

        else{
            Log.d("dannyproblem", "0 is being returned");
            return 0;
        }


    }
}
