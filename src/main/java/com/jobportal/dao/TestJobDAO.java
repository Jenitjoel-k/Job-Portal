package com.jobportal.dao;

import java.util.List;

import com.jobportal.model.Job;

public class TestJobDAO {

    public static void main(String[] args) {

        JobDAO jobDAO = new JobDAO();

        List<Job> jobs = jobDAO.getAllJobs();

        for (Job job : jobs) {
            System.out.println(job);
        }
        System.out.println("\n===== ADD JOB =====");

        Job newJob = new Job(
                "Backend Developer",
                "ABC Solutions",
                "Chennai",
                "Java Servlet and JDBC developer",
                450000,
                java.sql.Date.valueOf("2026-09-09")
        );

        boolean result = jobDAO.addJob(newJob);

        System.out.println("Job added: " + result);
    }
  
}