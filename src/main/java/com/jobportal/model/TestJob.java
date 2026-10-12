package com.jobportal.model;

import java.sql.Date;

public class TestJob {

    public static void main(String[] args) {

        Job job = new Job(
                1,
                "Java Developer",
                "ABC Technologies",
                "Chennai",
                "Java backend developer",
                400000,
                Date.valueOf("2026-09-09")
        );

        System.out.println(job);
    }
}