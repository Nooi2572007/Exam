<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>
	<c:param name="content">
		<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>
		<form action="SubjectUpdateExecute.action" method="post">
			<div class="mb-3">
				<label class="form-label">科目コード</label>
				<%-- 科目コードは勝手に変えられると困るので、見るだけ（pタグ）にしておく --%>
				<p class="form-control-plaintext">${subject.cd}</p>
				<%-- ただし、裏側にはコードを送る必要があるので hidden（隠しパーツ）でこっそり忍ばせる --%>
				<input type="hidden" name="cd" value="${subject.cd}">
				<div class="mt-2 text-warning">${errors.get("1") }</div>
			</div>
			<div class="mb-4">
				<label class="form-label">科目名</label>
				<%-- value="${subject.name}" とすることで、最初から今の科目名を入力欄に入れておく --%>
				<input type="text" name="name" class="form-control" maxlength="20" value="${subject.name}" required autofocus>
			</div>
			<button type="submit" class="btn btn-primary">変更</button><br><br>
			<a href="SubjectList.action">戻る</a>
		</form>
	</c:param>
</c:import>