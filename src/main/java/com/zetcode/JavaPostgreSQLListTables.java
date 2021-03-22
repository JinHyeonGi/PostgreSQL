package com.zetcode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaPostgreSQLListTables {
	public static void main(String[] args) {

		Properties props = JavaPostgreSQLRetrieveProperties.readProperties();

		String url = props.getProperty("db.url");
		String user = props.getProperty("db.user");
		String passwd = props.getProperty("db.passwd");
		
		String query = "SELECT table_name FROM information_schema.tables " + "WHERE table_schema = 'public'";

		try (Connection con = DriverManager.getConnection(url, user, passwd);
				PreparedStatement pst = con.prepareStatement(query);
				ResultSet rs = pst.executeQuery()) {

			while (rs.next()) {

				System.out.println(rs.getString(1));
			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(JavaPostgreSQLListTables.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}
}
