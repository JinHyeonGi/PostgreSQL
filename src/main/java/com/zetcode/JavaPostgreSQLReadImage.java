package com.zetcode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaPostgreSQLReadImage {
	public static void main(String[] args) {
		
		Properties props = JavaPostgreSQLRetrieveProperties.readProperties();

		String url = props.getProperty("db.url");
		String user = props.getProperty("db.user");
		String passwd = props.getProperty("db.passwd");

		String query = "SELECT data, LENGTH(data) FROM images WHERE id = 1";

		try (Connection con = DriverManager.getConnection(url, user, passwd);
				PreparedStatement pst = con.prepareStatement(query);
				ResultSet rs = pst.executeQuery()) {

			rs.next();
			File myFile = new File("src/main/resource/sid.jpg");

			try (FileOutputStream fos = new FileOutputStream(myFile)) {
				int len = rs.getInt(2);
				byte[] buf = rs.getBytes("data");
				fos.write(buf, 0, len);
			}

		} catch (IOException | SQLException ex) {

			Logger lgr = Logger.getLogger(JavaPostgreSQLReadImage.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}
}
