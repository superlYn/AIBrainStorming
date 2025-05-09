package com.example.brainstromai.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ProjectReport {

    @Id
    public String projectReportId;

    public String reportContent;

}
