package com.example.carlosespejo.wguapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

public class CourseDetail extends AppCompatActivity {

    long courseId;
    DatabaseManager dbMgr;
    Course course;
    private TextView selectDate1;
    private TextView selectDate2;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private DatePickerDialog.OnDateSetListener onDateSetListener2;
    SharedPreferences courseStartDatesAlerts;
    SharedPreferences courseEndDatesAlerts;
    SharedPreferences assessmentDueDateAlerts;
    SharedPreferences assessmentGoalDatesAlerts;
    Button courseDStartDateButton;
    Button courseDEndDateButton;
    TextView courseDStartDateText;
    TextView courseDEndDateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        //set Alert Buttons
        //set buttons
        courseDStartDateButton = (Button) findViewById(R.id.courseDStartDateAlertBtn);
        courseDEndDateButton = (Button) findViewById(R.id.courseDEndDateAlertBtn);
        courseDStartDateText = (TextView) findViewById(R.id.courseDStartDateInput);
        courseDEndDateText = (TextView) findViewById(R.id.courseDEndDateInput);

        /**
         * get detail info from DB
         */
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            courseId =  extras.getLong("id");

            DatabaseManager dbMgr = new DatabaseManager(this, null, null, 2);
            course = new Course();
            try {

                course = dbMgr.getCourse(courseId);



            } catch (ParseException e) {


                e.printStackTrace();
            }
            if (course != null) {


                ((TextView) findViewById(R.id.courseDTitleInput))
                        .setText(course.getTitle());
                ((TextView) findViewById(R.id.courseDStartDateInput))
                        .setText(course.getStartDateFormatted());
                ((TextView) findViewById(R.id.courseDEndDateInput))
                        .setText(course.getEndDateFormatted());
                setCourseStatusDropDownValue();
                ((TextView) findViewById(R.id.courseDNotesInput))
                        .setText(course.getNotes());
            } else {
                Log.d("db", "contact null");
            }
        }

        //set Button text fields
        setAlertButtonText();

        //show date picker
        selectDate1 = (TextView) findViewById(R.id.courseDStartDateInput);
        selectDate2 = (TextView) findViewById(R.id.courseDEndDateInput);


        selectDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);



                DatePickerDialog dialog = new DatePickerDialog(
                        CourseDetail.this,
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
                        CourseDetail.this,
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



        /**
         * list for mentors
         */
        ListView listView = (ListView) findViewById(
                R.id.courseDMentorsListView);
        Log.d("dannyProblem", "listView  "+ listView.toString());
        dbMgr = new DatabaseManager(this, null, null, 1);

        Cursor cursor = dbMgr.getMentorCursorForCourse(courseId);


        startManagingCursor(cursor);
        Log.d("dannyProblem", "mentor cursor "+ cursor.getCount());


        ListAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursor,
                new String[] {DatabaseManager.MENTOR_NAME_FIELD,
                        DatabaseManager.MENTOR_EMAIL_FIELD, DatabaseManager.MENTOR_PHONE_FIELD},
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
                                MentorDetailScreen.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                });

        /**
         * list for mentors
         */
        ListView listView2 = (ListView) findViewById(
                R.id.courseDAssessmentsListView);


        Cursor cursor2 = dbMgr.getAssessmentCursorForCourse(courseId);
        Log.d("dannyProblem", "assess cursor "+ cursor2.getCount());


        startManagingCursor(cursor2);




        ListAdapter adapter2 = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursor2,
                new String[] {DatabaseManager.ASSESSMENT_TITLE_FIELD,
                        DatabaseManager.ASSESSMENT_DUE_DATE_FIELD, DatabaseManager.ASSESSMENT_IS_OBJECTIVE_FIELD},
                new int[] {android.R.id.text1, android.R.id.text2},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        listView2.setAdapter(adapter2);
        listView2.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView2.setOnItemClickListener(
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView,
                                            View view, int position, long id) {
                        Intent intent2 = new Intent(
                                getApplicationContext(),
                                AssessmentDetailScreen.class);
                        intent2.putExtra("id", id);
                        startActivity(intent2);
                    }
                });
    } // end of onCreate

    public void goToAddMentorScreen(View v){
        Intent intent = new Intent(CourseDetail.this, AddMentorScreen.class);
        intent.putExtra("id", courseId);
        startActivity(intent);
    }

    public void goToAddAssessmentScreen(View v){
        Intent intent = new Intent(CourseDetail.this, AddAssessmentScreen.class);
        intent.putExtra("id", courseId);
        startActivity(intent);
    }


    public void deleteCourse(View view) throws ParseException {
        new AlertDialog.Builder(this)
                .setTitle("Please confirm")
                .setMessage(
                        "Are you sure you want to delete " +
                                "this class?")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog,
                                    int whichButton) {
                                DatabaseManager dbMgr =
                                        new DatabaseManager(
                                                getApplicationContext(), null, null, 2);
                                //dbMgr.deleteMentor(courseId);
                                //dbMgr.deleteAssessment(courseId);
                                dbMgr.deleteMentorsForCourse(courseId);
                                dbMgr.deleteAsmForCourse(courseId);
                                dbMgr.deleteCourse(courseId);

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

        //delete course and  assignment alerts
        ArrayList<Assessment> allAssignmentsToBeDeleted = new ArrayList<Assessment>();
        allAssignmentsToBeDeleted = dbMgr.getAllAssessmentsForCourse(courseId);
        courseStartDatesAlerts = getSharedPreferences("courseStartDatesAlerts", Context.MODE_PRIVATE);
        courseEndDatesAlerts = getSharedPreferences("courseEndDatesAlerts", Context.MODE_PRIVATE);
        assessmentDueDateAlerts = getSharedPreferences("assessmentDueDateAlerts", Context.MODE_PRIVATE);
        assessmentGoalDatesAlerts = getSharedPreferences("assessmentGoalDateAlerts", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorStartDate = courseStartDatesAlerts.edit();
        SharedPreferences.Editor editorEndDate = courseEndDatesAlerts.edit();
        editorEndDate.remove(Long.toString(courseId));
        editorStartDate.remove(Long.toString(courseId));
        editorEndDate.apply();
        editorStartDate.apply();

        SharedPreferences.Editor editorDueDate = assessmentDueDateAlerts.edit();
        SharedPreferences.Editor editorGoalDate = assessmentGoalDatesAlerts.edit();


        for(int x = 0; x < allAssignmentsToBeDeleted.size(); x++){
            editorDueDate.remove(Long.toString(allAssignmentsToBeDeleted.get(x).getId()));
            editorGoalDate.remove(Long.toString(allAssignmentsToBeDeleted.get(x).getId()));
        }
        editorDueDate.apply();
        editorGoalDate.apply();

    }

    public void updateCourse(View view) throws ParseException {
        DatabaseManager dbMgr = new DatabaseManager(this, null, null, 2);
        String title = ((TextView) findViewById(
                R.id.courseDTitleInput)).getText().toString();
        String startDate = ((TextView) findViewById(
                R.id.courseDStartDateInput)).getText().toString();
        String endDate = ((TextView) findViewById(
                R.id.courseDEndDateInput)).getText().toString();
        String status = getCourseStatusFromSpinner();
        String notes = ((TextView) findViewById(
                R.id.courseDNotesInput)).getText().toString();

        Course course = new Course(title, startDate, endDate, status, notes);
        course.setId(courseId);
        dbMgr.updateCourse(course);

        //save coure Alerts
        saveAlertDetails();



        finish();
    }

    private String getCourseStatusFromSpinner(){

        Log.d("dannyproblem", "Entering get course status method");

        Spinner type = (Spinner) findViewById(R.id.courseDStatusDropdown);
        String status = type.getSelectedItem().toString();
        Log.d("dannyproblem", "whats the type being added? : " + status);


        if(status.equals("Not Started")){

            return "Not Started";

        }

        else if(status.equals("In Progress")){


            return "In Progress";

        }

        else{

            return "Passed";
        }


    }

    private void setCourseStatusDropDownValue(){

        Spinner type = (Spinner) findViewById(R.id.courseDStatusDropdown);

        Log.d("dannyproblem", "Course Status: " + course.getStatus());
        if(course.getStatus().equals("Not Started")){

            type.setSelection(0);

        }
        else if(course.getStatus().equals("In Progress")){
            type.setSelection(1);
        }

        else{
            type.setSelection(2);
        }


    }

    public void setStartDateAlertButtonText(View view){

        String value = courseDStartDateButton.getText().toString();

        if (value.equals("Turn Alert On")){
            courseDStartDateButton.setText("Turn Alert Off");
        }
        else{
            courseDStartDateButton.setText("Turn Alert On");
        }
    }

    public void setEndDateAlertButtonText(View view){

        String value = courseDEndDateButton.getText().toString();

        if (value.equals("Turn Alert On")){
            courseDEndDateButton.setText("Turn Alert Off");
        }
        else{
            courseDEndDateButton.setText("Turn Alert On");
        }
    }

    public void saveAlertDetails(){
        courseStartDatesAlerts = getSharedPreferences("courseStartDatesAlerts", Context.MODE_PRIVATE);
        courseEndDatesAlerts = getSharedPreferences("courseEndDatesAlerts", Context.MODE_PRIVATE);


        SharedPreferences.Editor editorStartDate = courseStartDatesAlerts.edit();
        SharedPreferences.Editor editorEndDate = courseEndDatesAlerts.edit();

        //start date
        if(courseDStartDateButton.getText().toString().equals("Turn Alert On")){
            editorStartDate.remove(Long.toString(courseId));
        }
        else{
            editorStartDate.putString(Long.toString(courseId), courseDStartDateText.getText().toString());

        }

        editorStartDate.apply();

        //end date
        if(courseDEndDateButton.getText().toString().equals("Turn Alert On")){
            editorEndDate.remove(Long.toString(courseId));
        }
        else{
            editorEndDate.putString(Long.toString(courseId), courseDEndDateText.getText().toString());

        }

        editorEndDate.apply();
    }

    private void setAlertButtonText(){
        courseStartDatesAlerts = getSharedPreferences("courseStartDatesAlerts", Context.MODE_PRIVATE);
        courseEndDatesAlerts = getSharedPreferences("courseEndDatesAlerts", Context.MODE_PRIVATE);

        if(courseStartDatesAlerts.contains(Long.toString(courseId))){
            courseDStartDateButton.setText("Turn Alert Off");
        }

        if(courseEndDatesAlerts.contains(Long.toString(courseId))){
            courseDEndDateButton.setText("Turn Alert Off");
        }
    }

    public void shareNotes(View view){

        String notes = ((TextView) findViewById(
                R.id.courseDNotesInput)).getText().toString();
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_TEXT   , notes);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(CourseDetail.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }
}
