package b_preparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UpdateEmp {

	public static void main(String[] args) {
		
		// 입력값
		int sabun = 7698;
		String saname = "아무개";
		int salary = 15000;
		
		// 7698 사원의 이름과 월급을 변경하세요
		
		try {
			// 1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
				System.out.println("드라이버로딩 성공");
				
			// 2. 연결객체 얻어오기
			String url = "jdbc:oracle:thin:@192.168.0.40:1521:xe";
			String user = "scott";
			String pass = "tiger";
			
			Connection con = DriverManager.getConnection(url,user,pass);
			System.out.println("디비 연결 성공2");
			//3.SQL 문장
			// 		
			String sql = "UPDATE emp SET ename = ?, sal = ?  "
					+ "  WHERE empno = ? ";
			

				
	        //4. SQL 전송객체 (3에서 문장을 만들어주면 날리는 이게 필요)
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, saname);
	        stmt.setInt(2, salary);
	        stmt.setInt(3, sabun);
	        //5. SQL 전송메소드
	           // 1. ResultSet executeQuery()  : SELECT 문장에만
	           // 2. int executeUpdate()  : INSERT/DELETE/UPDATE 문장에만 
	        
			ResultSet rs = stmt.executeQuery();
			
	        int result = stmt.executeUpdate();  //***** 이미 sql 연결함
	        System.out.println(result + "행을 실행");
	        
	        //6. 닫기
	        rs.close();
	        stmt.close();
	        con.close();
		} catch (Exception e) {
			System.out.println("DB 실패 :"+e);
		}

	}

}
