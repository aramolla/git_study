package org.zerock.smcal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.zerock.smcal.util.Database;

public class EditController extends HttpServlet {

    // 일정 수정 페이지로 이동
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String eventId = request.getParameter("id");
        try (Connection conn = Database.getConnection()) {
            String query = "SELECT title, event_date, start_time, end_time FROM events WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, eventId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        request.setAttribute("id", eventId);
                        request.setAttribute("title", rs.getString("title"));
                        request.setAttribute("event_date", rs.getDate("event_date").toString());
                        request.setAttribute("start_time", rs.getTime("start_time").toString());
                        request.setAttribute("end_time", rs.getTime("end_time").toString());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/WEB-INF/views/editPage.jsp").forward(request, response);
    }

    // 일정 수정 저장 및 삭제 처리
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String eventId = request.getParameter("id");
        String action = request.getParameter("action");

        try (Connection conn = Database.getConnection()) {
            if ("delete".equals(action)) {
                // 일정 삭제 처리
                String deleteQuery = "DELETE FROM events WHERE id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                    pstmt.setString(1, eventId);
                    pstmt.executeUpdate();
                }
            } else {
                // 일정 수정 처리
                String title = request.getParameter("title");
                String date = request.getParameter("date");
                String startTime = request.getParameter("startTime");
                String endTime = request.getParameter("endTime");

                String updateQuery = "UPDATE events SET title = ?, event_date = ?, start_time = ?, end_time = ? WHERE id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                    pstmt.setString(1, title);
                    pstmt.setString(2, date);
                    pstmt.setString(3, startTime);
                    pstmt.setString(4, endTime);
                    pstmt.setString(5, eventId);
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("smcal"); // main 페이지로 돌아가기
    }
}
