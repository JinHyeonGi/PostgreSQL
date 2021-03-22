package com.zetcode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaPostgreSQLRetrieve {

	public static void main(String[] args) {

		String url = "jdbc:postgresql://localhost:5432/testDB";
        String user = "postgres";
        String password = "mytest";

        try (Connection con = DriverManager.getConnection(url, user, password);
                PreparedStatement pst = con.prepareStatement("SELECT * FROM authors");
                ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                System.out.print(rs.getInt(1));
                System.out.print(": ");
                System.out.println(rs.getString(2));
            }

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(JavaPostgreSQLRetrieve.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
	}

}
