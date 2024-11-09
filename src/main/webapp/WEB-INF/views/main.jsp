<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.Calendar, java.util.GregorianCalendar" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Main Page</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            width: 14.28%;
            height: 100px;
            border: 1px solid #ddd;
            text-align: center;
        }
        .nav-btn {
            padding: 10px;
            font-size: 16px;
        }
        .today {
            background-color: yellow;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div style="display: flex; justify-content: space-between; align-items: center;">
    <%
        int year = (Integer) request.getAttribute("year");
        int month = (Integer) request.getAttribute("month");

        // 이전 달 설정
        Calendar prevCalendar = new GregorianCalendar(year, month, 1);
        prevCalendar.add(Calendar.MONTH, -1);
        int prevYear = prevCalendar.get(Calendar.YEAR);
        int prevMonth = prevCalendar.get(Calendar.MONTH);

        // 다음 달 설정
        Calendar nextCalendar = new GregorianCalendar(year, month, 1);
        nextCalendar.add(Calendar.MONTH, 1);
        int nextYear = nextCalendar.get(Calendar.YEAR);
        int nextMonth = nextCalendar.get(Calendar.MONTH);

        // 현재 날짜를 나타내는 today 객체 선언
        Calendar today = Calendar.getInstance();
    %>

    <!-- 이전 달 및 다음 달 버튼 -->
    <a href="smcal?year=<%= prevYear %>&month=<%= prevMonth %>" class="nav-btn">이전 달</a>
    <h2><%= year %>년 <%= month + 1 %>월 달력</h2>
    <a href="smcal?year=<%= nextYear %>&month=<%= nextMonth %>" class="nav-btn">다음 달</a>
</div>

<!-- 달력 표시 -->
<table>
    <thead>
    <tr>
        <th>일</th><th>월</th><th>화</th><th>수</th><th>목</th><th>금</th><th>토</th>
    </tr>
    </thead>
    <tbody>
    <%
        Calendar cal = (Calendar) request.getAttribute("calendar");
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        int currentDay = 1;
        boolean started = false;

        for (int i = 0; i < 6; i++) {
    %>
    <tr>
        <%
            for (int j = 1; j <= 7; j++) {
                if (!started && j == firstDayOfWeek) {
                    started = true;
                }

                if (started && currentDay <= daysInMonth) {
                    // 오늘 날짜와 일치하면 'today' 클래스를 적용
                    boolean isToday = (cal.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                            cal.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                            currentDay == today.get(Calendar.DAY_OF_MONTH));
                    String cellClass = isToday ? "today" : "";
        %>
        <td class="<%= cellClass %>"><%= currentDay %></td>
        <%
            currentDay++;
        } else {
        %>
        <td></td>
        <%
                }
            }
        %>
    </tr>
    <%
            if (currentDay > daysInMonth) {
                break;
            }
        }
    %>
    </tbody>
</table>

<!-- 작성 페이지와 로그아웃 버튼 -->
<div style="margin-top: 20px;">
    <a href="writePage.jsp" class="nav-btn">작성 페이지로 이동</a>
    <a href="logout" class="nav-btn">로그아웃</a>
</div>
</body>
</html>
