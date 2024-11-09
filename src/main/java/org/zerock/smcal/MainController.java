package org.zerock.smcal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Calendar calendar = new GregorianCalendar();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH); // 현재 월 (0부터 시작)

        String yearParam = request.getParameter("year");
        String monthParam = request.getParameter("month");

        if (yearParam != null && monthParam != null) {
            year = Integer.parseInt(yearParam);
            month = Integer.parseInt(monthParam);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
        }

        request.setAttribute("year", year);
        request.setAttribute("month", month);
        request.setAttribute("calendar", calendar);

        request.getRequestDispatcher("/WEB-INF/views/main.jsp").forward(request, response);
    }
}
