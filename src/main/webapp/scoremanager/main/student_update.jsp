<%-- 学生変更JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp" >
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section>
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>
			<form action="StudentUpdateExecute.action" method="post">
				<div class="col-15">
					<label for="ent_year">入学年度</label><br>
					<input class="form-control" type="text" id="ent_year" name="ent_year" value="${student.entYear }" readonly/>
				</div>
				<div class="col-">
					<label for="no">学生番号</label><br>
					<input class="form-control" type="text" id="no" name="no" value="${student.studentNo }" readonly/>
				</div>
				<div>
					<label for="name">氏名</label><br>
					<input class="form-control" type="text" id="name" name="name" value="${student.studentName }" required maxlength="30" placeholder="氏名を入力してください" />
				</div>
				<div class="mx-auto py-2">
					<label for="class_num">クラス</label>
					<select class="form-select" id="class_num" name="class_num">
						<c:forEach var="num" items="${class_num_set }">
							<%-- 現在のnumと選択されていたclass_numが一致していた場合selectedを追記 --%>
							<option value="${num }" <c:if test="${num == student.classNum}">selected</c:if>>${num }</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-1 form-check text-center">
						<label class="form-check-label" for="student-f3-check">
							<%-- パラメーターf3が存在している場合checkedを追記 --%>
							<input class="form-check-input" type="checkbox"
							id="student-f3-check" name="is_attend" value="t"
							<c:if test="${student.attend}">checked</c:if> />在学中
						</label>
					</div>
				<div class="mx-auto py-2">
					<button type="submit" class="btn btn-primary" id="create-button" name="end">変更</button>
				</div>
			</form>
			<a href="StudentList.action">戻る</a>
		</section>
	</c:param>
</c:import>