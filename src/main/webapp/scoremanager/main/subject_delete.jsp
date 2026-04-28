<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">科目削除確認 - 得点管理システム</c:param>
	<c:param name="content">
		
		<div class="container mt-4">
			<h3 class="mb-4 text-danger">科目削除確認</h3>
			
			<div class="alert alert-warning">
				以下の科目を削除しますか？<br>
				<strong>※この操作は元に戻せません。</strong>
			</div>
			
			<form action="SubjectDeleteExecute.action" method="post">
				<%-- 削除実行係に科目コードを伝えるための隠しパーツ --%>
				<input type="hidden" name="cd" value="${subject.cd}">
				
				<table class="table table-bordered mb-4">
					<tr>
						<th class="table-light" style="width: 200px;">科目コード</th>
						<td>${subject.cd}</td>
					</tr>
					<tr>
						<th class="table-light">科目名</th>
						<td>${subject.name}</td>
					</tr>
				</table>
				
				<button type="submit" class="btn btn-danger">削除する</button>
				<a href="SubjectList.action" class="btn btn-secondary ms-2">キャンセルして戻る</a>
			</form>
			
		</div>
		
	</c:param>
</c:import>