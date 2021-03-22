package com.zetcode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaPostgreSQLNoTransactionEx {

	public static void main(String[] args) {

		Properties props = JavaPostgreSQLRetrieveProperties.readProperties();

		String url = props.getProperty("db.url");
		String user = props.getProperty("db.user");
		String passwd = props.getProperty("db.passwd");

		try (Connection con = DriverManager.getConnection(url, user, passwd); 
				Statement st = con.createStatement()) {

			st.executeUpdate("UPDATE authors SET name = 'Leo Tolstoy' " + "WHERE Id = 1");
			st.executeUpdate("UPDATE books SET title = 'War and Peace' " + "WHERE Id = 1");
			st.executeUpdate("UPDATE books SET title = 'Anna Karenina' " + "WHERE Id = 2");

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(JavaPostgreSQLNoTransactionEx.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}
}
