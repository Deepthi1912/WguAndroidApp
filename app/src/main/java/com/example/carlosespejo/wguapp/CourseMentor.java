package com.example.carlosespejo.wguapp;

/**
 * Created by carlosespejo on 12/16/17.
 */

public class CourseMentor {

    private long id;
    private long courseId;
    private String name;
    private String phoneNumber;
    private String email;

    public CourseMentor() {
    }

    public CourseMentor(String name, String phoneNumber, String email) {

        this.setName(name);
        this.setPhoneNumber(phoneNumber);
        this.setEmail(email);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
