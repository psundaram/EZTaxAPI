package com.anpi.app.util;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Connection;

public class DbConnect {

	private Connection createConnection() {
		Connection connection_mysql = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
//			connection_mysql = (Connection) DriverManager.getConnection("jdbc:mysql://10.2.2.93:3306/billing", "mysqladmin", "My5q1adm1n");
			connection_mysql = (Connection) DriverManager.getConnection("jdbc:mysql://10.2.2.118:3306/billing", "billing", "bi**i&&");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection_mysql;
	}

	public int insert(String sql) {
		System.out.println("entering insert" + sql);
		boolean success = false;
		int insertId = 0;
		DbConnect dbConnect = new DbConnect();
		Connection con = dbConnect.createConnection();
		Statement stmt;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stmt.getGeneratedKeys();
			while (rs.next()) {
				insertId = rs.getInt(1);
			}
			success = true;
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		} finally {
			// Close connections be handled as functions with exception
			// handling.
			try {
				con.close();
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
		System.out.println("exiting insert -->");
		return insertId;
	}

	
	public static void main(String[] args) {
//		String sql = "insert into t_invoice_tax_summary (`invoice_number`,`description`,`billed_to`) VALUES ('9005','Access code','"+new Date()+"')";
		
		String sql ="insert into t_invoice_tax_summary (`invoice_number`,`description` ,`value`,`total`,`billed_from`,`billed_to`) VALUES ('9002','FEDERAL_FUSF (VoIP)','9.80960904',22.998229363926754,)";

		System.out.println("Sql Summary:"+sql);
		int id = new DbConnect().insert(sql);
	}
}
