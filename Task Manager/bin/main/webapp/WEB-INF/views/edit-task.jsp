<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <title>Редактировать задачу</title>
                <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
            </head>

            <body>
                <div class="container mt-5">
                    <h2>Редактировать задачу</h2>
                    <form action="task" method="post">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="id" value="${task.id}">

                        <div class="form-group">
                            <label for="title">Название:</label>
                            <input type="text" class="form-control" id="title" name="title" value="${task.title}"
                                required>
                        </div>

                        <div class="form-group">
                            <label for="description">Описание:</label>
                            <textarea class="form-control" id="description" name="description"
                                rows="3">${task.description}</textarea>
                        </div>

                        <div class="form-group">
                            <label for="status">Статус:</label>
                            <select class="form-control" id="status" name="status" required>
                                <option value="IN_PROGRESS" ${task.status=='IN_PROGRESS' ? 'selected' : '' }>В процессе
                                </option>
                                <option value="COMPLETED" ${task.status=='COMPLETED' ? 'selected' : '' }>Завершено
                                </option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="deadline">Срок выполнения:</label>
                            <input type="date" class="form-control" id="deadline" name="deadline"
                                value="<fmt:formatDate value=" ${task.deadline}" pattern="yyyy-MM-dd" />" required>
                        </div>

                        <button type="submit" class="btn btn-primary">Сохранить</button>
                        <a href="task" class="btn btn-secondary">Отмена</a>
                    </form>
                </div>

                <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
                <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
            </body>

            </html>