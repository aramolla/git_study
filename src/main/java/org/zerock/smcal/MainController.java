package org.zerock.smcal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.http.HttpSession;

public class MainController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // 세션이 없거나, 세션에 사용자 정보가 없으면 로그인 페이지로 리다이렉트
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

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
