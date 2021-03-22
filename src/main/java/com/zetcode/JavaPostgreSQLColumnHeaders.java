package com.zetcode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Formatter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaPostgreSQLColumnHeaders {
	public static void main(String[] args) {

		Properties props = JavaPostgreSQLRetrieveProperties.readProperties();

		String url = props.getProperty("db.url");
		String user = props.getProperty("db.user");
		String passwd = props.getProperty("db.passwd");


		String query = "SELECT name, title From authors, " + "books WHERE authors.id=books.author_id";

		try (Connection con = DriverManager.getConnection(url, user, passwd);
				PreparedStatement pst = con.prepareStatement(query);
				ResultSet rs = pst.executeQuery()) {

			ResultSetMetaData meta = rs.getMetaData();

			String colname1 = meta.getColumnName(1);
			String colname2 = meta.getColumnName(2);

			Formatter fmt1 = new Formatter();
			fmt1.format("%-21s%s", colname1, colname2);
			System.out.println(fmt1);

			while (rs.next()) {

				Formatter fmt2 = new Formatter();
				fmt2.format("%-21s", rs.getString(1));
				System.out.print(fmt2);
				System.out.println(rs.getString(2));
			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(JavaPostgreSQLColumnHeaders.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}
}
