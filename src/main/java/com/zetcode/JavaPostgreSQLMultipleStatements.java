package com.zetcode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaPostgreSQLMultipleStatements {
	public static void main(String[] args) {

		Properties props = JavaPostgreSQLRetrieveProperties.readProperties();

		String url = props.getProperty("db.url");
		String user = props.getProperty("db.user");
		String passwd = props.getProperty("db.passwd");

		String query = "SELECT id, name FROM authors WHERE Id=1;" + "SELECT id, name FROM authors WHERE Id=2;"
				+ "SELECT id, name FROM authors WHERE Id=3";

		try (Connection con = DriverManager.getConnection(url, user, passwd);
				PreparedStatement pst = con.prepareStatement(query)) {

			boolean isResult = pst.execute();

			do {
				try (ResultSet rs = pst.getResultSet()) {

					while (rs.next()) {

						System.out.print(rs.getInt(1));
						System.out.print(": ");
						System.out.println(rs.getString(2));
					}

					isResult = pst.getMoreResults();
				}
			} while (isResult);

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(JavaPostgreSQLMultipleStatements.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}
}
