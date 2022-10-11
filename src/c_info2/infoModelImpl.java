package c_info2;

import java.sql.*;
import java.util.ArrayList;

// JDBC 틀만들기
public class infoModelImpl implements InfoModel {

	final static String DRIVER = "oracle.jdbc.driver.OracleDriver";
	final static String URL = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	final static String USER = "scott";
	final static String PASS = "tiger";

	public infoModelImpl() throws ClassNotFoundException {
		// 1. 드라이버 로딩
		Class.forName(DRIVER); // 고객이 화면을 볼때 try 사용시 화면을 출력되나 오류가 나있는 상태이기 때문에 불편함을 느낄수 있음
		System.out.println("드라이버로딩 성공");

	} // end of infoModelImpl();

	/*
	 * 사용자 입력값을 받아서 DB에 저장하는 역할
	 */
	
	public void insertInfo(InfoVO vo) throws SQLException { // 예외를 던지고 시작
		// 2. 연결객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null; // 블럭안에서 선언시 해당블럭에서만 사용 가능하므로 블럭밖에서 선언
		try {
			con = DriverManager.getConnection(URL, USER, PASS);

			// 3. sql 문장 (#)
			String sql = "INSERT INTO info_tab(name, jumin, tel, gender, age, home)  " + "  VALUES (?,?,?,?,?,?)";

			// 4. 전송객체
			ps = con.prepareStatement(sql);
			// ? 세팅(#)
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getId());
			ps.setString(3, vo.getTel());
			ps.setString(4, vo.getGender());
			ps.setInt(5, vo.getAge());
			ps.setString(6, vo.getHome());
				// 창에 입력된 값들을 불러와 데이터베이스 테이블에 값을 입력
			// 5. 전송
			ps.executeUpdate();

		} finally {
			// 6. 닫기 무조건 finally 안에 넣어주기
			ps.close();
			con.close();
		}
	} // end of isertInfo()

	
	
	/*
	 * 전체 info_tab 의 레코드 검색
	 */
	@Override
	public ArrayList<InfoVO> selectAll() throws SQLException {
		// 2. 연결객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null; // 블럭안에서 선언시 해당블럭에서만 사용 가능하므로 블럭밖에서 선언
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASS);

			// 3. sql 문장
			String sql = "SELECT * FROM info_tab";

			// 4. 전송객체
			ps = con.prepareStatement(sql);

			// 5. 전송
			rs = ps.executeQuery();
			// 한사람에 대한 정보를 VO에 담기
			ArrayList<InfoVO> list = new ArrayList<InfoVO>();
			while (rs.next()) {	// rs.next 다음행으로 넘기기위해 사용 엔터키
				InfoVO vo = new InfoVO();
				vo.setName(rs.getString("name"));
				vo.setId(rs.getString("jumin"));
				vo.setTel(rs.getString("tel"));
				vo.setGender(rs.getString("gender"));
				vo.setAge(rs.getInt("age"));
				vo.setHome(rs.getString("home"));
				// 칼럼내용을 받아와 vo 6가지 컬럼 저장후 다음행으로 넘어가 다시 6가지 칼럼저장 배열에 담김
				
				list.add(vo);
			}

			return list;

		} finally {
			// 6. 닫기 무조건 finally 안에 넣어주기
			rs.close();
			ps.close();
			con.close();
		}

	} // end of selectAll();

	@Override
	public InfoVO selectByTel(String tel) throws SQLException {
		// 2. 연결객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null; 
		ResultSet rs = null;
		InfoVO vo = new InfoVO();  // 객체 선언
		try {
			con = DriverManager.getConnection(URL, USER, PASS);
			// 3.sql 문장
			String sql = "SELECT * FROM info_tab WHERE tel = ?";
			// 4. 전송객체 얻어오기
			ps = con.prepareStatement(sql);
			ps.setString(1, tel);
			// 5. 전송
			rs = ps.executeQuery();
			// select 결과집합(가상테이블)을 불러와 resultset로 받음
			if(rs.next()) {	// rs.next 다음행으로 넘기기위해 사용 엔터키
				vo.setName(rs.getString("name"));
				vo.setId(rs.getString("jumin"));
				vo.setTel(rs.getString("tel"));
				vo.setGender(rs.getString("gender"));
				vo.setAge(rs.getInt("age"));
				vo.setHome(rs.getString("home"));
			}
		
		} finally {
			// 6. 닫기 무조건 finally 안에 넣어주기
			rs.close();
			ps.close();
			con.close();
		}
		return vo; // vo값을 리턴해줌 
	} //end of selectByTel);

	/*
	 * 메소드명  : delete 
	 * 인자		: 전화번호
	 * 리턴값		: 삭제한 행 수
	 * 역할		: 전화번호를 넘겨받아 해당 전화번호의 레코드를 삭제	
	 */
	public int delete(String tel) throws SQLException {
		// 2. 연결객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null; 
		int de = 0;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASS);
			// 3.sql 문장
			String sql = "DELETE FROM info_tab  WHERE tel = ?";
			// 4. 전송객체 얻어오기
			ps = con.prepareStatement(sql);
			ps.setString(1, tel);
			// 5. 삭제
			de = ps.executeUpdate();

		} finally {
			// 6. 닫기 무조건 finally 안에 넣어주기

			ps.close();
			con.close();
		}
		return de; 
	}
	
	public int update(InfoVO vo) throws SQLException{
		// 2. 연결객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null; 
		int upd = 0;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASS);
			// 3.sql 문장
			String sql = " UPDATE info_tab SET name=?,jumin=?,gender=?,age=?,home=?  WHERE tel = ?";
			// 4. 전송객체 얻어오기
			ps = con.prepareStatement(sql);
			ps.setString(6, vo.getTel()); // where tel
	
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getId());
			ps.setString(3, vo.getGender());
			ps.setInt(4, vo.getAge());
			ps.setString(5, vo.getHome());

			
			
			// 5. 삭제
			upd = ps.executeUpdate();

		} finally {
			// 6. 닫기 무조건 finally 안에 넣어주기

			ps.close();
			con.close();
		}
		return upd ;
		
		
	}
	

}

// 자바에선 오류가 안날수 있으나 실제 오류가 날지 안날지 불확실하기 때문에
// 전부 예외가 발생할꺼라 생각하고 예외처리를 해준다.
//public infoModelImpl() throws ClassNotFoundException
// public void insertInfo(InfoVO vo) throws SQLException
// try catch 를 사용하지않고 throws 를 사용하는 이유는 try catch를 사용시
// 화면에 예외에러가 발생하더라도 고객컴퓨터에서는 에러가 난지 모르기 때문에(화면이 넘어가지 않는 것등)
// throws로 예외처리 던진다. 
// try finally 는 예외가 발생하여 닫을수 없기때문에 무조건 실행시키기 위해 사용