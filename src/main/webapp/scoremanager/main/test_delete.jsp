<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>
	<c:param name="content">
		<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績情報削除</h2>
		<p>${test.student.studentName}さんの${test.subject.name}の${test.no}回目の成績を削除してもよろしいでしょうか？</p>
		<form action="TestDeleteExecute.action" method="post">
			<input type="hidden" name="student_no" value="${test.student.studentNo}">
    		<input type="hidden" name="cd" value="${test.subject.cd}">
    		<input type="hidden" name="num" value="${test.no}">
			<button type="submit" class="btn btn-danger">削除</button>
		</form>
		<br>
		<br>
		<br>
		<a href="TestRegist.action">戻る</a>
	</c:param>
</c:import>