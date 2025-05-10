<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <title>Edit Task</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        margin: 0;
                        padding: 20px;
                        background-color: #f5f5f5;
                    }

                    .container {
                        max-width: 800px;
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
                    textarea,
                    select {
                        width: 100%;
                        padding: 8px;
                        border: 1px solid #ddd;
                        border-radius: 4px;
                        box-sizing: border-box;
                    }

                    .button-group {
                        margin-top: 20px;
                        display: flex;
                        gap: 10px;
                    }

                    button {
                        padding: 10px 20px;
                        border: none;
                        border-radius: 4px;
                        cursor: pointer;
                        color: white;
                    }

                    .btn-save {
                        background-color: #4CAF50;
                    }

                    .btn-cancel {
                        background-color: #f44336;
                    }

                    button:hover {
                        opacity: 0.9;
                    }
                </style>
            </head>

            <body>
                <div class="container">
                    <h1>Edit Task</h1>

                    <form action="${pageContext.request.contextPath}/tasks/update/${task.id}" method="post">
                        <div class="form-group">
                            <label for="title">Title:</label>
                            <input type="text" id="title" name="title" value="${task.title}" required>
                        </div>

                        <div class="form-group">
                            <label for="description">Description:</label>
                            <textarea id="description" name="description" rows="3">${task.description}</textarea>
                        </div>

                        <div class="form-group">
                            <label for="deadline">Deadline:</label>
                            <input type="date" id="deadline" name="deadline" value="<fmt:formatDate value="
                                ${task.deadline}" pattern="yyyy-MM-dd" />" required>
                        </div>

                        <div class="form-group">
                            <label for="status">Status:</label>
                            <select id="status" name="status">
                                <option value="IN_PROGRESS" ${task.status=='IN_PROGRESS' ? 'selected' : '' }>In Progress
                                </option>
                                <option value="COMPLETED" ${task.status=='COMPLETED' ? 'selected' : '' }>Completed
                                </option>
                            </select>
                        </div>

                        <div class="button-group">
                            <button type="submit" class="btn-save">Save Changes</button>
                            <a href="${pageContext.request.contextPath}/tasks">
                                <button type="button" class="btn-cancel">Cancel</button>
                            </a>
                        </div>
                    </form>
                </div>
            </body>

            </html>