package org.hbrs.se2.project.hellocar.dtos;

import java.time.LocalDate;

public interface StudentDTO
{

    public int getId();
    void setId(int id);

    public String getLastname();

    void setLastname(String lastname);

    public String getFirstname();

    void setFirstname(String firstname);

    public LocalDate getBirthday();

    void setBirthday(LocalDate birthday);

    public int getAge();
}
