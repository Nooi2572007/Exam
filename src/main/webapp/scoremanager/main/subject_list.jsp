<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">科目管理 - 得点管理システム</c:param>
	<c:param name="content">
		
		<div class="container mt-4">
			<h3 class="mb-4">科目管理</h3>
			
			<div class="table-responsive">
				<table class="table table-hover table-bordered table-striped">
					<thead class="table-light">
						<tr>
							<th scope="col">科目コード</th>
							<th scope="col">科目名</th>
							<th scope="col" class="text-center">変更</th>
							<th scope="col" class="text-center">削除</th>
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
			
			<div class="mt-3">
				<a href="SubjectCreate.action">新しい科目を登録する</a>
			</div>
			
		</div>
		
	</c:param>
</c:import>