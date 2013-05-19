package com._500bottles.da.internal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com._500bottles.config.Config;

/**
 * Class to interface directly with database via JDBC drivers. Can conduct
 * queries that both modify and read information from the database.
 */
public class Database
{
	/**
	 * Conducts a query of the database the does not modify the contents of
	 * the database.
	 * @param q	Query to execute.
	 * @return      ResultSet object of query result.
	 * @throws SQLException
	 */
	public static ResultSet readQuery(String q) throws SQLException
	{
		PreparedStatement p;
		ResultSet r;

		try {
			Connection conn = connect();

			p = conn.prepareStatement(q);
			r = p.executeQuery(q);

			disconnect(conn);

		} catch (SQLException e) {
			throw e;
		}

		return r;
	}

	/**
	 * Conducts a query of the database that may modify database records.
	 * @param q	Query to execute.
	 * @return	Numeric result of database query.
	 * @throws SQLException
	 */
	public static int modQuery(String q) throws SQLException
	{
		PreparedStatement p;
		int i;

		try {
			Connection conn = connect();

			p = conn.prepareStatement(q);
			i = p.executeUpdate(q);

			disconnect(conn);


		} catch (SQLException e) {
			throw e;
		}

		return i;
	}

	/**
	 * Connects to the database and returns the Connection object.
	 * @return	Database Connection object.
	 * @throws SQLException
	 */
	public static Connection connect() throws SQLException
	{
		String dbUsername, dbPassword, connectionUrl;

		Connection connection;

		dbUsername = Config.getProperty("databaseUsername");
		dbPassword = Config.getProperty("databasePassword");
		connectionUrl = getConnectionUrl();

		try {
			connection = DriverManager.getConnection(connectionUrl, dbUsername, dbPassword);
		} catch (SQLException e) {
			throw e;
		}

		return connection;
	}

	/**
	 * Disconnects a database connection.
	 * @param connection	Connection object to disconnect.
	 * @throws SQLException
	 */
	public static void disconnect(Connection connection) throws SQLException
	{
		if (connection == null) return;

		try {
			connection.close();
		} catch (SQLException e) {
			throw e;
		}
	}

	/**
	 * Builds the JDBC connection URL using configuration information.
	 * @return	JDBC Connection URL
	 */
	private static String getConnectionUrl()
	{
		String dbPort, dbHost, dbName, connectionUrl;

		dbPort = Config.getProperty("databasePort");
		dbHost = Config.getProperty("databaseHost");
		dbName = Config.getProperty("databaseName");

		connectionUrl = "jdbc:mysql://" + dbHost + ":"
			+ dbPort + "/" + dbName + "?";

		return connectionUrl;
	}
}
