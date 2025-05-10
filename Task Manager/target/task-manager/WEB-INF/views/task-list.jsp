<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <title>Список задач</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
                <style>
                    .task-status {
                        padding: 5px 10px;
                        border-radius: 4px;
                        font-weight: bold;
                    }

                    .status-in-progress {
                        background-color: #ffd700;
                        color: #000;
                    }

                    .status-completed {
                        background-color: #90EE90;
                        color: #000;
                    }
                </style>
            </head>

            <body>
                <div class="container mt-4">
                    <h2>Список задач</h2>

                    <div class="mb-3">
                        <a href="${pageContext.request.contextPath}/tasks/new" class="btn btn-primary">Создать новую
                            задачу</a>
                    </div>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>

                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Название</th>
                                <th>Описание</th>
                                <th>Статус</th>
                                <th>Срок выполнения</th>
                                <th>Действия</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${tasks}" var="task">
                                <tr>
                                    <td>${task.id}</td>
                                    <td>${task.title}</td>
                                    <td>${task.description}</td>
                                    <td>
                                        <span class="task-status status-${task.status.name().toLowerCase()}">
                                            ${task.status.name()}
                                        </span>
                                    </td>
                                    <td>
                                        <fmt:parseDate value="${task.deadline}" pattern="yyyy-MM-dd" var="parsedDate"
                                            type="date" />
                                        <fmt:formatDate value="${parsedDate}" pattern="dd.MM.yyyy" />
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/tasks/edit/${task.id}"
                                            class="btn btn-sm btn-warning">Редактировать</a>
                                        <a href="${pageContext.request.contextPath}/tasks/delete/${task.id}"
                                            class="btn btn-sm btn-danger"
                                            onclick="return confirm('Вы уверены, что хотите удалить эту задачу?')">Удалить</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
            </body>

            </html>