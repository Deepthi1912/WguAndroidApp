package com.example.carlosespejo.wguapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;

public class MentorDetailScreen extends AppCompatActivity {

    long courseId;
    long mentorId;
    CourseMentor mentor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_detail_screen);

        //set fields
        /**
         * get detail info from DB
         */
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mentorId =  extras.getLong("id");

            DatabaseManager dbMgr = new DatabaseManager(this, null, null, 2);
             mentor = new CourseMentor();
            try {

                mentor = dbMgr.getMentor(mentorId);
                courseId = mentor.getCourseId();


            } catch (ParseException e) {


                e.printStackTrace();
            }
            if (mentor != null) {


                ((TextView) findViewById(R.id.mentorDetailNameInput))
                        .setText(mentor.getName());
                ((TextView) findViewById(R.id.mentorDetailPhoneInput))
                        .setText(mentor.getPhoneNumber());
                ((TextView) findViewById(R.id.mentorDetailEmailInput))
                        .setText(mentor.getEmail());

            } else {
                Log.d("db", "contact null");
            }
        }
    }// end of onCreate

    public void deleteMentor(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Please confirm")
                .setMessage(
                        "Are you sure you want to delete " +
                                "this mentor?")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog,
                                    int whichButton) {
                                DatabaseManager dbMgr =
                                        new DatabaseManager(
                                                getApplicationContext(), null, null, 2);
                                dbMgr.deleteMentor(mentorId);
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
    }

    public void updateMentor(View view) throws ParseException {
        DatabaseManager dbMgr = new DatabaseManager(this, null, null, 2);
        String name = ((TextView) findViewById(
                R.id.mentorDetailNameInput)).getText().toString();
        String phone = ((TextView) findViewById(
                R.id.mentorDetailPhoneInput)).getText().toString();
        String email = ((TextView) findViewById(
                R.id.mentorDetailEmailInput)).getText().toString();

        CourseMentor mentor = new CourseMentor(name, phone, email);
        mentor.setCourseId(courseId);
        mentor.setId(mentorId);
        dbMgr.updateMentor(mentor);



        finish();
    }




}
