package Util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnect {
	private final String DBDRIVER = "org.gjt.mm.mysql.Driver";
	private final String DBURL = "jdbc:mysql://localhost:3306/webex21java";
	private final String DBUSER = "root";
	private final String DBPSD = "root";
	private Connection conn = null;
	private Statement stmt = null;
	private PreparedStatement prepstmt = null;

	void init() {
		try {
			Class.forName(DBDRIVER);
			conn = DriverManager.getConnection(DBURL,DBUSER,DBPSD);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public DBConnect() throws SQLException {
		init();
		stmt = conn.createStatement();
	}

	public DBConnect(int resultSetType, int resultSetConcurrency) throws SQLException {
		init();
		stmt = conn.createStatement(resultSetType, resultSetConcurrency);
	}

	public DBConnect(String sql) throws SQLException {
		init();
		this.prepareStatement(sql);
	}

	public DBConnect(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		init();
		this.prepareStatement(sql, resultSetType, resultSetConcurrency);
	}

	public int executeUpdate(String sql, int preparetype) throws SQLException {
		int generatedid = 0;
		if (stmt != null) {
			stmt.executeUpdate(sql, preparetype);
			ResultSet genid = stmt.getGeneratedKeys();
			if (genid.next()) {
				generatedid = genid.getInt(1);
			}
		}
		return generatedid;
	}

	public Connection getConnection() {
		return conn;
	}

	public void prepareStatement(String sql) throws SQLException {
		prepstmt = conn.prepareStatement(sql);
	}

	public void prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		prepstmt = conn.prepareStatement(sql, resultSetType, resultSetConcurrency);
	}

	public void setString(int index, String value) throws SQLException {
		prepstmt.setString(index, value);
	}

	public void setInt(int index, int value) throws SQLException {
		prepstmt.setInt(index, value);
	}

	public void setBoolean(int index, boolean value) throws SQLException {
		prepstmt.setBoolean(index, value);
	}

	public void setDate(int index, Date value) throws SQLException {
		prepstmt.setDate(index, value);
	}

	public void setLong(int index, long value) throws SQLException {
		prepstmt.setLong(index, value);
	}

	public void setDouble(int index, double value) throws SQLException {
		prepstmt.setDouble(index, value);
	}

	public void setFloat(int index, float value) throws SQLException {
		prepstmt.setFloat(index, value);
	}

	public void setBytes(int index, byte[] value) throws SQLException {
		prepstmt.setBytes(index, value);
	}

	public void setBinaryStream(int index, InputStream is, int length) throws SQLException {
		prepstmt.setBinaryStream(index, is, length);
	}

	public void setObject(int index, Object value) throws SQLException {
		prepstmt.setObject(index, value);
	}

	public void clearParameters() throws SQLException {
		prepstmt.clearParameters();
		prepstmt = null;
	}

	public PreparedStatement getPreparedStatement() {
		return prepstmt;
	}

	public Statement getStatement() {
		return stmt;
	}

	public ResultSet executeQuery(String sql) throws SQLException {
		if (stmt != null) {
			return stmt.executeQuery(sql);
		} else {
			return null;
		}
	}

	public ResultSet executeQuery() throws SQLException {
		if (prepstmt != null) {
			return prepstmt.executeQuery();
		} else {
			return null;
		}
	}

	public void executeUpdate(String sql) throws SQLException {
		if (stmt != null) {
			stmt.executeUpdate(sql);
		}
	}

	public int executeUpdate() throws SQLException {
		int index=-1;
		if (prepstmt != null) {
			index=prepstmt.executeUpdate();
		}
		return index;
	}

	public void close() throws SQLException {
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
		if (prepstmt != null) {
			prepstmt.close();
			prepstmt = null;
		}
		if (conn != null) {

			conn.close();
			conn = null;
		}
	}
}
