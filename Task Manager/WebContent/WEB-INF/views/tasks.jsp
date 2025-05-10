<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <title>Task Manager</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        margin: 0;
                        padding: 20px;
                        background-color: #f5f5f5;
                    }

                    .container {
                        max-width: 1200px;
                        margin: 0 auto;
                        background-color: white;
                        padding: 20px;
                        border-radius: 5px;
                        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                    }

                    h1 {
                        color: #333;
                        margin-bottom: 20px;
                    }

                    .task-form {
                        margin-bottom: 30px;
                        padding: 20px;
                        background-color: #f9f9f9;
                        border-radius: 5px;
                    }

                    .form-group {
                        margin-bottom: 15px;
                    }

                    label {
                        display: block;
                        margin-bottom: 5px;
                        color: #666;
                    }

                    input[type="text"],
                    input[type="date"],
                    textarea {
                        width: 100%;
                        padding: 8px;
                        border: 1px solid #ddd;
                        border-radius: 4px;
                        box-sizing: border-box;
                    }

                    button {
                        background-color: #4CAF50;
                        color: white;
                        padding: 10px 20px;
                        border: none;
                        border-radius: 4px;
                        cursor: pointer;
                    }

                    button:hover {
                        background-color: #45a049;
                    }

                    .task-list {
                        list-style: none;
                        padding: 0;
                    }

                    .task-item {
                        padding: 15px;
                        border-bottom: 1px solid #eee;
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                    }

                    .task-item:last-child {
                        border-bottom: none;
                    }

                    .task-actions {
                        display: flex;
                        gap: 10px;
                    }

                    .btn-edit {
                        background-color: #2196F3;
                    }

                    .btn-delete {
                        background-color: #f44336;
                    }

                    .status-in-progress {
                        color: #ff9800;
                    }

                    .status-completed {
                        color: #4CAF50;
                    }
                </style>
            </head>

            <body>
                <div class="container">
                    <h1>Task Manager</h1>

                    <div class="task-form">
                        <h2>Create New Task</h2>
                        <form action="${pageContext.request.contextPath}/tasks" method="post">
                            <div class="form-group">
                                <label for="title">Title:</label>
                                <input type="text" id="title" name="title" required>
                            </div>
                            <div class="form-group">
                                <label for="description">Description:</label>
                                <textarea id="description" name="description" rows="3"></textarea>
                            </div>
                            <div class="form-group">
                                <label for="deadline">Deadline:</label>
                                <input type="date" id="deadline" name="deadline" required>
                            </div>
                            <button type="submit">Create Task</button>
                        </form>
                    </div>

                    <h2>Task List</h2>
                    <ul class="task-list">
                        <c:forEach items="${tasks}" var="task">
                            <li class="task-item">
                                <div class="task-info">
                                    <h3>${task.title}</h3>
                                    <p>${task.description}</p>
                                    <p>Deadline:
                                        <fmt:formatDate value="${task.deadline}" pattern="yyyy-MM-dd" />
                                    </p>
                                    <p class="status-${task.status.name().toLowerCase()}">Status: ${task.status}</p>
                                </div>
                                <div class="task-actions">
                                    <a href="${pageContext.request.contextPath}/tasks/edit/${task.id}">
                                        <button class="btn-edit">Edit</button>
                                    </a>
                                    <form action="${pageContext.request.contextPath}/tasks/delete/${task.id}"
                                        method="post" style="display: inline;">
                                        <button type="submit" class="btn-delete">Delete</button>
                                    </form>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </body>

            </html>