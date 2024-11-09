package org.zerock.smcal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import org.zerock.smcal.util.Database;

public class MainController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        Calendar calendar = new GregorianCalendar();
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

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        Map<Integer, List<String>> eventsByDay = new HashMap<>();

        try (Connection conn = Database.getConnection()) {
            String query = "SELECT id, user_id, title, DAY(event_date) as day FROM events WHERE MONTH(event_date) = ? AND YEAR(event_date) = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, month + 1);
                pstmt.setInt(2, year);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int day = rs.getInt("day");
                        String event = rs.getInt("id") + " " + rs.getString("user_id") + " " + rs.getString("title");

                        eventsByDay.computeIfAbsent(day, k -> new ArrayList<>()).add(event);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("year", year);
        request.setAttribute("month", month);
        request.setAttribute("eventsByDay", eventsByDay);
        request.setAttribute("calendar", calendar);

        request.getRequestDispatcher("/WEB-INF/views/main.jsp").forward(request, response);
    }
}
