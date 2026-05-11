<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">成績参照</c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績一覧（学生）</h2>

            <div class="border mx-3 mb-3 p-3 rounded">
                <form method="get" action="TestListSubjectExecute.action" class="mb-3">
                    <div class="row align-items-end">
                        <div class="col-auto text-secondary small" style="width:100px;">科目情報</div>
                        <div class="col-2">
                            <label class="form-label small mb-1">入学年度</label>
                            <select class="form-select form-select-sm" name="f1">
                                <option value="0">--------</option>
                                <c:forEach var="year" items="${ent_year_set}">
                                    <option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-2">
                            <label class="form-label small mb-1">クラス</label>
                            <select class="form-select form-select-sm" name="f2">
                                <option value="0">--------</option>
                                <c:forEach var="num" items="${class_num_set}">
                                    <option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-4">
                            <label class="form-label small mb-1">科目</label>
                            <select class="form-select form-select-sm" name="f3">
                                <option value="0">--------</option>
                                <c:forEach var="sub" items="${subjects}">
                                    <option value="${sub.cd}" <c:if test="${sub.cd==f3}">selected</c:if>>${sub.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-2">
                            <button class="btn btn-secondary btn-sm w-100">検索</button>
                        </div>
                    </div>
                </form>
                <hr>
                <form method="get" action="TestListStudentExecute.action">
                    <div class="row align-items-center">
                        <div class="col-auto text-secondary small" style="width:100px;">学生情報</div>
                        <div class="col-auto small">学生番号</div>
                        <div class="col-6">
                            <input type="text" name="f5" class="form-control form-control-sm" value="${f5}" placeholder="学生番号を入力してください" required>
                        </div>
                        <div class="col-2">
                            <button class="btn btn-secondary btn-sm w-100">検索</button>
                        </div>
                    </div>
                </form>
            </div>

            <div class="mx-3 mt-4">
                <c:if test="${not empty selected_student}">
                    <div class="mb-2 fw-bold">氏名：${selected_student.studentName} (${selected_student.studentNo})</div>
                    <table class="table table-hover">
                        <thead>
                            <tr class="border-bottom"><th>科目名</th><th>科目コード</th><th>回数</th><th>点数</th></tr>
                        </thead>
                        <tbody>
                            <c:forEach var="ts" items="${student_tests}">
                                <tr><td>${ts.subjectName}</td><td>${ts.subjectCd}</td><td>${ts.num}</td><td>${ts.point}</td></tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${not empty student_not_found}">
                    <div class="text-danger small">${student_not_found}</div>
                </c:if>
            </div>
        </section>
    </c:param>
</c:import>