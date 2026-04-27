<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">科目変更 - 得点管理システム</c:param>
	<c:param name="content">
		
		<div class="container mt-4">
			<h3 class="mb-4">科目変更</h3>
			
			<form action="SubjectUpdateExecute.action" method="post">
				<div class="mb-3">
					<label class="form-label">科目コード</label>
					<%-- 科目コードは勝手に変えられると困るので、見るだけ（pタグ）にしておく --%>
					<p class="form-control-plaintext">${subject.cd}</p>
					<%-- ただし、裏側にはコードを送る必要があるので hidden（隠しパーツ）でこっそり忍ばせる --%>
					<input type="hidden" name="cd" value="${subject.cd}">
				</div>
				<div class="mb-4">
					<label class="form-label">科目名</label>
					<%-- value="${subject.name}" とすることで、最初から今の科目名を入力欄に入れておく --%>
					<input type="text" name="name" class="form-control" maxlength="20" value="${subject.name}" required autofocus>
				</div>
				
				<button type="submit" class="btn btn-primary">変更して次へ</button>
				<a href="SubjectList.action" class="btn btn-secondary ms-2">戻る</a>
			</form>
			
		</div>
		
	</c:param>
</c:import>