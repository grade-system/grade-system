<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% request.setAttribute("pageTitle", "班级编辑"); request.setAttribute("active", "class"); %>
<jsp:include page="/common/header.jsp" />

<div class="card">
    <h2>${cls == null ? '新增班级' : '编辑班级'}</h2>
    <form action="${pageContext.request.contextPath}/teacher/class" method="post" style="max-width: 500px;">
        <input type="hidden" name="id" value="${cls.id}">
        <div class="form-group">
            <label>班级名称</label>
            <input type="text" name="className" value="${cls.className}" required placeholder="如：计算机1班">
        </div>
        <div class="form-group">
            <label>年级</label>
            <input type="text" name="grade" value="${cls.grade}" required placeholder="如：2023级">
        </div>
        <div style="margin-top: 20px;">
            <button type="submit" class="btn btn-success">保存</button>
            <a href="${pageContext.request.contextPath}/teacher/class" class="btn btn-danger">取消</a>
        </div>
    </form>
</div>

<jsp:include page="/common/footer.jsp" />
