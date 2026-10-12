package com.jobportal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.jobportal.model.Job;
import com.jobportal.util.DBConnection;

public class JobDAO {

    // ==============================
    // GET ALL JOBS
    // ==============================
    public List<Job> getAllJobs() {

        List<Job> jobs = new ArrayList<>();

        String sql = "SELECT * FROM jobs";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Job job = new Job();

                job.setId(resultSet.getInt("id"));
                job.setTitle(resultSet.getString("title"));
                job.setCompany(resultSet.getString("company"));
                job.setLocation(resultSet.getString("location"));
                job.setDescription(resultSet.getString("description"));
                job.setSalary(resultSet.getDouble("salary"));
                job.setPostedDate(resultSet.getDate("posted_date"));

                jobs.add(job);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jobs;
    }


    // ==============================
    // GET JOB BY ID
    // ==============================
    public Job getJobById(int id) {

        Job job = null;

        String sql = "SELECT * FROM jobs WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    job = new Job();

                    job.setId(resultSet.getInt("id"));
                    job.setTitle(resultSet.getString("title"));
                    job.setCompany(resultSet.getString("company"));
                    job.setLocation(resultSet.getString("location"));
                    job.setDescription(resultSet.getString("description"));
                    job.setSalary(resultSet.getDouble("salary"));
                    job.setPostedDate(resultSet.getDate("posted_date"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return job;
    }
 // ADD NEW JOB
    public boolean addJob(Job job) {

        String sql = "INSERT INTO jobs " +
                     "(title, company, location, description, salary, posted_date) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, job.getTitle());
            statement.setString(2, job.getCompany());
            statement.setString(3, job.getLocation());
            statement.setString(4, job.getDescription());
            statement.setDouble(5, job.getSalary());
            statement.setDate(6, job.getPostedDate());

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
 // UPDATE JOB
    public boolean updateJob(Job job) {

        String sql = "UPDATE jobs SET " +
                     "title = ?, " +
                     "company = ?, " +
                     "location = ?, " +
                     "description = ?, " +
                     "salary = ?, " +
                     "posted_date = ? " +
                     "WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, job.getTitle());
            statement.setString(2, job.getCompany());
            statement.setString(3, job.getLocation());
            statement.setString(4, job.getDescription());
            statement.setDouble(5, job.getSalary());
            statement.setDate(6, job.getPostedDate());
            statement.setInt(7, job.getId());

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
 // DELETE JOB
    public boolean deleteJob(int id) {

        String sql = "DELETE FROM jobs WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}