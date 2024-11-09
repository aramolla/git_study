package org.zerock.smcal;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.zerock.smcal.util.Database;

import java.io.IOException;

public class SignUpController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Database의 addUser 메서드를 호출
        if (Database.addUser(username, password)) {
            // 회원가입 성공 시, 세션 생성 및 로그인 상태 설정
            HttpSession session = request.getSession();
            session.setAttribute("user", username);

            response.sendRedirect("smcal");
        } else {
            response.sendRedirect("signupError");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
    }
}
