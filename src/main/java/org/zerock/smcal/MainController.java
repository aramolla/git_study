package org.zerock.smcal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        // Calendar 객체 생성
        Calendar calendar = new GregorianCalendar();

        // year와 month 파라미터를 받아 현재 날짜를 설정
        String yearParam = request.getParameter("year");
        String monthParam = request.getParameter("month");

        if (yearParam != null && monthParam != null) {
            int year = Integer.parseInt(yearParam);
            int month = Integer.parseInt(monthParam);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        }

        // 현재 year와 month를 request에 설정
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        request.setAttribute("year", year);
        request.setAttribute("month", month);
        request.setAttribute("calendar", calendar);

        request.getRequestDispatcher("/WEB-INF/views/main.jsp").forward(request, response);
    }
}
