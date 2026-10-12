package com.jobportal.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobportal.dao.ApplicationDAO;
import com.jobportal.model.Application;

@WebServlet("/applications/*")
public class ApplicationServlet extends HttpServlet {

    private ApplicationDAO applicationDAO;

    private ObjectMapper objectMapper;


    @Override
    public void init() throws ServletException {

        applicationDAO =
            new ApplicationDAO();

        objectMapper =
            new ObjectMapper();
    }


    // =========================
    // CORS
    // =========================

    private void setCorsHeaders(
            HttpServletResponse response) {

        response.setHeader(
            "Access-Control-Allow-Origin",
            "http://localhost:3000"
        );

        response.setHeader(
            "Access-Control-Allow-Methods",
            "GET, POST, OPTIONS"
        );

        response.setHeader(
            "Access-Control-Allow-Headers",
            "Content-Type"
        );
    }


    // =========================
    // OPTIONS
    // =========================

    @Override
    protected void doOptions(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        setCorsHeaders(response);

        response.setStatus(
            HttpServletResponse.SC_OK
        );
    }


    // =========================
    // POST
    // =========================

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        setCorsHeaders(response);

        response.setContentType(
            "application/json"
        );

        response.setCharacterEncoding(
            "UTF-8"
        );


        String json =
            request.getReader()
                .lines()
                .collect(
                    Collectors.joining()
                );


        Application application =
            objectMapper.readValue(
                json,
                Application.class
            );


        boolean alreadyApplied =
            applicationDAO.hasAlreadyApplied(
                application.getUserId(),
                application.getJobId()
            );


        PrintWriter out =
            response.getWriter();


        if (alreadyApplied) {

            response.setStatus(
                HttpServletResponse.SC_BAD_REQUEST
            );


            out.print(
                "{\"message\":\"You have already applied for this job\"}"
            );

            out.flush();

            return;
        }


        boolean result =
            applicationDAO.applyForJob(
                application
            );


        if (result) {

            out.print(
                "{\"message\":\"Application submitted successfully\"}"
            );

        } else {

            response.setStatus(
                HttpServletResponse.SC_BAD_REQUEST
            );

            out.print(
                "{\"message\":\"Application submission failed\"}"
            );
        }


        out.flush();
    }


    // =========================
    // GET
    // =========================

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        setCorsHeaders(response);

        response.setContentType(
            "application/json"
        );

        response.setCharacterEncoding(
            "UTF-8"
        );


        String path =
            request.getPathInfo();


        PrintWriter out =
            response.getWriter();


        if (path == null) {

            response.setStatus(
                HttpServletResponse.SC_BAD_REQUEST
            );

            out.print(
                "{\"message\":\"User ID is required\"}"
            );

            return;
        }


        if (path.startsWith("/user/")) {

            String userIdString =
                path.substring(
                    "/user/".length()
                );


            try {

                int userId =
                    Integer.parseInt(
                        userIdString
                    );


                List<Application> applications =
                    applicationDAO
                        .getApplicationsByUser(
                            userId
                        );


                String json =
                    objectMapper.writeValueAsString(
                        applications
                    );


                out.print(json);


            } catch (NumberFormatException e) {

                response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
                );


                out.print(
                    "{\"message\":\"Invalid user ID\"}"
                );
            }
        }


        out.flush();
    }
}