package com.example.admin.report.pojo;

import java.io.Serializable;

/**
 * Created by Admin on 2016/11/10.
 */

public class Student implements Serializable {
    private String name;
    private String surname;
    private int  user_ID;
    private int std_Number;
    private String gender;
    private String course;
    private int Subject1;
    private int Subject2;
    private int Subject3;

    public Student(String course, String gender, int subject1, String name, int subject2, int subject3, String surname, int user_ID) {
        this.course = course;
        this.gender = gender;
        Subject1 = subject1;
        this.name = name;
        Subject2 = subject2;
        Subject3 = subject3;
        this.surname = surname;
        this.user_ID = user_ID;
    }

    public Student() {
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStd_Number() {
        return std_Number;
    }

    public void setStd_Number(int std_Number) {
        this.std_Number = std_Number;
    }

    public int getSubject1() {
        return Subject1;
    }

    public void setSubject1(int subject1) {
        Subject1 = subject1;
    }

    public int getSubject2() {
        return Subject2;
    }

    public void setSubject2(int subject2) {
        Subject2 = subject2;
    }

    public int getSubject3() {
        return Subject3;
    }

    public void setSubject3(int subject3) {
        Subject3 = subject3;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }
}
