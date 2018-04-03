package com.example.carlosespejo.wguapp;

import android.util.Log;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by carlosespejo on 12/12/17.
 */

public class Term {

    long id;
    String title;
    Date startDate;
    Date endDate;

    //helper varibales
    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

    public Term() {
    }

    public Term(String title, Date startDate, Date endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Term(String title, String startDate, String endDate) throws ParseException {

        this.title = title;
        this.startDate = df.parse(startDate);
        this.endDate = df.parse(endDate);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStartDateFormatted(){
        return this.df.format(this.getStartDate());
    }

    public void setStartDateWithParser(String date) throws ParseException {
        this.startDate= this.df.parse(date);
    }

    public String getEndDateFormatted(){
        return this.df.format(this.getEndDate());
    }


    public void setEndDateWithParser(String date) throws ParseException {
        this.endDate = this.df.parse(date);
    }
}
