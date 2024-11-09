package org.zerock.smcal.util;

import java.sql.*;

public class Database {
    private static Connection connection;

    // 데이터베이스 연결 메서드
    public static Connection getConnection() {
        if (connection == null) {
            try {
                // JDBC 드라이버 로드
                Class.forName("org.mariadb.jdbc.Driver");

                // 데이터베이스 연결 정보 설정
                String url = "jdbc:mariadb://pilab.smu.ac.kr:3306/dkfk1640_db";
                String username = "dkfk1640";
                String password = "xL7vD3qJtY!";

                // 데이터베이스 연결 생성
                connection = DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("JDBC 드라이버 로드 실패");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("데이터베이스 연결 실패");
            }
        }
        return connection;
    }

    // 사용자 확인 메서드
    public static boolean isValidUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                boolean userExists = rs.next();  // 결과가 있으면 true 반환
                System.out.println("로그인 시도: " + username + ", 성공 여부: " + userExists);
                return userExists;  // 결과가 있으면 true 반환
            }
        } catch (SQLException e) {
            System.out.println("SQL 예외 발생: " + e.getMessage());
            e.printStackTrace();
        }
        return false;  // 일치하는 사용자가 없으면 false 반환
    }

    // 사용자 추가 메서드
    public static boolean addUser(String username, String password) {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            System.out.println("사용자 등록 성공: " + username);  // 성공 로그
            return true;  // 성공적으로 추가 시 true 반환
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {  // 중복된 username일 경우
                System.out.println("이미 존재하는 사용자 이름입니다: " + username);
            } else {
                System.out.println("SQL 예외 발생: " + e.getMessage());
            }
            e.printStackTrace();
        }
        System.out.println("사용자 등록 실패: " + username);  // 실패 로그
        return false;  // 추가 실패 시 false 반환
    }

}
