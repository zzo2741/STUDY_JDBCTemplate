<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<script>
	$(function() {

	});
</script>
</head>
<body>
	<div class="container">
		<h2>비회원제 게시판 - 상세보기</h2>

		<table border=1 width=90%>
			<colgroup>
				<col width="20%" />
				<col width="30%" />
				<col width="20%" />
				<col width="30%" />
			</colgroup>
			<tr>
				<th>No</th>
				<td>${viewRow.idx }</td>
				<th>작성자</th>
				<td>${viewRow.name }</td>
			</tr>
			<tr>
				<th>작성일</th>
				<td>${viewRow.postdate }</td>
				<th>조회수</th>
				<td>${viewRow.hits }</td>
			</tr>
			<tr>
				<th>제목</th>
				<td colspan="3">${viewRow.title }</td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="3" style="height: 150px;">${viewRow.contents }</td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<button type="button" onclick="location.href='./reply.do?idx=${viewRow.idx}&nowPage=${nowPage }';">답변글달기</button>

					<button type="button" onclick="location.href='./password.do?idx=${viewRow.idx}&mode=edit&nowPage=${nowPage }';">수정하기</button>

					<button type="button" onclick="location.href='./password.do?idx=${viewRow.idx}&mode=delete&nowPage=${nowPage }';">삭제하기</button>

					<button type="button" onclick="location.href='./list.do?nowPage=${param.nowPage}';">리스트보기</button>
				</td>
			</tr>
		</table>
	</div>

</body>
</html>