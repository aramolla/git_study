<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>일정 수정</title>
</head>
<body>
<h2>일정 수정 페이지</h2>
<form action="edit" method="post">
    <input type="hidden" name="id" value="${id}">

    <label for="title">일정 제목:</label>
    <input type="text" id="title" name="title" value="${title}" required><br><br>

    <label for="date">날짜:</label>
    <input type="date" id="date" name="date" value="${event_date}" required><br><br>

    <label for="startTime">시작 시간:</label>
    <input type="time" id="startTime" name="startTime" value="${start_time}" required><br><br>

    <label for="endTime">종료 시간:</label>
    <input type="time" id="endTime" name="endTime" value="${end_time}" required><br><br>

    <button type="submit" name="action" value="update">완료</button>
    <button type="submit" name="action" value="delete">삭제</button>
</form>
</body>
</html>
