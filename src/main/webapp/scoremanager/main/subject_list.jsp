<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">科目管理 - 得点管理システム</c:param>
	<c:param name="content">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報</h2>
			<div class="my-2 text-end px-4">
				<a href="SubjectCreate.action">新規登録</a>
			</div>
			<div class="table-responsive">
				<table class="table">
					<thead>
						<tr>
							<th scope="col">科目コード</th>
							<th scope="col">科目名</th>
							<th></th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<%-- Actionから送られてきた subjects の中身を1行ずつ取り出して表示するループ --%>
						<c:forEach var="subject" items="${subjects}">
							<tr>
								<td class="align-middle">${subject.cd}</td>
								<td class="align-middle">${subject.name}</td>
								<td class="text-center align-middle">
									<a href="SubjectUpdate.action?cd=${subject.cd}">変更</a>
								</td>
								<td class="text-center align-middle">
									<a href="SubjectDelete.action?cd=${subject.cd}">削除</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
	</c:param>
</c:import>