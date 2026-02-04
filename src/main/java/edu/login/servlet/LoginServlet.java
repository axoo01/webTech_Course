package edu.login.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

       
        String username = request.getParameter("username");
        String password = request.getParameter("password");

       
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        
        if (password != null && password.length() < 8) {
            out.println("<h2>Hello " + username + ", your password is weak. Try a strong one.</h2>");
        } else {
            out.println("<h2>Welcome " + username + "!</h2>");
        }
    }
}