<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts"></c:param>
	
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">メニュー</h2>
			
			<div class="row text-center px-4 fs-5 my-5">
				
				<div class="col d-flex align-items-center justify-content-center mx-2 rounded shadow"
					style="height: 10rem; background-color: #fcc; padding: 15px 0;">
					<a href="StudentList.action">学生管理</a>
				</div>
				
				<div class="col d-flex align-items-center justify-content-center mx-2 rounded shadow"
					style="height: 10rem; background-color: #dfd; padding: 15px 0;">
					<div>
						<div class="mb-2 fw-bold">成績管理</div>
						<div class="mb-2">
							<a href="TestRegist.action">成績登録</a>
						</div>
						<div>
							<a href="TestList.action">成績参照</a>
						</div>
					</div>
				</div>
				
				<div class="col d-flex align-items-center justify-content-center mx-2 rounded shadow"
					style="height: 10rem; background-color: #ccf; padding: 15px 0;">
					<a href="SubjectList.action">科目管理</a>
				</div>
				
				<div class="col d-flex align-items-center justify-content-center mx-2 rounded shadow"
					style="height: 10rem; background-color: #ffd; padding: 15px 0;">
					<a href="ClassList.action">クラス管理</a>
				</div>
				
			</div>
		</section>
	</c:param>
</c:import>