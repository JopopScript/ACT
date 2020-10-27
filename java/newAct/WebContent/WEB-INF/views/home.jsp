<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>

<!DOCTYPE html>

<!-- 폰트
	src: url("resources/fonts/NanumSquareRoundOTFB.otf");
			src: url("resources/fonts/NanumSquareRoundOTFEB.otf");
			src: url("resources/fonts/NanumSquareRoundR.ttf"); -->
<html>
<head>

<META http-equiv="refresh" content="${timeDelay} URL=http://localhost:8800/newAct/">
<!-- Polyteck Zone의 무선 와이파이를 잡아서 입력했습니다. ctc IP로 바꿔주세요 -->
<title>체온측정 키오스크</title>
<style>
@font-face {
	font-family: "MyFont";
}

h1, h2, h3, h4 {
	font-family: MyFont;
	text-align: center;
	font-size: 300;
}

.red {
	color: red;
	font-size: 300;
	text-align: center;
}

.green1 {
	color: #008000;
	font-size: 300;
	text-align: center;
}

.green2 {
	color: #00cc00;
	font-size: 300;
	text-align: center;
}

#main {
	text-align: center;
}

.title {
	margin-left: auto;
	margin-right: auto;
}
</style>
<!--  
	<script language="JavaScript">
		function timedRefresh(time) {
			setTimeout("location.reload(true);", time);
		}
	</script>
	 -->
</head>
<!-- 
<body onload="timedRefresh(1500)">
 -->
<body>
	<div style="text-align: center; margin: 0 auto;">
	<img src="resources/logo(2).png" width=300 alt="폴리텍 대학교 로고" class=title>
	<h2 id=main class=title>비접촉 검진 시스템</h2>
	</div>

	<!--  <h1 style="text-align:center; font-family:MyFont; font-size:2em; color:blue; ">한국폴리텍대학 분당융합기술교육원</h1> -->
	<form method="GET" action="join_result.do">
		<table>
			<!-- 코드 작성중이므로 테스트 위해  type을 text로 함  -->
			<tr>
				<td></td>
				<td><input type="hidden" name="name"></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="hidden" name="school_id"></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="hidden" name="temperature"></td>
			</tr>
			<!--<tr><td>temperature</td><td><input type="text" name="temperature" ></td></tr>   -->
		</table>
		<!--  <input type="submit" value="ok">-->

		${result}
</body>

</html>