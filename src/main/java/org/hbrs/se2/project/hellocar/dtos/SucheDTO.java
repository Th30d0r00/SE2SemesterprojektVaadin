package org.hbrs.se2.project.hellocar.dtos;

import java.time.LocalDateTime;
import java.util.List;

public interface SucheDTO {

    public String getJobTitle();

    public String getCompany();

    public List<String> getJobType();

    public List<String> getStandort();

    public String getPublicationDate();

    public String getJobDescription();
}
