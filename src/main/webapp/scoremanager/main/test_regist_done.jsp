<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- c:import を使って base.jsp を読み込み、その中にコンテンツを流し込みます --%>
<c:import url="../../common/base.jsp">
    <c:param name="title">得点入力完了</c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal">成績管理</h2>

            <div class="alert alert-success mt-4" role="alert">
                <h4 class="alert-heading">登録完了</h4>
                <p>成績の登録・更新が正常に完了しました。</p>
            </div>

            <div class="mt-5">
                <a href="TestRegist.action" class="btn btn-primary me-3">続けて登録する</a>
            </div>
        </section>
    </c:param>
</c:import>