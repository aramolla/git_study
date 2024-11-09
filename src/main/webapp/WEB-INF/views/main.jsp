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
            background-color: yellow;  /* 오늘 날짜의 배경 색상 */
            font-weight: bold;
        }
    </style>
</head>
<body>
<div style="display: flex; justify-content: space-between; align-items: center;">
    <a href="main?year=${year}&month=${month - 1}" class="nav-btn">이전 달</a>
    <h2>${year}년 ${month + 1}월 달력</h2>
    <a href="main?year=${year}&month=${month + 1}" class="nav-btn">다음 달</a>
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
        Calendar today = new GregorianCalendar();  // 오늘 날짜
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
<!-- 작성 페이지와 로그아웃 버튼 -->
<div style="margin-top: 20px;">
    <a href="writePage.jsp" class="nav-btn">작성 페이지로 이동</a>
    <a href="logout" class="nav-btn">로그아웃</a>
</div>
</body>
</html>
