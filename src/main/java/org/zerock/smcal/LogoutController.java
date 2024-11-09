package org.zerock.smcal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutController extends HttpServlet {

    // GET 요청에 대한 로그아웃 처리 메서드
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // 기존 세션 가져오기
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        response.sendRedirect("login"); // 로그인 페이지로 리다이렉트
    }
}
