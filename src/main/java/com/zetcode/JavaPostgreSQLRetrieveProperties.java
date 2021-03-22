package com.zetcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaPostgreSQLRetrieveProperties {
	
	public static Properties readProperties() {

		Properties props = new Properties();
		Path myPath = Paths.get("src/main/resource/database.properties");

		try {
			BufferedReader bf = Files.newBufferedReader(myPath, StandardCharsets.UTF_8);
			props.load(bf);
		} catch (IOException ex) {
			Logger.getLogger(JavaPostgreSQLRetrieveProperties.class.getName()).log(Level.SEVERE, null, ex);
		}

		return props;
	}

	public static void main(String[] args) {

		Properties props = readProperties();

		String url = props.getProperty("db.url");
		String user = props.getProperty("db.user");
		String passwd = props.getProperty("db.passwd");

		try (Connection con = DriverManager.getConnection(url, user, passwd);
				PreparedStatement pst = con.prepareStatement("SELECT * FROM Authors");
				ResultSet rs = pst.executeQuery()) {

			while (rs.next()) {
				System.out.print(rs.getInt(1));
				System.out.print(": ");
				System.out.println(rs.getString(2));
			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(JavaPostgreSQLRetrieveProperties.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}
}