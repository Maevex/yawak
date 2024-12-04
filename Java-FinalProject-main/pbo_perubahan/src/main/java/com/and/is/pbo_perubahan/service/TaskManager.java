package com.and.is.pbo_perubahan.service;

import com.and.is.pbo_perubahan.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    public Task findTaskByTitle(String title) {
        String sql = "SELECT * FROM tasks WHERE title = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Task(
                        rs.getInt("id"), // Pastikan Task memiliki atribut ID
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getTimestamp("due_date") != null ? rs.getTimestamp("due_date").toLocalDateTime() : null,
                        rs.getString("category"),
                        rs.getString("priority"),
                        rs.getBoolean("completed")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void addTask(Task task) {
        String query = "INSERT INTO tasks (title, description, due_date, category, priority, completed) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setTimestamp(3, task.getDueDate() != null ? Timestamp.valueOf(task.getDueDate()) : null);
            stmt.setString(4, task.getCategory());
            stmt.setString(5, task.getPriority());
            stmt.setBoolean(6, task.isCompleted());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM tasks";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Task task = new Task(
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getTimestamp("due_date") != null ? rs.getTimestamp("due_date").toLocalDateTime() : null,
                        rs.getString("category"),
                        rs.getString("priority"),
                        rs.getBoolean("completed")
                );
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public boolean updateTask(Task task) {
        String sql = "UPDATE tasks SET title = ?, description = ?, due_date = ?, category = ?, priority = ?, completed = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setTimestamp(3, task.getDueDate() != null ? Timestamp.valueOf(task.getDueDate()) : null);
            stmt.setString(4, task.getCategory());
            stmt.setString(5, task.getPriority());
            stmt.setBoolean(6, task.isCompleted());
            stmt.setInt(7, task.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public void deleteTask(String title) {
        String query = "DELETE FROM tasks WHERE title=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
