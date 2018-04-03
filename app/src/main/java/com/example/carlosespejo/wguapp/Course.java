package com.example.carlosespejo.wguapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by carlosespejo on 12/12/17.
 */

public class Course {

    private long id;
    private long termId;
    private String title;
    private String status;
    private Date startDate;
    private Date endDate;
    private String notes;

    //helper varibales
    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

    public Course() {
    }

    public Course(String title, Date startDate, Date endDate, String status, String notes) {

        this.setTitle(title);
        this.setStatus(status);
        this.setStartDate(startDate);
        setEndDate(endDate);
        this.setNotes(notes);
    }

    public Course(String title, String startDate, String endDate, String status, String notes) throws ParseException {

        this.setTitle(title);
        this.startDate = df.parse(startDate);
        this.endDate = df.parse(endDate);
        this.setStatus(status);
        this.setNotes(notes);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTermId() {
        return termId;
    }

    public void setTermId(long termId) {
        this.termId = termId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
