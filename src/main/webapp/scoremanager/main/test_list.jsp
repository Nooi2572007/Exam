<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">成績参照 - 得点管理システム</c:param>
	<c:param name="content">
		
		<div class="container mt-4">
			<h3 class="mb-4">成績一覧</h3>
			
			<div class="card mb-4 shadow-sm">
				<div class="card-body">
					<form action="TestList.action" method="get">
						<div class="row g-3 align-items-end mb-3">
							<div class="col-md-3">
								<label class="form-label text-muted small">入学年度</label>
								<select name="ent_year" class="form-select">
									<option value="0">--------</option>
									<c:forEach var="year" items="${ent_year_list}">
										<option value="${year}" <c:if test="${year == param.ent_year}">selected</c:if>>${year}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-md-3">
								<label class="form-label text-muted small">クラス</label>
								<select name="class_num" class="form-select">
									<option value="0">--------</option>
									<c:forEach var="num" items="${class_num_list}">
										<option value="${num}" <c:if test="${num == param.class_num}">selected</c:if>>${num}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-md-4">
								<label class="form-label text-muted small">科目</label>
								<select name="subject_cd" class="form-select">
									<option value="0">--------</option>
									<c:forEach var="sub" items="${subjects}">
										<option value="${sub.cd}" <c:if test="${sub.cd == param.subject_cd}">selected</c:if>>${sub.name}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-md-2">
								<button type="submit" name="search_subject" class="btn btn-secondary w-100">検索</button>
							</div>
						</div>

						<hr class="my-3">

						<div class="row g-3 align-items-end">
							<div class="col-md-10">
								<label class="form-label text-muted small">学籍番号</label>
								<input type="text" name="student_no" class="form-control" placeholder="学生番号を入力してください" value="${param.student_no}">
							</div>
							<div class="col-md-2">
								<button type="submit" name="search_student" class="btn btn-secondary w-100">検索</button>
							</div>
						</div>
					</form>
				</div>
			</div>

			<c:if test="${not empty param.student_no}">
				<div class="mb-3">
					<%-- 検索した場合は、データが有っても無くても名前を表示する --%>
					<div>氏名：${student_name}（${target_no}）</div>
				</div>
				
				<%-- ① 成績データがある場合：テーブルを表示 --%>
				<c:if test="${not empty scores}">
					<table class="table table-hover table-bordered border-secondary">
						<thead class="table-light">
							<tr>
								<th>科目名</th>
								<th>科目コード</th>
								<th>回数</th>
								<th>点数</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="score" items="${scores}">
								<tr>
									<td class="text-muted">（DAO完成待ち）</td>
									<td>${score.subjectCd}</td>
									<td>${score.no}</td>
									<td>${score.point}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
				
				<%-- ② 成績データがない場合：設計書通りのエラーメッセージを表示 --%>
				<c:if test="${empty scores}">
					<div>成績情報が存在しませんでした</div>
				</c:if>
			</c:if>

		</div> <%-- containerの閉じタグ --%>
	</c:param> <%-- ★これを忘れていました！「content」パラメータの終了 --%>
</c:import> <%-- base.jspの読み込み終了 --%>