package com.jobportal.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobportal.dao.UserDAO;
import com.jobportal.model.User;

@WebServlet("/users/*")
public class UserServlet extends HttpServlet {

    private UserDAO userDAO;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {

        userDAO = new UserDAO();
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
            "POST, OPTIONS"
        );

        response.setHeader(
            "Access-Control-Allow-Headers",
            "Content-Type"
        );
    }

    // Handle OPTIONS request
    @Override
    protected void doOptions(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        setCorsHeaders(response);

        response.setStatus(HttpServletResponse.SC_OK);
    }

    // Handle POST request
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        setCorsHeaders(response);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Read JSON from React
        String json = request.getReader()
                .lines()
                .collect(Collectors.joining());

        // Convert JSON to User object
        User user = objectMapper.readValue(json, User.class);

        // Check which URL was called
        String path = request.getPathInfo();

        PrintWriter out = response.getWriter();

        // =========================
        // LOGIN
        // =========================

        if ("/login".equals(path)) {

            User loggedInUser = userDAO.loginUser(
                user.getEmail(),
                user.getPassword()
            );

            if (loggedInUser != null) {

                String responseJson =
                    objectMapper.writeValueAsString(loggedInUser);

                out.print(responseJson);

            } else {

                response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED
                );

                out.print(
                    "{\"message\":\"Invalid email or password\"}"
                );
            }

        }

        // =========================
        // REGISTER
        // =========================

        else {

            boolean result = userDAO.registerUser(user);

            if (result) {

                out.print(
                    "{\"message\":\"User registered successfully\"}"
                );

            } else {

                response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
                );

                out.print(
                    "{\"message\":\"Registration failed\"}"
                );
            }
        }

        out.flush();
    }
}