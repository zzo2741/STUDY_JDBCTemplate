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

		<h2>비회원제 게시판 - 글수정 폼</h2>
		<form name="writeFrm" method="post" action="./editAction.do" onsubmit="return checkValidate(this);">
			<input type="hid den" name="idx" value="${viewRow.idx }" />
			<input type="hid den" name="nowPage" value="${param.nowPage }" />
			<input type="hid den" name="pass" value="${viewRow.pass }" />
			<table border=1 width=800>
				<colgroup>
					<col width="25%" />
					<col width="*" />
				</colgroup>
				<tr>
					<td>작성자</td>
					<td>
						<input type="text" name="name" style="width: 50%;" value="${viewRow.name }" />
					</td>
				</tr>
				<!-- <tr>
					<td>패스워드</td>
					<td>
						<input type="password" name="pass" style="width: 30%;" />
					</td>
				</tr> -->
				<tr>
					<td>제목</td>
					<td>
						<input type="text" name="title" style="width: 90%;" value="${viewRow.title }" />
					</td>
				</tr>
				<tr>
					<td>내용</td>
					<td>
						<textarea name="contents" style="width: 90%; height: 200px;">${viewRow.contents }</textarea>
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
	<script type="text/javascript">
		function checkValidate(f) {
			if (f.name.value == "") {
				alert("이름을 입력하세요");
				f.name.focus();
				return false;
			}
			/* if (f.pass.value == "") {
				alert("패스워드를 입력하세요");
				f.pass.focus();
				return false;
			} */
			if (f.title.value == "") {
				alert("제목을 입력하세요");
				f.title.focus();
				return false;
			}
			if (f.contents.value == "") {
				alert("내용을 입력하세요");
				f.contents.focus();
				return false;
			}
		}
	</script>
</body>
</html>