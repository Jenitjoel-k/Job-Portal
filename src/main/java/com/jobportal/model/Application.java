package com.jobportal.model;

public class Application {

    private int id;

    private int userId;

    private int jobId;

    private String resume;

    private String coverLetter;

    private String status;

    private String appliedDate;

    private String jobTitle;

    private String company;

    private String location;


    public Application() {
    }


    public Application(
            int userId,
            int jobId,
            String resume,
            String coverLetter,
            String status,
            String appliedDate) {

        this.userId = userId;
        this.jobId = jobId;
        this.resume = resume;
        this.coverLetter = coverLetter;
        this.status = status;
        this.appliedDate = appliedDate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }


    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }


    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(String appliedDate) {
        this.appliedDate = appliedDate;
    }


    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }


    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}