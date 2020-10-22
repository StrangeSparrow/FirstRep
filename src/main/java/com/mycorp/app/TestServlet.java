package com.mycorp.app;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;

import java.io.*;

public class TestServlet extends HttpServlet {
    public void service(ServletRequest request,ServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        pw.println("<B>Hello!");
        pw. close () ;
    }
}
