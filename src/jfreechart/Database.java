package jfreechart;

import java.sql.*;
import java.util.*;

public class Database {

	String URL = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	String USER ="scott";
	String PASS = "tiger";

	public ArrayList<ArrayList> getData() {

		ArrayList<ArrayList> data = new ArrayList<ArrayList>();
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(URL, USER , PASS);	
			
			//***************************************************************
			String sql = "SELECT      nvl(to_char(hiredate, 'mm'),'확인되지않음')  m, count(*) p  "
					+ "  FROM        emp  "
					+ "  GROUP  BY     nvl(to_char(hiredate, 'mm'),'확인되지않음')  "
					+ "  ORDER  BY      nvl(to_char(hiredate, 'mm'),'확인되지않음');";
			
			//***************************************************************
			
			PreparedStatement stmt = con.prepareStatement( sql );	

			ResultSet rset = stmt.executeQuery();

			
			while( rset.next() ){
				ArrayList temp = new ArrayList();
				temp.add( rset.getInt("p"));
				temp.add( rset.getString("m"));
				
				//****************
	//****************		
				data.add(temp);
			}
			rset.close();
			stmt.close();
			con.close();
		} catch(Exception ex){
			System.out.println("에러 : " + ex.getMessage() );
		}
		return data;
	}
}
