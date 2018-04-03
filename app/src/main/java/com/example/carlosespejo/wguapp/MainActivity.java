package com.example.carlosespejo.wguapp;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements
        View.OnTouchListener {

    DatabaseManager dbMgr;
    private String inProgressCount;
    private String notStartedCount;
    private String passedCount;
    SharedPreferences assessmentGoalDatesAlerts;
    SharedPreferences assessmentDueDateAlerts;
    SharedPreferences courseStartDatesAlerts;
    SharedPreferences courseEndDatesAlerts;
    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");


    private static final String TAG = "dannysMessage";

    //Assessment test1 = new Assessment();

    TextView testText;
    EditText testEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //database initializer
        dbMgr = new DatabaseManager(this, null, null, 2);

        //check for alerts

        courseStartDatesAlerts = getSharedPreferences("courseStartDatesAlerts", Context.MODE_PRIVATE);
        courseEndDatesAlerts = getSharedPreferences("courseEndDatesAlerts", Context.MODE_PRIVATE);
        assessmentDueDateAlerts = getSharedPreferences("assessmentDueDateAlerts", Context.MODE_PRIVATE);
        assessmentGoalDatesAlerts = getSharedPreferences("assessmentGoalDateAlerts", Context.MODE_PRIVATE);

        //display alerts
        try {
            displayallAlerts();
        } catch (ParseException e) {
            e.printStackTrace();
       }


    }







    /**
     * Change all screens methods
     * @param v
     */

    public void goToTerms(View v){
        startActivity(new Intent(MainActivity.this, TermsList.class));
    }
    public void goToCourses(View v){
        startActivity(new Intent(MainActivity.this, CoursesList.class));
    }

    public void goToAssessments(View v){
        startActivity(new Intent(MainActivity.this, AssessmentsList.class));
    }


    //example
    public boolean onTouch(View arg0, MotionEvent event) {

        View id = findViewById(R.id.goToCoursesButton);


        Intent intent = new Intent(this, TermsList.class);
        intent.putExtra("message", id.getId());
        startActivity(intent);
        return true;
    }


    //example for messages
    public boolean onTouch2(View arg0, MotionEvent event) {

        int id = arg0.getId();
        Intent intent = new Intent(this, TermsList.class);
        intent.putExtra("message", "Message from First Screen");
        startActivity(intent);
        return true;
    }

        //example for switching
    public void changeView(View view){
        view.getId();
        Intent intent = new Intent(this, TermsList.class);
        startActivity(intent);

    }



    public void displayallAlerts() throws ParseException {
        //get current date
        Calendar cal = Calendar.getInstance();
        String currentDate = df.format(cal.getTime());
        Date currentDateAsDate = df.parse(currentDate);
        Date dateOfSelection;
        dbMgr = new DatabaseManager(this, null, null, 2);
        Course currentCourse = new Course();
        Assessment currentAssessment = new Assessment();
        Map<String, ?> alerts;

        courseStartDatesAlerts = getSharedPreferences("courseStartDatesAlerts", Context.MODE_PRIVATE);
        courseEndDatesAlerts = getSharedPreferences("courseEndDatesAlerts", Context.MODE_PRIVATE);
        assessmentDueDateAlerts = getSharedPreferences("assessmentDueDateAlerts", Context.MODE_PRIVATE);
        assessmentGoalDatesAlerts = getSharedPreferences("assessmentGoalDateAlerts", Context.MODE_PRIVATE);







        //Course due Start Dates

        if (!courseStartDatesAlerts.getAll().isEmpty()) {
            //do something

            alerts = courseStartDatesAlerts.getAll();
            for (Map.Entry<String, ?> entry : alerts.entrySet()) {
                Log.d("dannyalert", entry.getKey() + ": " +
                        entry.getValue().toString());

                dateOfSelection = df.parse(entry.getValue().toString());

                if (currentDateAsDate.equals(dateOfSelection)) {

                    currentCourse = dbMgr.getCourse(Long.parseLong(entry.getKey()));

                    Toast.makeText(this, "Course: " + currentCourse.getTitle()+ " starts today",
                            Toast.LENGTH_LONG).show();

                }

            }
        }//end of if course start date alert

        //Course end date

        if (!courseEndDatesAlerts.getAll().isEmpty()) {
            //do something


            alerts = courseEndDatesAlerts.getAll();
            for (Map.Entry<String, ?> entry : alerts.entrySet()) {
                Log.d("dannyalert", entry.getKey() + ": " +
                        entry.getValue().toString());
                dateOfSelection = df.parse(entry.getValue().toString());

                if (currentDateAsDate.equals(dateOfSelection)) {
                    currentCourse = dbMgr.getCourse(Long.parseLong(entry.getKey()));
                    Toast.makeText(this, "Course: " + currentCourse.getTitle()+ " ends today",
                            Toast.LENGTH_LONG).show();

                }

            }
        }//end of if duedates

        //Assessment due date

        if (!assessmentDueDateAlerts.getAll().isEmpty()) {
            //do something


            alerts= assessmentDueDateAlerts.getAll();
            for (Map.Entry<String, ?> entry : alerts.entrySet()) {
                Log.d("dannyalert", entry.getKey() + ": " +
                        entry.getValue().toString());
                dateOfSelection = df.parse(entry.getValue().toString());
                if (currentDateAsDate.equals(dateOfSelection)) {
                    currentAssessment = dbMgr.getAssessment(Long.parseLong(entry.getKey()));
                    Toast.makeText(this, "Assignment: " + currentAssessment.getTitle()+ " is due today",
                            Toast.LENGTH_LONG).show();

                }

            }
        }//end of if duedates

        //Assessment due date

        if (!assessmentGoalDatesAlerts.getAll().isEmpty()) {
            //do something


            alerts = assessmentGoalDatesAlerts.getAll();
            for (Map.Entry<String, ?> entry : alerts.entrySet()) {
                Log.d("dannyalert", entry.getKey() + ": " +
                        entry.getValue().toString());
                dateOfSelection = df.parse(entry.getValue().toString());
                if (currentDateAsDate.equals(dateOfSelection)) {
                    currentAssessment = dbMgr.getAssessment(Long.parseLong(entry.getKey()));
                    Toast.makeText(this, "Goal date for Assignment: " + currentAssessment.getTitle()+ " is today",
                            Toast.LENGTH_LONG).show();

                }

            }
        }//end of if duedates
    }//end of method





}
