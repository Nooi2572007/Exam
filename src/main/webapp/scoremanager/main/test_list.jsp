<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">成績参照</c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>
            

            <div class="border mx-3 mb-3 p-3 rounded">
                <%-- 科目検索フォーム --%>
                <form method="get" action="TestListSubjectExecute.action" class="mb-3">
                    <div class="row align-items-end">
                        <div class="col-auto text-secondary small" style="width:100px;">科目情報</div>
                        <div class="col-2">
                            <label class="form-label small mb-1">入学年度</label>
                            <select class="form-select form-select-sm" name="f1">
                                <option value="0">--------</option>
                                <c:forEach var="year" items="${ent_year_set}">
                                    <option value="${year}">${year}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-2">
                            <label class="form-label small mb-1">クラス</label>
                            <select class="form-select form-select-sm" name="f2">
                                <option value="0">--------</option>
                                <c:forEach var="num" items="${class_num_set}">
                                    <option value="${num}">${num}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-4">
                            <label class="form-label small mb-1">科目</label>
                            <select class="form-select form-select-sm" name="f3">
                                <option value="0">--------</option>
                                <c:forEach var="sub" items="${subjects}">
                                    <option value="${sub.cd}">${sub.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-2">
                            <button class="btn btn-secondary btn-sm w-50">検索</button>
                        </div>
                    </div>
                </form>

                <hr>

                <%-- 学生検索フォーム --%>
                <form method="get" action="TestListStudentExecute.action">
                    <div class="row align-items-center">
                        <div class="col-auto text-secondary small" style="width:100px;">学生情報</div>
                        
                        <div class="col-6">
                        <div class="col-auto small">学生番号</div>
                            <input type="text" name="f5" class="form-control" placeholder="学生番号を入力してください" required>
                        </div>
                        <div class="col-2">
                            <button class="btn btn-secondary btn-sm w-50">検索</button>
                        </div>
                    </div>
                </form>
            </div>

            <div class="text-info small">
                科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
            </div>
        </section>
    </c:param>
</c:import>