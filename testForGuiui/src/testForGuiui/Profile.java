package testForGuiui;

import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Profile extends JFrame implements ActionListener{//profile home
	JMenuBar mb;
	JMenu Back;
	
	JMenuItem home;
	JMenuItem exp;
	JMenuItem prof;
	JMenuItem logout;
	JMenuItem exit;
	JOptionPane aa=new JOptionPane();
	Color b = new Color(204,229,255);
	JButton back;
	
	static Statement stmt = null;
	static ResultSet rs = null;
	
	public Profile(String eid) throws SQLException {
		this.setLocationRelativeTo(null);
		this.getContentPane().setBackground(b);
		setTitle("follow/following");
		setSize(400,500);
		
		JTabbedPane pane = createTabbedPane(eid); 
		add(pane, BorderLayout.CENTER);
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);	
		mb = new JMenuBar();
		Back = new JMenu("Back");
		home = new JMenuItem("Profile");
		
		
		mb.add(Back);
		Back.add(home);
		
		

		this.setJMenuBar(mb);
		
		home.addActionListener(this);
		home.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
            	try {
					new profilehome(eid);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                setVisible(false);
            }
        });
	
		  
		
		 
		
	}
	
	private JPanel CreateScrollPanel(String query) throws SQLException {	
		ResultSet rs = null;
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		JTextArea resultArea = new JTextArea();
		resultArea.setEnabled(false);
		
		try (Connection con = JDBC.connection()) {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			// put result
			while(rs.next()) {
				String result = rs.getString(1) + "\n\n";
				resultArea.append(result);
				
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(resultArea);   
		scrollPane.setBounds(50,50,300,300);       
		
		panel.add(scrollPane);

		return panel;
	}

	
	// Tab
	public JTabbedPane createTabbedPane(String eid) throws SQLException {
		JTabbedPane t = new JTabbedPane();	
		
		
		String getFollow = "select followed_id from following where follower_id =\""+eid+"\"";
		String getFollower = "select follower_id from following where followed_id =\""+eid+"\"";
		
		
		JPanel pannelForFollow = CreateScrollPanel(getFollow); 
		JPanel pannelForFollower = CreateScrollPanel(getFollower);
	
		
		
		t.add("Following", pannelForFollow);
		t.add("Follower", pannelForFollower);
		
		return t;
	}
	
	 @Override
		public void actionPerformed(ActionEvent e) {
			
			
			if(e.getSource() == exit)
			{
				System.exit(0);
			}
			
		
			
		}
}
