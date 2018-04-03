package com.example.carlosespejo.wguapp;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTermScreen extends AppCompatActivity {

    private TextView selectDate1;
    private TextView selectDate2;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private DatePickerDialog.OnDateSetListener onDateSetListener2;
    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //show date picker and get date
        setContentView(R.layout.activity_add_term_screen);

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
                        AddTermScreen.this,
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
                        AddTermScreen.this,
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

    public void addTerm(View view) throws ParseException {

        if(datesAreCompliant()) {

            DatabaseManager dbMgr = new DatabaseManager(this, null, null, 2);
            String title = ((TextView) findViewById(
                    R.id.termDTitleInput)).getText().toString();
            String startDate = ((TextView) findViewById(
                    R.id.courseDateTextView1)).getText().toString();
            String endDate = ((TextView) findViewById(
                    R.id.courseDateTextView2)).getText().toString();
            Term term = new Term(title, startDate, endDate);
            dbMgr.addTerm(term);


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
}
