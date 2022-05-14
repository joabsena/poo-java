package main.java.task.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import main.java.task.model.Task;

public class TaskRepository {
	static Connection connection;
	
	private final static String URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=public";
    private final static String USER = "postgres";
    private final static String PASSWORD = "postgres";
    private final static String INSERT_TASK = "INSERT INTO TASK (id, name, description) VALUES (?, ?, ?)";
    private final static String LIST_ALL = "SELECT * FROM public.task";
	
	public static Task saveTask(Task task) {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(URL, USER, PASSWORD);

			
			PreparedStatement stmt = connection.prepareStatement(INSERT_TASK);
			stmt.setString(1, UUID.randomUUID().toString());
			stmt.setString(2, task.getName());
			stmt.setString(3, task.getDescription());
			
			stmt.execute();
			stmt.close();
			return task;
		} catch(Exception e) {
			System.out.println(e);
			e.getMessage();
		}
		return null;
	}
	
	public static List<Task> listAll() {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			List<Task> tasks = new ArrayList<Task>();
			String id;
			String name;
			String description;
			
			PreparedStatement stmt = connection.prepareStatement(LIST_ALL);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Task t = new Task();
				t.setId(rs.getString(1));
				t.setName(rs.getString(2));
				t.setDescription(rs.getString(3));
				tasks.add(t);
			}
			
			stmt.execute();
			stmt.close();
			return tasks;
		} catch(Exception e) {
			System.out.println(e);
			e.getMessage();
		}
		return null;
	}
}
