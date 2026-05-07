<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>
	<c:param name="content">
		<div class="container mt-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2>
			
			<%-- action先を「登録実行処理」に向けておきます --%>
			<form action="SubjectCreateExecute.action" method="post">
				
				<div class="mb-3">
					<label class="form-label">科目コード</label>
					<%-- 画面設計書に準じて、もし3文字固定なら maxlength="3" などを付けます --%>
					<input type="text" name="cd" class="form-control" maxlength="3" required autofocus>
					<div class="mt-2 text-warning">${errors.get("1") }</div>
				</div>
				
				<div class="mb-4">
					<label class="form-label">科目名</label>
					<input type="text" name="name" class="form-control" maxlength="20" required>
				</div>
				
				<button type="submit" class="btn btn-primary">登録</button><br><br>
				<a href="SubjectList.action">戻る</a>
			</form>
			
		</div>
		
	</c:param>
</c:import>