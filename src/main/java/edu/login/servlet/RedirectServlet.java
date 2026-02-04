package edu.login.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/redirect")
public class RedirectServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String query = request.getParameter("query");

        if (query != null && !query.trim().isEmpty()) {
            String googleUrl = "https://www.google.com/search?q=" + query.trim().replace(" ", "+");
            response.sendRedirect(googleUrl);
        } else {
            response.sendRedirect("https://www.google.com");
        }
    }
}