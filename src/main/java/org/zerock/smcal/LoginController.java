package org.zerock.smcal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.zerock.smcal.util.Database;

import java.io.IOException;

public class LoginController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (Database.isValidUser(username, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", username);
            response.sendRedirect("smcal");
        } else {
            response.sendRedirect("loginError");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // 로그인 페이지 강제 접근을 위한 파라미터
        String forceLogin = request.getParameter("forceLogin");

        if (session != null && session.getAttribute("user") != null && forceLogin == null) {
            // 세션이 존재하고 로그인 상태이며, forceLogin 파라미터가 없을 경우 main으로 리다이렉트
            response.sendRedirect("smcal");
        } else {
            // forceLogin 파라미터가 있거나 세션이 없을 경우 login 페이지로 포워드
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }

}