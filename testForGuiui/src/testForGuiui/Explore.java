package testForGuiui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class Explore extends JFrame implements ActionListener{	
	JFrame jframe = new JFrame();
	JPanel jpanel = new JPanel();
	
	// 팔로우 하기
	JList<String> searchList;
	DefaultListModel<String> model;
	static String selectedIdForFollow;
	JOptionPane message1, message2, message3;
	//
	
	JTabbedPane tPane1;
	JPanel pannelForAccount;
	JTextArea accountResultTArea;
	JButton btn1;
	JButton back;
	JMenuBar mb;
	JMenu menu;
	JMenuItem home, exp, prof, logout, exit;
	JButton tweet;
	JPanel panel;
	JTable table;
	Color b = new Color(204,229,255);
	Color c = new Color(102,153,204);
	Color d = new Color(0,172,238);
	Font font = new Font("Serif", Font.BOLD, 30);
	Font font1 = new Font("Serif", Font.BOLD, 15);
	Font font2 = new Font("맑은 고딕 ", Font.BOLD, 20);
	Font font3 = new Font("Aharoni 굵게",Font.BOLD,13);
	JOptionPane aa=new JOptionPane();
	
	static Statement stmt = null;
	static ResultSet rs = null;
    static PreparedStatement pstm = null;

    private Image img=new ImageIcon(mainPage.class.getResource("../image/twitter.png")).getImage();
    
	Explore(String id) throws SQLException
	{	 
		this.getContentPane().setBackground(b);
		
		
		// JList를 제어하기 위해 model객체 생성////////////////////////////////////////////
		model = new DefaultListModel();
		searchList = new JList(model);
		searchList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
					// 더블 클릭 처리 루틴
				if(e.getClickCount() == 2) {
					message1 = new JOptionPane();
					selectedIdForFollow = searchList.getSelectedValue().toString().trim(); // list를 클릭했을 때 데이터 가져오기
					int allowFollow = message1.showConfirmDialog(null, selectedIdForFollow + "님을 팔로우하시겠습니까?", "팔로우 여부", JOptionPane.YES_NO_CANCEL_OPTION);
					if(allowFollow == 0) {
						// 예 버튼 클릭됨
						// 자신은 팔로우할 수 없다.
						if(id.toString().equals(selectedIdForFollow)) {
							message2 = new JOptionPane();
							message2.showMessageDialog(null, "자신은 팔로우할 수 없습니다.");
							return;
						}
					
						Connection con = JDBC.connection();
						
						// 이미 팔로우 한 경우
						try {
							stmt = con.createStatement();
							String haveFollowed = "select follower_id from following where followed_id =\"" + selectedIdForFollow + "\" and follower_id = \'"+id+"\'" ;
							rs = stmt.executeQuery(haveFollowed);
							if(rs.next()) {
								stmt = con.createStatement();
								String result = rs.getString(1);
								if(result.equals(id)) {
									message2.showMessageDialog(null, "이미 팔로우했습니다.");
									return;
								}
							}
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
								
						String addFollower = "insert into following(follower_id,followed_id) value(\'"+id+"\', \'" + selectedIdForFollow + "\')"; 
						try {
							pstm = con.prepareStatement (addFollower);
							pstm.executeUpdate();
							message2.showMessageDialog(null, selectedIdForFollow + "님을 팔로우했습니다.");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} 
						
					}
				}					
			}
		});
		
		searchList.setBackground(SystemColor.activeCaption);

		model = (DefaultListModel) searchList.getModel();
		/////////////////////////////////////
				
		
		
	
		jframe.setTitle("Follow more");
		jframe.setSize(400,500);
		Dimension frameSize = getSize();
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((windowSize.width - frameSize.width)/2,
					(windowSize.height - frameSize.height) / 2);
		jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jframe.setVisible(true);
		
		// put TabbedPane
		tPane1 = new JTabbedPane();
		jframe.add(tPane1, BorderLayout.CENTER);
		
		 back = new JButton("Back");
         back.setBounds(45,350,100,25);;
         back.addActionListener(new ActionListener()
         {
             @Override
             public void actionPerformed(ActionEvent e)
             {
                 new Board(id);
                 jframe.setVisible(false);
             }
         });
		// 계정입력
		JTextField t1 = new JTextField();
		t1.setBounds(90,25,150,25);
		
		JLabel l1 = new JLabel("ID : ");
		l1.setBounds(50,21,70,30);
		
		btn1 = new JButton("search");
		btn1.setBounds(245,25,100,25);
		
		String getAccount = "select user_id from user";
		String getBoard = "select follower_id from following where followed_id =\""+id+"\"";
		 
		
		
		// pannelForAccount = CreateScrollPanel(getAccount); 
		pannelForAccount = new JPanel();
		pannelForAccount.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(50,50,300,300);   
		
		//JTextArea resultArea = new JTextArea();
		rs = getResult(getAccount);
		
		// put result
		while(rs.next()) {
			String result = rs.getString(1) + "\n\n";
			model.addElement(result);
			
		}
		
		scrollPane.setViewportView(searchList);    // 데이터가 아래로 내려갈 경우 아래로 자동으로 내려감
		
		pannelForAccount.add(scrollPane);
		pannelForAccount.setBackground(b);
		
		
		
		
		// pannelForAccount에 요소 붙이기
		pannelForAccount.add(scrollPane);
		pannelForAccount.add(l1);
		pannelForAccount.add(t1);
		pannelForAccount.add(btn1);
		pannelForAccount.add(back);
		JPanel pannelForBoard = CreateScrollPanel(getBoard);
		
		tPane1.add("계정", pannelForAccount);
		
		
		// 계정 검색 Action
		// 검색어를 포함! 하고 있는 id면 다 출력한다!
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String input_id = t1.getText();
				
				String getSearchResult = "select user_id from user where user_id LIKE " + "\"%" + input_id + "%\"";
				System.out.println("getSearchResule: " + getSearchResult);
				
				ArrayList searchStr = new ArrayList<>();
				
				try {
					rs = getResult(getSearchResult);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				// put result
				
				// 원래 JList에 있던 것 clear
				model.removeAllElements();
				try {
					while(rs.next()) {
						String result = rs.getString(1) + "\n";
						System.out.println(result);
						model.addElement(result);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				
				
				
				
				
				
				
				
				
				//JScrollPane scrollPane = new JScrollPane();
				//scrollPane.setViewportView(searchList);   
				//scrollPane.setBounds(50,50,300,300);      
				
				//pannelForAccount.add(scrollPane);
				
			}
		});
	}
	
	// get query(select) ResultSet
	private ResultSet getResult(String query) throws SQLException {
		Connection con = JDBC.connection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
			
		return rs;
	}
	
	
	private JPanel CreateScrollPanel(String query) throws SQLException {	
		ResultSet rs = null;
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		JTextArea resultArea = new JTextArea();
		rs = getResult(query);
		
		// put result
		while(rs.next()) {
			String result = rs.getString(1) + "\n\n";
			resultArea.append(result);
			
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(resultArea);    // 데이터가 아래로 내려갈 경우 아래로 자동으로 내려감
		scrollPane.setBounds(50,50,300,300);       //scroll에 resultArea를 붙였기에 이것을 크기설정
		
		panel.add(scrollPane);

		return panel;
	}
	
	private JLabel UserInfoPane(String query) throws SQLException {	
		String result = null;
		JLabel label = new JLabel();
		
		ResultSet rs = null;		
		rs = getResult(query);
		
		rs.next();
		result = rs.getString(1) + rs.getString(3) + rs.getString(6);
		label.setText(result);
		
		return label;
	}
	
	// Tab
		public JTabbedPane createTabbedPane(String id) throws SQLException {
			JTabbedPane t = new JTabbedPane();			
			
			String getAccount = "select user_id from user";
			String getBoard = "select follower_id from following where followed_id =\""+id+"\"";
			
			JPanel pannelForAccount = CreateScrollPanel(getAccount); 
			JPanel pannelForBoard = CreateScrollPanel(getBoard);
			
			t.add("계정", pannelForAccount);
		
			
			return t;
		}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == exit) {
			System.exit(0);
		}
	}
}
