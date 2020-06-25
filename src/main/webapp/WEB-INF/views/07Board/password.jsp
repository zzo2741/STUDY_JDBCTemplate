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
		s
	});
</script>
</head>
<body>
	<script type="text/javascript">
		function checkValidate(f) {
			if (f.pass.value == "") {
				alert("패스워드를 입력하세요");
				f.pass.focus();
				return false;
			}
		}
	</script>
	<div class="container">
		<h2>회원제 게시판 - 패스워드검증폼</h2>

		<!-- 패스워드 검증에 실패했을때 에러메세지 출력용 -->
		<span style="color: red; font-size: 1.8em;"> ${isCorrMsg } </span>

		<form name="writeFrm" method="post" action="./passwordAction.do" onsubmit="return checkValidate(this);">

			<input type="hid den" name="idx" value="${idx }" />
			<input type="hid den" name="mode" value="${param.mode }" />
			<input type="hid den" name="nowPage" value="${param.nowPage }" />

			<table border=1 width=800>
				<colgroup>
					<col width="25%" />
					<col width="*" />
				</colgroup>

				<tr>
					<td>패스워드</td>
					<td>
						<input type="password" name="pass" style="width: 30%;" />
					</td>
				</tr>

				<tr>
					<td colspan="2" align="center">
						<button type="submit">작성완료</button>
						<button type="reset">RESET</button>
						<button type="button" onclick="location.href='./list.do?nowPage=${param.nowPage}';">리스트바로가기</button>
					</td>
				</tr>
			</table>
		</form>
	</div>

</body>
</html>