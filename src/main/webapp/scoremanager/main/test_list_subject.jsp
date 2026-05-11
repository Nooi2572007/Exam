<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">成績参照</c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績一覧（科目）</h2>

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
                    <%-- 画像① 条件不足エラー --%>
                    <c:if test="${not empty subject_error}">
                        <div class="text-danger small mt-2" style="padding-left:100px;">${subject_error}</div>
                    </c:if>
                </form>
                <hr>
                <form method="get" action="TestListStudentExecute.action">
                    <div class="row align-items-center">
                        <div class="col-auto text-secondary small" style="width:100px;">学生情報</div>
                        <div class="col-auto small">学生番号</div>
                        <div class="col-6">
                            <input type="text" name="f5" class="form-control form-control-sm" value="${f5}" placeholder="学生番号を入力してください">
                        </div>
                        <div class="col-2">
                            <button class="btn btn-secondary btn-sm w-100">検索</button>
                        </div>
                    </div>
                </form>
            </div>

            <%-- 画像② 検索条件はOKだが結果0件 --%>
            <c:if test="${not empty subject_no_result}">
                <div class="mx-3 small">${subject_no_result}</div>
            </c:if>

            <div class="mx-3 mt-4">
                <c:if test="${not empty selected_subject}">
                    <div class="mb-2 fw-bold">科目：${selected_subject.name}</div>
                    <table class="table table-hover">
                        <thead>
                            <tr class="border-bottom">
                                <th>入学年度</th><th>クラス</th><th>学生番号</th><th>氏名</th><th>1回</th><th>2回</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="ts" items="${subject_tests}">
                                <tr>
                                    <td>${ts.entYear}</td><td>${ts.classNum}</td><td>${ts.studentNo}</td><td>${ts.studentName}</td>
                                    <td>${ts.points['1'] != null ? ts.points['1'] : "-"}</td>
                                    <td>${ts.points['2'] != null ? ts.points['2'] : "-"}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div>
        </section>
    </c:param>
</c:import>