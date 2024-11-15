<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.Calendar, java.util.GregorianCalendar, java.util.Map, java.util.List" %>
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
            vertical-align: top;
            font-size: 15px; /* 전체 글자 크기 조정 */
        }
        .nav-btn {
            padding: 10px;
            font-size: 16px;
        }
        .today {
            background-color: yellow;
            font-weight: bold;
        }
        .event-title {
            display: block;
            font-size: 10px; /* 일정 제목의 글자 크기 조정 */
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            max-width: 100%;
        }
        .event-title:hover {
            overflow: visible;
            white-space: normal;
            background-color: #f0f0f0;
            padding: 2px;
            border-radius: 4px;
            z-index: 1;
            position: absolute;
            box-shadow: 0px 2px 5px rgba(0,0,0,0.2);
        }
    </style>
</head>
<body>
<div style="display: flex; justify-content: space-between; align-items: center;">
    <%
        int year = (Integer) request.getAttribute("year");
        int month = (Integer) request.getAttribute("month");

        Calendar prevCalendar = new GregorianCalendar(year, month, 1);
        prevCalendar.add(Calendar.MONTH, -1);
        int prevYear = prevCalendar.get(Calendar.YEAR);
        int prevMonth = prevCalendar.get(Calendar.MONTH);

        Calendar nextCalendar = new GregorianCalendar(year, month, 1);
        nextCalendar.add(Calendar.MONTH, 1);
        int nextYear = nextCalendar.get(Calendar.YEAR);
        int nextMonth = nextCalendar.get(Calendar.MONTH);

        Calendar today = Calendar.getInstance();
        Map<Integer, List<String>> eventsByDay = (Map<Integer, List<String>>) request.getAttribute("eventsByDay");
    %>

    <a href="smcal?year=<%= prevYear %>&month=<%= prevMonth %>" class="nav-btn">이전 달</a>
    <h2><%= year %>년 <%= month + 1 %>월 달력</h2>
    <a href="smcal?year=<%= nextYear %>&month=<%= nextMonth %>" class="nav-btn">다음 달</a>
</div>

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
                    boolean isToday = (cal.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                            cal.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                            currentDay == today.get(Calendar.DAY_OF_MONTH));
                    String cellClass = isToday ? "today" : "";
        %>
        <td class="<%= cellClass %>">
            <%= currentDay %>
            <ul>
                <%
                    List<String> events = eventsByDay.get(currentDay);
                    if (events != null) {
                        for (String event : events) {
                            String[] eventDetails = event.split(" ");
                            if (eventDetails.length >= 3) { // 길이를 확인하여 안전하게 접근
                                String eventId = eventDetails[0]; // event ID
                                String eventUserId = eventDetails[1]; // user ID
                                String eventTitle = eventDetails[2]; // event title
                                String displayTitle = "[" + eventUserId + "] " + eventTitle;
                %>
                <li>
                    <a href="edit?id=<%= eventId %>">
                        <span class="event-title" title="<%= displayTitle %>"><%= displayTitle %></span>
                    </a>
                </li>
                <%
                            }
                        }
                    }
                %>

            </ul>
        </td>
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

<div style="margin-top: 20px;">
    <a href="write" class="nav-btn">작성 페이지로 이동</a>
    <a href="logout" class="nav-btn">로그아웃</a>
</div>
</body>
</html>
