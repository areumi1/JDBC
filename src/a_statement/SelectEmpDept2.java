package a_statement;

import java.sql.*;


public class SelectEmpDept2 {

	public static void main(String[] args) {
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
			
			//--- 입력값
			String bonmyeng = "본명";
			int welgup = 10000;
			String jikup = "IT";
	        
			//3.SQL 문장
			// 		20번 부서의 사원들의 정보 - 사번, 사원명, 월급, 부서명, 근무지
			String sql = "INSERT INTO emp (empno, ename, sal,j ob)  "
		            + "   VALUES ( seq_temp2.nextval, '"+bonmyeng+"',"+welgup+",'"+jikup+"')";
		         System.out.println(sql);
				
	        //4. SQL 전송객체 (3에서 문장을 만들어주면 날리는 이게 필요)
	        Statement stmt = con.createStatement();
	        
	        //5. SQL 전송메소드
	           // 1. ResultSet executeQuery()  : SELECT 문장에만
	           // 2. int executeUpdate()  : INSERT/DELETE/UPDATE 문장에만 
	        
			ResultSet rs = stmt.executeQuery(sql);
	        while(rs.next()) {
	        	// 여기에 출력
	        	int empno 		= rs.getInt("EMPNO");
	        	String ename	= rs.getString("ENAME");
	        	int sal			= rs.getInt("SAL");
	        	String job 		= rs.getString("JOB");
	        	System.out.println(empno + "/" + ename+ "/" + sal + "/" + job);
	        }
	        
	        //6. 닫기
	        rs.close();
	        stmt.close();
	        con.close();
			
			// 6. 닫기

			
			} catch (Exception e) {
				System.out.println("DB 실패 :"+e);
			}
			
	}

}
