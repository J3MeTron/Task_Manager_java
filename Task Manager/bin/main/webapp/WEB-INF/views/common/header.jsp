<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Task Manager</title>
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
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/tasks">Task Manager</a>
            </div>
        </nav>
        <div class="container mt-4">