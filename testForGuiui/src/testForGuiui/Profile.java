package testForGuiui;

import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Profile extends JFrame implements ActionListener{//profile home
	JMenuBar mb;
	JMenu menu;
	
	JMenuItem home;
	JMenuItem exp;
	JMenuItem prof;
	JMenuItem logout;
	JMenuItem exit;
	JOptionPane aa=new JOptionPane();
	Color b = new Color(204,229,255);
	
	public Profile(String eid) throws SQLException {
		 this.getContentPane().setBackground(b);
		setTitle("Profile");
		setSize(750,500);
		Dimension frameSize = getSize();
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((windowSize.width - frameSize.width)/2,
				(windowSize.height - frameSize.height) / 2);
		
		JTabbedPane pane = createTabbedPane(eid); 
		add(pane, BorderLayout.CENTER);
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);	
		mb = new JMenuBar();
		menu = new JMenu("MENU");
		home = new JMenuItem("HOME");
		exp = new JMenuItem("#EXPLORE");
		prof = new JMenuItem("PROFILE");
		logout = new JMenuItem("LOGOUT");
		exit = new JMenuItem("EXIT");
		
		mb.add(menu);
		menu.add(home);
		menu.add(exp);
		menu.add(prof);
		menu.add(logout);
		menu.add(exit);
		

		this.setJMenuBar(mb);
		
		home.addActionListener(this);
		home.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new mainPage(eid);
                setVisible(false);
            }
        });
		exp.addActionListener(this);
		prof.addActionListener(this);
		prof.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
            	try {
					new Profile(eid);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				setVisible(false);
			}
            }
        );
		logout.addActionListener(this);
		logout.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
            	aa.showMessageDialog(null, "Are you Sure to logout?");
            	new login();
				setVisible(false);
			}
            }
        );
		exit.addActionListener(this);
		
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
		scrollPane.setViewportView(resultArea);   
		scrollPane.setBounds(50,50,300,300);       
		
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
	public JTabbedPane createTabbedPane(String eid) throws SQLException {
		JTabbedPane t = new JTabbedPane();	
		
		
		String getFollow = "select followed_id from following where follower_id =\""+eid+"\"";
		String getFollower = "select follower_id from following where followed_id =\""+eid+"\"";
		String getUserInfo = "select * from user where user_id =\""+eid+"\"";
		
		JPanel pannelForFollow = CreateScrollPanel(getFollow); 
		JPanel pannelForFollower = CreateScrollPanel(getFollower);
		JLabel userLabel = UserInfoPane(getUserInfo);
		
		t.add("profile home", userLabel);
		t.add("팔로우", pannelForFollow);
		t.add("팔로워", pannelForFollower);
		
		return t;
	}
	
	// get query(select) ResultSet
	private ResultSet getResult(String query) throws SQLException {
		Connection con = JDBC.connection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		return rs;
	}
	
	 @Override
		public void actionPerformed(ActionEvent e) {
			
			
			if(e.getSource() == exit)
			{
				System.exit(0);
			}
			
		
			
		}
}
