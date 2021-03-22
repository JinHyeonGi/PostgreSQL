package com.zetcode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaPostgreSQLWriteImage {
	public static void main(String[] args) {
		Properties props = JavaPostgreSQLRetrieveProperties.readProperties();

		String url = props.getProperty("db.url");
		String user = props.getProperty("db.user");
		String passwd = props.getProperty("db.passwd");

		String query = "INSERT INTO images(data) VALUES(?)";
		
		try (Connection con = DriverManager.getConnection(url, user, passwd);
				PreparedStatement pst = con.prepareStatement(query)) {

			File img = new File("src/main/resource/sid.jpg");

			try (FileInputStream fin = new FileInputStream(img)) {

				pst.setBinaryStream(1, fin, (int) img.length());
				pst.executeUpdate();
			} catch (IOException ex) {
				Logger.getLogger(JavaPostgreSQLWriteImage.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
			}

		} catch (SQLException ex) {

			Logger lgr = Logger.getLogger(JavaPostgreSQLWriteImage.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}
}
