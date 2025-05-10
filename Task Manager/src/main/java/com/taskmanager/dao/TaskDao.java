package com.taskmanager.dao;

import com.taskmanager.model.Task;
import java.sql.SQLException;
import java.util.List;

public interface TaskDao {
    void create(Task task) throws SQLException;

    Task getById(Long id) throws SQLException;

    List<Task> getAll() throws SQLException;

    List<Task> getByUserId(Long userId) throws SQLException;

    void update(Task task) throws SQLException;

    void delete(Long id) throws SQLException;
}