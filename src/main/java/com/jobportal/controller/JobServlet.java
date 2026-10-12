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
import com.jobportal.dao.JobDAO;
import com.jobportal.model.Job;

@WebServlet("/jobs/*")
public class JobServlet extends HttpServlet {

    private JobDAO jobDAO;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        jobDAO = new JobDAO();
        objectMapper = new ObjectMapper();
    }

    // CORS
    private void setCorsHeaders(HttpServletResponse response) {

        response.setHeader(
            "Access-Control-Allow-Origin",
            "http://localhost:3000"
        );

        response.setHeader(
            "Access-Control-Allow-Methods",
            "GET, POST, PUT, DELETE, OPTIONS"
        );

        response.setHeader(
            "Access-Control-Allow-Headers",
            "Content-Type"
        );
    }

    // Handle CORS preflight request
    @Override
    protected void doOptions(HttpServletRequest request,
                             HttpServletResponse response)
            throws ServletException, IOException {

        setCorsHeaders(response);

        response.setStatus(HttpServletResponse.SC_OK);
    }

    // GET
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        setCorsHeaders(response);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathInfo = request.getPathInfo();

        PrintWriter out = response.getWriter();

        if (pathInfo == null) {

            List<Job> jobs = jobDAO.getAllJobs();

            String json = objectMapper.writeValueAsString(jobs);

            out.print(json);

        } else {

            String idString = pathInfo.substring(1);

            int id = Integer.parseInt(idString);

            Job job = jobDAO.getJobById(id);

            String json = objectMapper.writeValueAsString(job);

            out.print(json);
        }

        out.flush();
    }

    // POST
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        setCorsHeaders(response);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = request.getReader()
                .lines()
                .collect(Collectors.joining());

        Job job = objectMapper.readValue(json, Job.class);

        boolean result = jobDAO.addJob(job);

        PrintWriter out = response.getWriter();

        if (result) {
            out.print("{\"message\":\"Job added successfully\"}");
        } else {
            out.print("{\"message\":\"Failed to add job\"}");
        }

        out.flush();
    }

    // PUT
    @Override
    protected void doPut(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        setCorsHeaders(response);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathInfo = request.getPathInfo();

        int id = Integer.parseInt(pathInfo.substring(1));

        String json = request.getReader()
                .lines()
                .collect(Collectors.joining());

        Job job = objectMapper.readValue(json, Job.class);

        job.setId(id);

        boolean result = jobDAO.updateJob(job);

        PrintWriter out = response.getWriter();

        if (result) {
            out.print("{\"message\":\"Job updated successfully\"}");
        } else {
            out.print("{\"message\":\"Job not found\"}");
        }

        out.flush();
    }

    // DELETE
    @Override
    protected void doDelete(HttpServletRequest request,
                            HttpServletResponse response)
            throws ServletException, IOException {

        setCorsHeaders(response);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathInfo = request.getPathInfo();

        int id = Integer.parseInt(pathInfo.substring(1));

        boolean result = jobDAO.deleteJob(id);

        PrintWriter out = response.getWriter();

        if (result) {
            out.print("{\"message\":\"Job deleted successfully\"}");
        } else {
            out.print("{\"message\":\"Job not found\"}");
        }

        out.flush();
    }
}