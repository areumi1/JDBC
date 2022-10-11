package c_info2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.swing.*;

public class InfoView {
	
	//1. 멤버변수 선언
	JFrame f;
	JTextField tfname, tfId, tfTel, tfGender, tfAge, tfHome;
	JTextArea ta;
	JButton bAdd, bShow, bSearch, bDelete, bCancel, bExit;
	ImageIcon  image;
	
	// 비지니스로직 - 모델단
	InfoModel  model;

	
	//2. 멤버변수 객체생성
	
	InfoView(){
		f = new JFrame("DBTest");
		
		ta = new JTextArea(40,20);
		
		tfname = new JTextField(20);
		tfId = new JTextField(20);
		tfTel = new JTextField(20);
		tfGender = new JTextField(20);
		tfAge = new JTextField(20);
		tfHome = new JTextField(20);
		
		bAdd = new JButton("Add");
		bShow = new JButton("Show");
		bSearch = new JButton("Search");
		bDelete = new JButton("Delete");
		bCancel = new JButton("Cancel");
		bExit = new JButton("수정하기");
		
		// 모델객체 생성 // infoModelImpl 클래스에서 예외를 던전걸 잡아서 처리
		try {
			model = new infoModelImpl();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//infoModelImpl 에서 던진 예외를 처리해준다. 
	}
	

	
	//3. 화면구성하고 출력
	/*
	 *  전체 프레임 BorderLayout 지정 
	 *  	- WEST 	: JPanel 붙이기 ( GridLayout(6,2))
	 *  	- CENTER : TextArea
	 *  	- SOUTH : JPanel 붙이기 ( GridLayout(1,6))
	 */
	void addLayout() {
		
		f.setLayout(new BorderLayout());
		
		JPanel west = new JPanel();
		west.setLayout(new GridLayout(6,2));
		west.add(new JLabel("Name", JLabel.CENTER));
		west.add(tfname);
		west.add(new JLabel("ID", JLabel.CENTER));
		west.add(tfId);
		west.add(new JLabel("Tel", JLabel.CENTER));
		west.add(tfTel);
		west.add(new JLabel("Sex", JLabel.CENTER));
		west.add(tfGender);
		west.add(new JLabel("Age", JLabel.CENTER));
		west.add(tfAge);
		west.add(new JLabel("Home", JLabel.CENTER));
		west.add(tfHome);
		f.add(west , BorderLayout.WEST);
		
		f.add(new JScrollPane(ta), BorderLayout.CENTER);
		
		JPanel south = new JPanel();
		south.setLayout(new GridLayout(1,6));
		south.add(bAdd);
		south.add(bShow);
		south.add(bSearch);
		south.add(bDelete);
		south.add(bCancel);
		south.add(bExit);
		f.add(south, BorderLayout.SOUTH);
		
		// 화면 출력
		f.setBounds(500, 1000, 900, 400);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("DBTest");
	}
	
	void eventProc() {
		// Add 버튼 눌렸을때
		bAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertData();
			}
		});
		
		// Show 버튼 눌렸을때
		bShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectAll();
			}
		});
		
		// bSearch 버튼이 눌렸을 때
		bSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectByTel();
			}
		});
		tfTel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectByTel();
			}
		});
		
		// bDelete 버튼이 눌렸을 때 
		bDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteByTel();
			}
		});
		
		bExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});
		
	} // end of eventProc();
	
	void update() {
		// (1) 사용자가 입력한 값들을 얻어오기
		String tel = tfTel.getText();
		String name = tfname.getText();
		String home = tfHome.getText();
		String gender	= tfGender.getText();
		int age		= Integer.parseInt(tfAge.getText());
		String id	= tfId.getText();
		// 수정한 값들을 불러와 지역변수에 값을 입력 
		
		InfoVO vo = new InfoVO ();		
		vo.setName(name);
		vo.setId(id);
		vo.setGender(gender);
		vo.setHome(home);
		vo.setTel(tel);
		vo.setAge(age);
		// 매개변수에 입력된 값들을 vo에 입력
		// (2) 모델단에 delete() 호출
		try {
			int de = model.update(vo);
			ta.setText(de + "행이 수정되었습니다.");
		
			// (3) 성공시 화면을 지우기 
			clearText();
			}catch (SQLException e) {
				ta.setText("수정실패 : " + e.getMessage());
			}
	}
	
	void deleteByTel() {
		// (1) 사용자가 입력한 전화번호 값을 얻어오기
				String tel = tfTel.getText();
		// (2) 모델단에 delete() 호출
		try {
		int de = model.delete(tel);
		if (de == 0) {
			ta.setText("잘못된 전화번호입니다. 삭제되지 않았습니다.");
		}else if (de != 0) {
			ta.setText(de +"행이 삭제 되었습니다.");
		}
		// (3) 성공시 화면을 지우기 
		clearText();
		}catch (SQLException e) {
			ta.setText("삭제실패 : " + e.getMessage());
		}
	
	}
	
	void selectByTel() {
		try {
		// (1) 사용자가 입력한 전화번호 값을 얻어오기
		String tel = tfTel.getText();
		// (2) 모델단에 selectByTel() 호출
		InfoVO vo = model.selectByTel(tel);
		// (3) 받은 결과를 각각의 텍스트필드에 지정(출력)
		tfname.setText( vo.getName());
		tfId.setText( vo.getId());
		tfGender.setText( vo.getGender());
		tfAge.setText( String.valueOf(vo.getAge()));
		tfHome.setText( vo.getHome());
		}catch(Exception ex) {
			ta.setText("전화번호 검색 실패 : " + ex.getMessage());
		}
	}
		

			
	void selectAll() {
		try {
			ArrayList<InfoVO> data = model.selectAll();  // 칼럼내용들을 담은 배열을 불러와 향상된 for문으로 출력(각 방의내용들 출력0
			ta.setText(" ---- 검색결과 ---- \n\n");
			for(InfoVO vo : data) {		
				ta.append(vo.toString());
			}
		} catch (SQLException e) {
			ta.setText("검색실패 : " + e.getMessage());
		}
	} // end of selectAll()
	
	
	void insertData() {
		// 1. 사용자입력값 얻어오기
		String name 	= tfname.getText();
		String id		= tfId.getText();
		String home 	= tfHome.getText();
		String gender 	= tfGender.getText();
		String tel 		= tfTel.getText();
		int age 		= Integer.parseInt(tfAge.getText());
		
		// 2. 1번의 값들을 하나의 InfoVO에 지정
		InfoVO vo = new InfoVO();
			// infoVO = new inforVo(name, id, tel, gender, age, home);
		vo.setName(name);
		vo.setId(id);
		vo.setGender(gender);
		vo.setHome(home);
		vo.setTel(tel);
		vo.setAge(age);
		
		// 3. 모델의 insertInfo() 호출   // 실질적으로 데이터베이스에 넣어주는 역확
		try {
		model.insertInfo(vo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// InfoView 에 예외처리를 해야 고객화면에 예외 값이 출력됨.
		
		// 4. 화면의 입력값들을 지우기
		clearText();
	}
	
	void clearText() {
		tfname.setText(null);
		tfId.setText(null);
		tfTel.setText(null);
		tfAge.setText(null);
		tfGender.setText(null);
		tfHome.setText(null);
		
	}

	public static void main(String[] args) {

		InfoView info = new InfoView();
		info.addLayout();
		info.eventProc();

	}

}
