package com.zetcode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaPostgreSQLBatchUpdates {
	public static void main(String[] args) {

		Properties props = JavaPostgreSQLRetrieveProperties.readProperties();

		String url = props.getProperty("db.url");
		String user = props.getProperty("db.user");
		String passwd = props.getProperty("db.passwd");

		try (Connection con = DriverManager.getConnection(url, user, passwd)) {

			try (Statement st = con.createStatement()) {

				con.setAutoCommit(false);
				
				st.addBatch("DROP TABLE IF EXISTS friends");
				st.addBatch("CREATE TABLE friends(id serial, name VARCHAR(10))");
				st.addBatch("INSERT INTO friends(name) VALUES ('Jane')");
				st.addBatch("INSERT INTO friends(name) VALUES ('Tom')");
				st.addBatch("INSERT INTO friends(name) VALUES ('Rebecca')");
				st.addBatch("INSERT INTO friends(name) VALUES ('Jim')");
				st.addBatch("INSERT INTO friends(name) VALUES ('Robert')");

				int counts[] = st.executeBatch();

				con.commit();

				System.out.println("Committed " + counts.length + " updates");

			} catch (SQLException ex) {

				if (con != null) {
					try {
						con.rollback();
					} catch (SQLException ex1) {
						Logger lgr = Logger.getLogger(JavaPostgreSQLBatchUpdates.class.getName());
						lgr.log(Level.WARNING, ex1.getMessage(), ex1);
					}
				}

				Logger lgr = Logger.getLogger(JavaPostgreSQLBatchUpdates.class.getName());
				lgr.log(Level.SEVERE, ex.getMessage(), ex);
			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(JavaPostgreSQLBatchUpdates.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}
}
