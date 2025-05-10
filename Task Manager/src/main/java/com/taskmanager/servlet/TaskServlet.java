package com.taskmanager.servlet;

import com.taskmanager.model.Task;
import com.taskmanager.service.TaskService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/tasks/*")
public class TaskServlet extends HttpServlet {
    private final TaskService taskService;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public TaskServlet() {
        this.taskService = new TaskService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                listTasks(request, response);
            } else if (pathInfo.equals("/new")) {
                showNewForm(request, response);
            } else if (pathInfo.startsWith("/edit/")) {
                String idStr = pathInfo.substring(6);
                request.setAttribute("id", idStr);
                showEditForm(request, response);
            } else if (pathInfo.startsWith("/delete/")) {
                String idStr = pathInfo.substring(8);
                request.setAttribute("id", idStr);
                deleteTask(request, response);
            } else {
                listTasks(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Error processing request", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                createTask(request, response);
            } else if (pathInfo.startsWith("/update/")) {
                String idStr = pathInfo.substring(8);
                request.setAttribute("id", idStr);
                updateTask(request, response);
            } else {
                listTasks(request, response);
            }
        } catch (SQLException | ParseException e) {
            throw new ServletException("Error processing request", e);
        }
    }

    private void listTasks(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Task> tasks = taskService.getAllTasks();
        request.setAttribute("tasks", tasks);
        request.getRequestDispatcher("/WEB-INF/views/task-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/task-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String idStr = (String) request.getAttribute("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            throw new ServletException("Task ID is required");
        }

        Long id = Long.parseLong(idStr);
        Task task = taskService.getTaskById(id);
        if (task == null) {
            throw new ServletException("Task not found with ID: " + id);
        }

        request.setAttribute("task", task);
        request.getRequestDispatcher("/WEB-INF/views/edit-task.jsp").forward(request, response);
    }

    private void createTask(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ParseException, IOException, ServletException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String statusStr = request.getParameter("status");
        String deadlineStr = request.getParameter("deadline");
        String userIdStr = request.getParameter("userId");

        if (title == null || title.trim().isEmpty()) {
            throw new ServletException("Title is required");
        }
        if (statusStr == null || statusStr.trim().isEmpty()) {
            throw new ServletException("Status is required");
        }
        if (deadlineStr == null || deadlineStr.trim().isEmpty()) {
            throw new ServletException("Deadline is required");
        }
        if (userIdStr == null || userIdStr.trim().isEmpty()) {
            throw new ServletException("User ID is required");
        }

        Task task = new Task();
        task.setTitle(title.trim());
        task.setDescription(description != null ? description.trim() : "");
        task.setStatus(Task.TaskStatus.valueOf(statusStr));
        task.setDeadline(dateFormat.parse(deadlineStr));
        task.setUserId(Long.parseLong(userIdStr));

        taskService.createTask(task);
        response.sendRedirect(request.getContextPath() + "/tasks");
    }

    private void updateTask(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ParseException, IOException, ServletException {
        String idStr = (String) request.getAttribute("id");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String statusStr = request.getParameter("status");
        String deadlineStr = request.getParameter("deadline");

        if (idStr == null || idStr.trim().isEmpty()) {
            throw new ServletException("Task ID is required");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new ServletException("Title is required");
        }
        if (statusStr == null || statusStr.trim().isEmpty()) {
            throw new ServletException("Status is required");
        }
        if (deadlineStr == null || deadlineStr.trim().isEmpty()) {
            throw new ServletException("Deadline is required");
        }

        Long id = Long.parseLong(idStr);
        Task task = taskService.getTaskById(id);
        if (task == null) {
            throw new ServletException("Task not found with ID: " + id);
        }

        task.setTitle(title.trim());
        task.setDescription(description != null ? description.trim() : "");
        task.setStatus(Task.TaskStatus.valueOf(statusStr));
        task.setDeadline(dateFormat.parse(deadlineStr));

        taskService.updateTask(task);
        response.sendRedirect(request.getContextPath() + "/tasks");
    }

    private void deleteTask(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String idStr = (String) request.getAttribute("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            throw new ServletException("Task ID is required");
        }

        Long id = Long.parseLong(idStr);
        Task task = taskService.getTaskById(id);
        if (task == null) {
            throw new ServletException("Task not found with ID: " + id);
        }

        taskService.deleteTask(id);
        response.sendRedirect(request.getContextPath() + "/tasks");
    }
}