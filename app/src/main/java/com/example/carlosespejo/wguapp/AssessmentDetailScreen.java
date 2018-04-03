package com.example.carlosespejo.wguapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.AlertDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AssessmentDetailScreen extends AppCompatActivity {

    long courseId;
    long assessmentId;
    DatabaseManager dbMgr;
    private TextView selectDate1;
    private TextView selectDate2;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private DatePickerDialog.OnDateSetListener onDateSetListener2;
    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    Assessment assessment;
    SharedPreferences assessmentGoalDatesAlerts;
    SharedPreferences assessmentDueDateAlerts;
    Button alertDueDateButton;
    Button alertGoalDateButton;
    TextView dueDateText;
    TextView goalDateText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail_screen);

        //set buttons
        alertDueDateButton = (Button) findViewById(R.id.assessDDueDateAlertBtn);
        alertGoalDateButton = (Button) findViewById(R.id.assessDGoalDateAlertBtn);
        dueDateText = (TextView) findViewById(R.id.assessDetailDueDateInput);
        goalDateText = (TextView) findViewById(R.id.assessDGoalDateInput);

        /**
         * get detail info from DB
         */
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            assessmentId =  extras.getLong("id");
            Log.d("dannyalert", "Couse id: " + Long.toString(assessmentId));

            dbMgr = new DatabaseManager(this, null, null, 2);
             assessment = new Assessment();
            try {

                assessment = dbMgr.getAssessment(assessmentId);
                courseId = assessment.getCourseId();
                Log.d("dannyalert", "Assess id: " + Long.toString(assessmentId));
                Log.d("dannyalert", "Course id: after the assess assghnment " + Long.toString(courseId));


            } catch (ParseException e) {


                e.printStackTrace();
            }
            if (assessment != null) {


                ((TextView) findViewById(R.id.assessDetailTitleInput))
                        .setText(assessment.getTitle());
                ((TextView) findViewById(R.id.assessDetailDueDateInput))
                        .setText(assessment.getDueDateFormatted());
                ((TextView) findViewById(R.id.assessDGoalDateInput))
                        .setText(assessment.getGoalDateFormatted());
                setAssessmentTypeForTextInput();

            } else {
                Log.d("db", "contact null");
            }
        }

        //setAlertButtons
        setAlertButtonText();



        //show date picker
        selectDate1 = (TextView) findViewById(R.id.assessDetailDueDateInput);
        selectDate2 = (TextView) findViewById(R.id.assessDGoalDateInput);



        selectDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);



                DatePickerDialog dialog = new DatePickerDialog(
                        AssessmentDetailScreen.this,
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
        }; // end of onCreate

        selectDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);



                DatePickerDialog dialog = new DatePickerDialog(
                        AssessmentDetailScreen.this,
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


    public void deleteAssessment(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Please confirm")
                .setMessage(
                        "Are you sure you want to delete " +
                                "this assessment?")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog,
                                    int whichButton) {
                                DatabaseManager dbMgr =
                                        new DatabaseManager(
                                                getApplicationContext(), null, null, 2);
                                dbMgr.deleteAssessment(assessmentId);
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
        //delete alerts
        assessmentDueDateAlerts = getSharedPreferences("assessmentDueDateAlerts", Context.MODE_PRIVATE);
        assessmentGoalDatesAlerts = getSharedPreferences("assessmentGoalDateAlerts", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorDueDate = assessmentDueDateAlerts.edit();
        SharedPreferences.Editor editorGoalDate = assessmentGoalDatesAlerts.edit();
        editorDueDate.remove(Long.toString(assessmentId));
        editorGoalDate.remove(Long.toString(assessmentId));
        editorDueDate.apply();
        editorGoalDate.apply();


    }

    public void updateAssessment(View view) throws ParseException {
        DatabaseManager dbMgr = new DatabaseManager(this, null, null, 1);
        String title = ((TextView) findViewById(
                R.id.assessDetailTitleInput)).getText().toString();
        String dueDate = ((TextView) findViewById(
                R.id.assessDetailDueDateInput)).getText().toString();
        String goalDate = ((TextView) findViewById(
                R.id.assessDGoalDateInput)).getText().toString();

        int type = getAssessmentType();


        Assessment assessment = new Assessment(title, dueDate, goalDate, type);
        assessment.setCourseId(courseId);
        assessment.setId(assessmentId);
        dbMgr.updateAssessment(assessment);

        //alerts shared preferences
        saveAlertDetails();

        finish();
    }

    private int getAssessmentType(){

        Spinner type = (Spinner) findViewById(R.id.assessDetailTypeDropdown);
        String dueDate = type.getSelectedItem().toString();

        if(dueDate.equals("Objective")){
            return 1;
        }

        else{
            return 0;
        }


    }

    private void setAssessmentTypeForTextInput(){

        Spinner type = (Spinner) findViewById(R.id.assessDetailTypeDropdown);

        Log.d("dannyproblem", "assesment type: " + assessment.isObjective());
        if(assessment.isObjective()){

            type.setSelection(0);

        }

        else{
            type.setSelection(1);
        }


    }

    public void setGoalDateAlertButtonText(View view){

        String value = alertGoalDateButton.getText().toString();

        if (value.equals("Turn Alert On")){
            alertGoalDateButton.setText("Turn Alert Off");
        }
        else{
            alertGoalDateButton.setText("Turn Alert On");
        }
    }

    public void setDueDateAlertButtonText(View view){

        String value = alertDueDateButton.getText().toString();

        if (value.equals("Turn Alert On")){
            alertDueDateButton.setText("Turn Alert Off");
        }
        else{
            alertDueDateButton.setText("Turn Alert On");
        }
    }

    public void saveAlertDetails(){
        assessmentDueDateAlerts = getSharedPreferences("assessmentDueDateAlerts", Context.MODE_PRIVATE);
        assessmentGoalDatesAlerts = getSharedPreferences("assessmentGoalDateAlerts", Context.MODE_PRIVATE);


        SharedPreferences.Editor editorDueDate = assessmentDueDateAlerts.edit();
        SharedPreferences.Editor editorGoalDate = assessmentGoalDatesAlerts.edit();

        //due date
        if(alertDueDateButton.getText().toString().equals("Turn Alert On")){
            editorDueDate.remove(Long.toString(assessmentId));

        }
        else{
            editorDueDate.putString(Long.toString(assessmentId), dueDateText.getText().toString());

        }

        editorDueDate.apply();

        //goal date
        if(alertGoalDateButton.getText().toString().equals("Turn Alert On")){
            editorGoalDate.remove(Long.toString(assessmentId));
        }
        else{
            editorGoalDate.putString(Long.toString(assessmentId), goalDateText.getText().toString());

        }

        editorGoalDate.apply();
    }

    private void setAlertButtonText(){
        assessmentDueDateAlerts = getSharedPreferences("assessmentDueDateAlerts", Context.MODE_PRIVATE);
        assessmentGoalDatesAlerts = getSharedPreferences("assessmentGoalDateAlerts", Context.MODE_PRIVATE);

        if(assessmentDueDateAlerts.contains(Long.toString(assessmentId))){
            alertDueDateButton.setText("Turn Alert Off");
        }

        if(assessmentGoalDatesAlerts.contains(Long.toString(assessmentId))){
            alertGoalDateButton.setText("Turn Alert Off");
        }
    }
}

