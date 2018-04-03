package com.example.carlosespejo.wguapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by carlosespejo on 11/4/17.
 */

//import java.time.*;

public class Assessment {

    private long id;
    private long courseId;
    private Date dueDate;
    private String title;
    private boolean isObjective;



    private Date goalDate;

    //helper varibales
    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

    public Assessment() {
    }

    public Assessment(String title, Date dueDate, boolean isObjective) {

        this.dueDate = dueDate;
        this.title = title;
        this.isObjective = isObjective;
    }

    public Assessment(String title, Date dueDate,  int objectiveBit) {

        this.dueDate = dueDate;
        this.title = title;
        this.setObjectiveBit(objectiveBit);
    }

    public Assessment(String title, String dueDate,  int objectiveBit) throws ParseException {

        this.dueDate = df.parse(dueDate);
        this.title = title;
        this.setObjectiveBit(objectiveBit);
    }

    public Assessment(String title, String dueDate, String goalDate , int objectiveBit) throws ParseException {

        this.dueDate = df.parse(dueDate);
        this.title = title;
        this.setObjectiveBit(objectiveBit);
        this.goalDate = df.parse(goalDate);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isObjective() {
        return isObjective;
    }

    public void setObjective(boolean objective) {
        isObjective = objective;
    }

    //for sql database
    public int isObjectiveBit() {

        if(this.isObjective){
            return 1;
        }
        return 0;
    }

    public void setObjectiveBit(int number) {

        if(number == 1){
            this.setObjective(true);
        }
        else{
            isObjective = false;
        }
    }

    public String getDueDateFormatted(){
        return this.df.format(this.getDueDate());
    }

    public void setDueDateWithParser(String date) throws ParseException {
        this.dueDate= this.df.parse(date);
    }

    public Date getGoalDate() {
        return goalDate;
    }

    public void setGoalDate(Date goalDate) {
        this.goalDate = goalDate;
    }

    public String getGoalDateFormatted() {
        return this.df.format(this.getGoalDate());
    }

    public void setGoalDateFormatted(String goalDate) throws ParseException {
        this.goalDate = this.df.parse(goalDate);
    }
}
