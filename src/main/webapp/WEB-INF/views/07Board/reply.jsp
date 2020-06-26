<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		<h2>비회원제 게시판 - 답변글쓰기 폼</h2>
		<form action="./replyAction.do" name="writeFrm" method="post" onsubmit="return checkValidate(this)">
			<!-- 답변글 처리를 위해서는 원본글에 대한 idx값과 추가적으로 group, step, indent값이 필요하다. -->
			<input type="hid den" name="idx" value="${replyRow.idx }" />
			<input type="hid den" name="nowPage" value="${param.nowPage }" />
			<input type="hid den" name="bgroup" value="${replyRow.bgroup }" />
			<input type="hid den" name="bstep" value="${replyRow.bstep }" />
			<input type="hid den" name="bindent" value="${replyRow.bindent }" />
			<table border=1 width=800>
				<colgroup>
					<col width="25%" />
					<col width="*" />
				</colgroup>
				<tr>
					<td>작성자</td>
					<td>
						<input type="text" name="name" style="width: 50%;" />
					</td>
				</tr>
				<tr>
					<td>패스워드</td>
					<td>
						<input type="password" name="pass" style="width: 30%;" />
					</td>
				</tr>
				<tr>
					<td>제목</td>
					<td>
						<input type="text" name="title" style="width: 90%;" value="${replyRow.title }" />
					</td>
				</tr>
				<tr>
					<td>내용</td>
					<td>
						<textarea name="contents" style="width: 90%; height: 200px;">${replyRow.contents }</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<button type="submit">작성완료</button>
						<button type="reset">RESET</button>
						<button type="button" onclick="location.href='./list.do';">리스트바로가기</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>