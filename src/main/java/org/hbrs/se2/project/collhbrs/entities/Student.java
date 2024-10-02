package org.hbrs.se2.project.collhbrs.entities;

import java.time.LocalDate;

public class Student{

    private int id;
    private String firstname;
    private String lastname;
    private LocalDate birthday;
    private int fachsemester;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public int getFachsemester() {
        return fachsemester;
    }

    public void setFachsemester(int fachsemester) {
        this.fachsemester = fachsemester;
    }
}

