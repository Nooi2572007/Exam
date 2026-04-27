<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">科目登録完了 - 得点管理システム</c:param>
	<c:param name="content">
		
		<div class="container mt-5 text-center">
			<h3 class="mb-4 text-success">科目登録完了</h3>
			<p class="fs-5">新しい科目の登録が完了しました。</p>
			
			<div class="mt-5">
				<a href="SubjectList.action" class="btn btn-primary btn-lg">科目一覧へ戻る</a>
			</div>
		</div>
		
	</c:param>
</c:import>