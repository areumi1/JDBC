package b_preparedStatement;

import java.sql.*;

public class InsertEmp {

	public static void main(String[] args) {

		try {
		// 1. 드라이버 로딩
		Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버로딩 성공");
			
		// 2. 연결객체 얻어오기
		String url = "jdbc:oracle:thin:@192.168.0.81:1521:xe";
		String user = "scott";
		String pass = "tiger";
		
		Connection con = DriverManager.getConnection(url,user,pass);
		System.out.println("디비 연결 성공2");
        
		//--- 입력값
		String bonmyeng = "본명";
		int welgup = 10000;
		String jikup = "IT";
		
		
		//3.SQL 문장
        String sql = "INSERT INTO emp(empno, ename, sal, job)  "
              + "  VALUES(5555,?,?,?)";
        

        
        //4. SQL 전송객체 (3에서 문장을 만들어주면 날리는 이게 필요)
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, bonmyeng);
        stmt.setInt(2, welgup);
        stmt.setString(3, jikup);
        
        //5. SQL 전송메소드
           // 1. ReusltSet executeQuery()  : SELECT 문장에만
           // 2. int executeUpdate()  : INSERT/DELETE/UPDATE 문장에만 
        
        int result = stmt.executeUpdate();  //***** 이미 sql 연결함
        System.out.println(result + "행을 실행");
        
        //6. 닫기
        stmt.close();
        con.close();
		
		// 6. 닫기
		stmt.close();
		con.close();
		
		} catch (Exception e) {
			System.out.println("DB 실패 :"+e);
		}
		

	}

}
