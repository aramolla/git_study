<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>일정 작성</title>
</head>
<body>
<h2>일정 작성 페이지</h2>
<form action="write" method="post">
    <label for="title">일정:</label>
    <input type="text" id="title" name="title" required><br><br>

    <label for="date">날짜:</label>
    <input type="date" id="date" name="date" required><br><br>

    <label for="startTime">시작 시간:</label>
    <input type="time" id="startTime" name="startTime" required><br><br>

    <label for="endTime">종료 시간:</label>
    <input type="time" id="endTime" name="endTime" required><br><br>

    <button type="submit">완료</button>
</form>
</body>
</html>
