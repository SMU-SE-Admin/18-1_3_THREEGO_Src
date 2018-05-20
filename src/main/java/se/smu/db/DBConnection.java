package se.smu.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
	private Connection conn = null;
	private Statement st = null;
	private ResultSet rs = null;
	private Logger lgr = null;

	public DBConnection() {
		lgr = Logger.getLogger(DBConnection.class.getName());
		String url = "jdbc:postgresql://se-project.ci1zyasoxkzt.ap-northeast-2.rds.amazonaws.com/se_project";
		String user = "se_project";
		String password = "sethreego";
		
		try {
			this.conn = DriverManager.getConnection(url, user, password);
			lgr.log(Level.INFO, "DB Connected");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public Object[] getLogIn(String _id, String _pwd) {
		String id = "";
		String pwd = "";
		Object[] result = new Object[2];
		String sql = "select * from user_tbl where id = \'" + _id + "\' and pwd = \'" + _pwd + "\'";
		rs = makeSQL(sql);
		try {
		if(rs.next()) {
			id = rs.getString("id");
			pwd = rs.getString("pwd");
			result[0] = id;
			result[1] = pwd;
			lgr.log(Level.FINE, "Success Log In");
		}
		}catch(Exception e) {
			lgr.log(Level.SEVERE, e.getMessage(), e);
		}finally {
			closeRSST();
		}
		return result;
	}
	
	public boolean setTodo(Object[] row) {
		boolean result = false;
		String sql = "insert into todo_tbl values (" + row[0] + ", \'" + row[1] + "\', \'"
				+ row[2] + "\', \'" + row[3] + "\', \'" + row[4] + "\', \'" 
				+ row[5] + "\')";
		
		rs = makeSQL(sql);
		lgr.log(Level.FINE, "Success Insert todo");
		result = true;
		
		return result;
	}
	
	
	
	private ResultSet makeSQL(String sql) {
		st = null;
		rs = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
		}catch(SQLException e) {
			lgr.log(Level.SEVERE, e.getMessage(), e);
		}
		return rs; 		
	}
	
	private void closeRSST() {
		try {
			if(rs != null) {
				rs.close();
			}
			if(st != null) {
				st.close();
			}
		}catch(SQLException e) {
			lgr.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	public void close() {
		try {
			if(conn != null) {
				conn.close();
			}
		}catch(SQLException e) {
			lgr.log(Level.WARNING, e.getMessage(), e);
		}
	}

}
