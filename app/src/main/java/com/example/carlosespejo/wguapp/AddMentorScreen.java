package com.example.carlosespejo.wguapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;

public class AddMentorScreen extends AppCompatActivity {

    long courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mentor_screen);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            courseId = extras.getLong("id");
        }




    }

    public void cancel(View view) {
        finish();
    }

    public void addMentor(View view) throws ParseException {
        DatabaseManager dbMgr = new DatabaseManager(this, null, null, 2);
        String title = ((TextView) findViewById(
                R.id.addMentorNameInput)).getText().toString();
        String phone = ((TextView) findViewById(
                R.id.addMentorPhoneInput)).getText().toString();
        String email = ((TextView) findViewById(
                R.id.addMentorDEmailInput)).getText().toString();

        CourseMentor mentor = new CourseMentor(title, phone, email);
        mentor.setCourseId(courseId);
        dbMgr.addMentor(mentor);



        finish();
    }
}
