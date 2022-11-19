package testForGuiui;
import java.time.LocalDate;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import javax.swing.table.*;
public class Board extends JFrame implements ActionListener{
		
	JMenuBar mb;
	JMenu menu;
	
	JMenuItem home;
	JMenuItem exp;
	JMenuItem prof;
	JMenuItem logout;
	JMenuItem exit;
	
	JButton search;
	JButton Explore;
	JPanel panel;
	JTable table;
	JTable table1;
	
	JOptionPane optionPane = new JOptionPane();

	static Statement stmt = null;
	static ResultSet rs = null;
	static ResultSet rs2 = null;
    static PreparedStatement pstm = null;
    
	Color b = new Color(204,229,255);
	Color c = new Color(102,153,204);
	Font font = new Font("Serif", Font.BOLD, 30);
	Font font1 = new Font("Aharoni 굵게", Font.BOLD, 15);
	Font font2 = new Font("Aharoni 굵게 ", Font.BOLD, 20);
    
    private Image img=new ImageIcon(mainPage.class.getResource("../image/twitter.png")).getImage();
    private Image sear=new ImageIcon(mainPage.class.getResource("../image/search.png")).getImage();
    private Image img3 =new ImageIcon(mainPage.class.getResource("../image/calendar.png")).getImage();	
    
	Board(String id) {
		// frame setting
		this.setSize(590,640);
	    this.setLocationRelativeTo(null);
	    this.setTitle("Twitter");
	    this.setLayout(null);
	    this.getContentPane().setBackground(b);
	    
	    // Menu Bar
	    mb = new JMenuBar();
	    
	    // Menu Items
	    menu = new JMenu("MENU");
		home = new JMenuItem("HOME");
		exp = new JMenuItem("#EXPLORE");
		prof = new JMenuItem("PROFILE");
		logout = new JMenuItem("LOGOUT");
		exit = new JMenuItem("EXIT");
		
		// Menu and Menu Bar setting
		mb.add(menu);
		menu.add(home);
		menu.add(exp);
		menu.add(prof);
		menu.add(logout);
		menu.add(exit);

		this.setJMenuBar(mb);
			
		home.addActionListener(this);
		home.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				new mainPage(id);
				setVisible(false);
			}
		});
			
		exp.addActionListener(this);
		exp.addActionListener(new ActionListener()
	    {
			@Override
	        public void actionPerformed(ActionEvent e)
	        {
				new Board(id);
	            setVisible(false);
	        }
	    });
			
		prof.addActionListener(this);
		prof.addActionListener(new ActionListener()
	    {
			@Override
			public void actionPerformed(ActionEvent e)
	        {
				try {
					new profilehome(id);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				setVisible(false);
			}
	    });
			
		logout.addActionListener(this);
		logout.addActionListener(new ActionListener()
	    {
			@Override
	        public void actionPerformed(ActionEvent e)
	        {
				optionPane.showMessageDialog(null, "Are you Sure to logout?");            	
				new login();
				setVisible(false);
			}
	    });
		
		exit.addActionListener(this);

		
		Image changeImg = img.getScaledInstance(50, 45, Image.SCALE_SMOOTH);
	    ImageIcon changeIcon = new ImageIcon(changeImg);
	    
	    JLabel twitter = new JLabel(changeIcon);
	    twitter.setBounds(220,26,50, 45);
	    this.add(twitter);
	  
	    JLabel jl = new JLabel("#Explore Board");
	    jl.setFont(font2);
	    jl.setSize(250,100);
	    jl.setLocation(0,0);
	    jl.setHorizontalAlignment(JLabel.CENTER);
	    this.add(jl); 
	        
	    Image changeImg3 = img3.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        ImageIcon changeIcon3 = new ImageIcon(changeImg3);
        JLabel calendar_image = new JLabel(changeIcon3);
        calendar_image.setBounds(50,80,18,18);
        this.add(calendar_image);
        this.setVisible(true);
	        
	    JLabel jk = new JLabel("Search ID: ");
	    jk.setFont(font1);
	    jk.setSize(100,200);
	    jk.setLocation(50,20);
	    this.add(jk); 
	        
	    JTextField jt = new JTextField();
	    jt.setSize(100,25);
	    jt.setLocation(140,107);
	    this.add(jt);
	        
	    LocalDate now = LocalDate.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	    String formatedNow = now.format(formatter);
	    JLabel time = new JLabel(formatedNow);
	    time.setSize(100,50);
	    time.setLocation(70,65);
	    time.setHorizontalAlignment(JLabel.CENTER);
	    this.add(time);
	        
	    Image changeImg2 = sear.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
	    ImageIcon changeIcon2 = new ImageIcon(changeImg2);
	    search = new JButton(changeIcon2);  
	    search.setSize(20,20);
	    search.setLocation(240,108);
	    this.add(search); 
	    this.setVisible(true);
	    
	    
	    String found[] = {"id", "name", "post_content"};
		String fdata[][] = new String[100][3];
		      
		/*상대방 보드 출*/
		String post[] = {"id", "create_time", "post_content"};
		String data[][] = new String[100][3];
		
	    search.addActionListener(this);       
	    search.addActionListener(new ActionListener() {
	    	@Override
	        public void actionPerformed(ActionEvent e) {
	    		String sid = jt.getText();
				try (Connection con = JDBC.connection()){
						
					String s2 = "select user_id, name, create_at from user where user_id = \'" + sid + "\'";
					stmt = con.createStatement();
					rs = stmt.executeQuery(s2);
					
					int i = 0;
					while(rs.next()) { // 사용자 검색 테이블 
						fdata[i][0] = rs.getString("user_id");
					    fdata[i][1] = rs.getString("name");
					    fdata[i][2] = rs.getString("create_at");
					    System.out.println("user_id: " + fdata[i][0] + "name: " + fdata[i][1] + "create_at: " + fdata[i][2]);
					    i++;
					    
					    optionPane.showMessageDialog(null, "Found!"); 
					    String cnt= "select count(*) from posts where writer_id = \'" + sid + "\' ";
					    rs = stmt.executeQuery(cnt);
					    
					    while(rs.next())
					    {
					    String num = rs.getString("count(*)");
					    String s3 = "select writer_id, date, content from posts where writer_id = \'" + sid + "\'";
					    stmt = con.createStatement();
					    rs = stmt.executeQuery(s3);
					    
					    	int n = Integer.parseInt(num);
					    	int k = n-1;
					
					    while(rs.next()) {  //사용자 검색 게시글 출력 테이블  
					    	data[k][0] = rs.getString("writer_id");
					        data[k][1] = rs.getString("date");
					        data[k][2] = rs.getString("content");
					        k--;
					    }
					        
					    for(int j = n; j < 100; j++) {
					    	for(int p = 0; p < 3; p++) {
					    		data[j][p] = " ";
					        }
					    }
					} 
					}
				} catch(SQLException E) {
						E.printStackTrace(); 
				}
	        }
	   });
	    
	    JScrollPane scroll1 = new JScrollPane(table1);
		scroll1.setPreferredSize(new Dimension(700,100));
		this.add(scroll1);	        
	        
	   table1 = new JTable(fdata, found) {
		   public boolean isCellEditable(int row, int column) {
			   return false;
		   }
		};
		
		this.add(table1);
		table1.setSize(490,20);
		table1.setLocation(50,150);
		table1.addMouseListener(new MouseAdapter () { 
		@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 1) {
					String ssid = data[0][0];
		    		try {
						new SCprofilehome(id, ssid);
						setVisible(false);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
		    		   
		    	} 
		    }
		});
		       JLabel list = new JLabel("User's Board");
		        list.setFont(font1);
		        list.setSize(250,20);
		        list.setLocation(50,175);
		        this.add(list); 
		    
		       table = new JTable(data, post) {
		    	   public boolean isCellEditable(int row, int column) {
		    		   return false;
		    	   }
		       };
		       table.setRowHeight(40); 
		       JScrollPane scroll = new JScrollPane(table);
		       scroll.setSize(490,300);
	           scroll.setLocation(50,200);
		       this.add(scroll);
		      
	           table.addMouseListener(new MouseAdapter () { 
	    	   @Override
	    	   public void mouseClicked(MouseEvent e) {
	    	   if (e.getButton() == 1) {
	    		   int row = table.getSelectedRow();
	    		   int col = table.getSelectedColumn();
	    		   
	    		   String roid = (String) table.getModel().getValueAt(row,0);
	    		   var content = table.getModel().getValueAt(row, 2);
	    		   
 		   try (Connection con = JDBC.connection()){
				   int k = 2;
				   String s3 = "select post_id from posts where writer_id = \'" + roid + "\' and content = \'" + content + "\'";
				   stmt = con.createStatement();
				   rs = stmt.executeQuery(s3);
				   
				   while(rs.next()) {
					   String poid = rs.getString("post_id");
					   new post_page(poid, id, k); //여기에 포스트 페이지  
					   setVisible(false);
				   }				 
 		   }catch(SQLException E) {
				   E.printStackTrace();
 		   }
	    	   } 
	    	   }
	    	   });
	       
	       
	       	Explore = new JButton("Follow more");  
	       	Explore.setSize(130,30);
	       	Explore.setLocation(50,510);
	       	Explore.setFont(font1);
	       	Explore.setForeground(new Color(0,172,238));
	        this.add(Explore); 
	        this.setVisible(true);
	        Explore.addActionListener(this);
	        
	        Explore.addActionListener(new ActionListener() {
	        	@Override
                public void actionPerformed(ActionEvent e)
                {
                    try {
						new Explore(id);
						setVisible(false);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
                }
	        });
	    }
	    @Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == exit)
			{
				System.exit(0);
			}		
		}
	}