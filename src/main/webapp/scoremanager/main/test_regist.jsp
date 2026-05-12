<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>
    <c:param name="content">
        <section class="me=4">
            <h2 class="h3 mb-3 bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>
            
            <%-- 検索条件指定フォーム --%>
            <form action="TestRegist.action" method="get">
                <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
                    <div class="col-md-3">
                        <label class="form-label">入学年度</label>
                        <select name="f1" class="form-select">
                            <option value="0">--------</option>
                            <c:forEach var="year" items="${ent_years}">
                                <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <label class="form-label">クラス</label>
                        <select name="f2" class="form-select">
                            <option value="0">--------</option>
                            <c:forEach var="c_num" items="${class_nums}">
                                <option value="${c_num}" <c:if test="${c_num == f2}">selected</c:if>>${c_num}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">科目</label>
                        <select name="f3" class="form-select">
                            <option value="0">--------</option>
                            <c:forEach var="sub" items="${subjects}">
                                <option value="${sub.cd}" <c:if test="${sub.cd == f3}">selected</c:if>>${sub.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <label class="form-label">回数</label>
                        <select name="f4" class="form-select">
                        <option value="0">--------</option>
                            <c:forEach var="i" begin="1" end="10">
                                <option value="${i}" <c:if test="${i == f4}">selected</c:if>>${i}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-2 d-flex align-items-end">
                        <button type="submit" class="btn btn-secondary">検索</button>
                    </div>
                </div>
            </form>

            <%-- 検索結果がある場合のみ表示 --%>
            <c:if test="${!empty tests}">
                <div class="mt-4">
                    <%-- 選択された科目の情報を表示 --%>
                    <p class="mb-2">科目：${subject.name} （${num}回）</p>
                    
                    <%-- 登録実行用フォーム --%>
                    <form action="TestRegistExecute.action" method="post">
                        <table class="table table-hover">
                            <thead>
                                <tr class="table-light">
                                    <th>入学年度</th>
                                    <th>クラス</th>
                                    <th>学生番号</th>
                                    <th>氏名</th>
                                    <th>点数</th>
                                    <th>削除</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="test" items="${tests}">
                                    <tr>
                                        <td>${test.student.entYear}</td>
                                        <td>${test.student.classNum}</td>
                                        <td>
                                            ${test.student.studentNo}
                                            <%-- 登録時に必要な学生番号を隠しデータで送信 --%>
                                            <input type="hidden" name="student_no_set[]" value="${test.student.studentNo}">
                                        </td>
                                        <td>${test.student.studentName}</td>
                                        <td>
                                            <%-- 点数入力欄。未入力(0未満)の場合は空欄にする --%>
                                            <input type="number" name="point_set[]" 
                                                   value="${test.point >= 0 ? test.point : ''}" 
                                                   class="" min="0" max="100">
                                        </td>
                                        <td><a href="TestDelete.action?student_no=${test.student.studentNo}&cd=${f3}&num=${f4}">削除</a></td>
                                        
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        
                        <div class="mt-3">
                            <button type="submit" class="btn btn-secondary">登録して終了</button>
                        </div>
                        
                        <%-- 登録時に必要な科目コードと回数を隠しデータで送信 --%>
                        <input type="hidden" name="subject_cd" value="${f3}">
                        <input type="hidden" name="num" value="${f4}">
                    </form>
                </div>
            </c:if>
            
            <%-- 検索結果が空の場合のメッセージ --%>
            <c:if test="${empty tests && !empty f1}">
                <p class="mt-3">学生の情報が見つかりませんでした。</p>
            </c:if>
        </section>
    </c:param>
</c:import>