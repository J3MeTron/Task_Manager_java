package com.taskmanager.dao;

import com.taskmanager.model.Task;
import com.taskmanager.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

public class TaskDaoImpl implements TaskDao {
    private static final String CREATE_TASK = "INSERT INTO tasks (title, description, status, deadline, user_id) VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID = "SELECT * FROM tasks WHERE id = ?";
    private static final String FIND_BY_USER_ID = "SELECT * FROM tasks WHERE user_id = ?";
    private static final String UPDATE_TASK = "UPDATE tasks SET title = ?, description = ?, status = ?, deadline = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
    private static final String DELETE_TASK = "DELETE FROM tasks WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM tasks";

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void create(Task task) throws SQLException {
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(CREATE_TASK, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getStatus().name());
            stmt.setString(4, dateFormat.format(task.getDeadline()));
            stmt.setLong(5, task.getUserId());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    task.setId(rs.getLong(1));
                }
            }
        }
    }

    @Override
    public Task getById(Long id) throws SQLException {
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(FIND_BY_ID)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractTask(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Task> getAll() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(FIND_ALL)) {
            while (rs.next()) {
                tasks.add(extractTask(rs));
            }
        }
        return tasks;
    }

    @Override
    public List<Task> getByUserId(Long userId) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(FIND_BY_USER_ID)) {
            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tasks.add(extractTask(rs));
                }
            }
        }
        return tasks;
    }

    @Override
    public void update(Task task) throws SQLException {
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(UPDATE_TASK)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getStatus().name());
            stmt.setString(4, dateFormat.format(task.getDeadline()));
            stmt.setLong(5, task.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(DELETE_TASK)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private Task extractTask(ResultSet rs) throws SQLException {
        Task task = new Task();
        task.setId(rs.getLong("id"));
        task.setTitle(rs.getString("title"));
        task.setDescription(rs.getString("description"));
        task.setStatus(Task.TaskStatus.valueOf(rs.getString("status")));

        String deadlineStr = rs.getString("deadline");
        if (deadlineStr != null) {
            try {
                task.setDeadline(dateFormat.parse(deadlineStr));
            } catch (Exception e) {
                try {
                    task.setDeadline(shortDateFormat.parse(deadlineStr));
                } catch (Exception e2) {
                    throw new SQLException("Error parsing deadline: " + deadlineStr, e2);
                }
            }
        }

        task.setUserId(rs.getLong("user_id"));

        String createdAtStr = rs.getString("created_at");
        if (createdAtStr != null) {
            try {
                task.setCreatedAt(dateFormat.parse(createdAtStr));
            } catch (Exception e) {
                try {
                    task.setCreatedAt(shortDateFormat.parse(createdAtStr));
                } catch (Exception e2) {
                    // Игнорируем ошибку парсинга created_at
                }
            }
        }

        String updatedAtStr = rs.getString("updated_at");
        if (updatedAtStr != null) {
            try {
                task.setUpdatedAt(dateFormat.parse(updatedAtStr));
            } catch (Exception e) {
                try {
                    task.setUpdatedAt(shortDateFormat.parse(updatedAtStr));
                } catch (Exception e2) {
                    // Игнорируем ошибку парсинга updated_at
                }
            }
        }

        return task;
    }
}