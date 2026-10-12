package com.jobportal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.jobportal.model.Application;
import com.jobportal.util.DBConnection;

public class ApplicationDAO {


    // =========================
    // APPLY FOR JOB
    // =========================

    public boolean applyForJob(Application application) {

        String sql =
            "INSERT INTO applications " +
            "(user_id, job_id, resume, cover_letter, status, applied_date) " +
            "VALUES (?, ?, ?, ?, ?, ?)";


        try (
            Connection connection =
                DBConnection.getConnection();

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setInt(
                1,
                application.getUserId()
            );

            statement.setInt(
                2,
                application.getJobId()
            );

            statement.setString(
                3,
                application.getResume()
            );

            statement.setString(
                4,
                application.getCoverLetter()
            );

            statement.setString(
                5,
                "Pending"
            );

            statement.setString(
                6,
                application.getAppliedDate()
            );


            int rows =
                statement.executeUpdate();


            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }


        return false;
    }


    // =========================
    // CHECK DUPLICATE APPLICATION
    // =========================

    public boolean hasAlreadyApplied(
            int userId,
            int jobId) {

        String sql =
            "SELECT id FROM applications " +
            "WHERE user_id = ? AND job_id = ?";


        try (
            Connection connection =
                DBConnection.getConnection();

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userId);

            statement.setInt(2, jobId);


            ResultSet resultSet =
                statement.executeQuery();


            return resultSet.next();

        } catch (Exception e) {

            e.printStackTrace();
        }


        return false;
    }


    // =========================
    // GET USER APPLICATIONS
    // =========================

    public List<Application> getApplicationsByUser(
            int userId) {


        List<Application> applications =
            new ArrayList<>();


        String sql =
            "SELECT " +
            "a.id, " +
            "a.user_id, " +
            "a.job_id, " +
            "a.resume, " +
            "a.cover_letter, " +
            "a.status, " +
            "a.applied_date, " +
            "j.title, " +
            "j.company, " +
            "j.location " +

            "FROM applications a " +

            "JOIN jobs j " +
            "ON a.job_id = j.id " +

            "WHERE a.user_id = ? " +

            "ORDER BY a.id DESC";


        try (
            Connection connection =
                DBConnection.getConnection();

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userId);


            ResultSet resultSet =
                statement.executeQuery();


            while (resultSet.next()) {

                Application application =
                    new Application();


                application.setId(
                    resultSet.getInt("id")
                );

                application.setUserId(
                    resultSet.getInt("user_id")
                );

                application.setJobId(
                    resultSet.getInt("job_id")
                );

                application.setResume(
                    resultSet.getString("resume")
                );

                application.setCoverLetter(
                    resultSet.getString("cover_letter")
                );

                application.setStatus(
                    resultSet.getString("status")
                );

                application.setAppliedDate(
                    resultSet.getString("applied_date")
                );

                application.setJobTitle(
                    resultSet.getString("title")
                );

                application.setCompany(
                    resultSet.getString("company")
                );

                application.setLocation(
                    resultSet.getString("location")
                );


                applications.add(application);
            }


        } catch (Exception e) {

            e.printStackTrace();
        }


        return applications;
    }
}