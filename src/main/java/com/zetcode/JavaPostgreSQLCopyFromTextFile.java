package com.zetcode;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

public class JavaPostgreSQLCopyFromTextFile {
	public static void main(String[] args) {

		Properties props = JavaPostgreSQLRetrieveProperties.readProperties();

		String url = props.getProperty("db.url");
		String user = props.getProperty("db.user");
		String passwd = props.getProperty("db.passwd");

		try (Connection con = DriverManager.getConnection(url, user, passwd)) {

			CopyManager cm = new CopyManager((BaseConnection) con);

			String fileName = "src/main/resources/friends.txt";

			try (FileInputStream fis = new FileInputStream(fileName);
					InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8)) {

				cm.copyIn("COPY friends FROM STDIN WITH DELIMITER '|'", isr);
				System.out.println("success");
			}

		} catch (SQLException | IOException ex) {
			Logger lgr = Logger.getLogger(JavaPostgreSQLCopyFromTextFile.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		}
	}
}
