<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">科目登録 - 得点管理システム</c:param>
	<c:param name="content">
		
		<div class="container mt-4">
			<h3 class="mb-4">科目登録</h3>
			
			<%-- action先を「登録実行処理」に向けておきます --%>
			<form action="SubjectCreateExecute.action" method="post">
				<div class="mb-3">
					<label class="form-label">科目コード</label>
					<%-- 画面設計書に準じて、もし3文字固定なら maxlength="3" などを付けます --%>
					<input type="text" name="cd" class="form-control" maxlength="3" required autofocus>
				</div>
				<div class="mb-4">
					<label class="form-label">科目名</label>
					<input type="text" name="name" class="form-control" maxlength="20" required>
				</div>
				
				<button type="submit" class="btn btn-primary">登録して次へ</button>
				<a href="SubjectList.action" class="btn btn-secondary ms-2">戻る</a>
			</form>
			
		</div>
		
	</c:param>
</c:import>