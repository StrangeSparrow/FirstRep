package com.mycorp.app;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;

public class TestServlet extends HttpServlet {
    public void service(ServletRequest request, ServletResponse response) throws IOException {
        response.setContentType(Constants.CONTENT_TYPE);
        PrintWriter pw = response.getWriter();

        pw.write("Start Page");
        pw.close();
    }
}
