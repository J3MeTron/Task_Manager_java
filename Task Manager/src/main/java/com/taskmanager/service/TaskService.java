package com.taskmanager.service;

import com.taskmanager.dao.TaskDao;
import com.taskmanager.dao.TaskDaoImpl;
import com.taskmanager.model.Task;
import java.sql.SQLException;
import java.util.List;

public class TaskService {
    private final TaskDao taskDao;

    public TaskService() {
        this.taskDao = new TaskDaoImpl();
    }

    public void createTask(Task task) throws SQLException {
        taskDao.create(task);
    }

    public Task getTaskById(Long id) throws SQLException {
        return taskDao.getById(id);
    }

    public List<Task> getTasksByUserId(Long userId) throws SQLException {
        return taskDao.getByUserId(userId);
    }

    public void updateTask(Task task) throws SQLException {
        taskDao.update(task);
    }

    public void deleteTask(Long id) throws SQLException {
        taskDao.delete(id);
    }

    public List<Task> getAllTasks() throws SQLException {
        return taskDao.getAll();
    }
}