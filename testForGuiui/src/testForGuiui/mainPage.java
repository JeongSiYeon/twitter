package testForGuiui;
import java.time.LocalDate;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import javax.swing.table.*;
public class mainPage extends JFrame implements ActionListener{
		
	JMenuBar mb;
	JMenu menu;
	
	JMenuItem home;
	JMenuItem exp;
	JMenuItem prof;
	JMenuItem logout;
	JMenuItem exit;
	
	JButton tweet;
	JPanel panel;
	JTable table;
	Color b = new Color(204,229,255);
	Color c = new Color(102,153,204);
	Font font = new Font("Serif", Font.BOLD, 30);
	Font font1 = new Font("Serif", Font.BOLD, 15);
	Font font2 = new Font("맑은 고딕 ", Font.BOLD, 20);
	JOptionPane aa=new JOptionPane();
	
	static String url = "jdbc:mysql://localhost/twittwe_db";
    static String user = "root";
	static String passwd = "anselmochung24";
	static Statement stmt = null;
	static ResultSet rs = null;
	static ResultSet rs2 = null;
    static PreparedStatement pstm = null;
	
	private Image img=new ImageIcon(mainPage.class.getResource("../image/profile1.png")).getImage();
		
	    mainPage(String id)
	    {
	    	
	       this.setSize(1000,640);
	       this.setLocation(1000,500);
	       this.setTitle("Twitter");
	       this.setLayout(null);
	       this.getContentPane().setBackground(b);
	       
	 
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
	            	new Board();
	                setVisible(false);
				}
	            }
	        );
			prof.addActionListener(this);
			prof.addActionListener(new ActionListener()
	        {
	            @Override
	            public void actionPerformed(ActionEvent e)
	            {
	            	try {
						new Profile(id);
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
			
			
	  
	        JLabel jl = new JLabel("HOME");
	        jl.setFont(font);
	        jl.setSize(200,30);
	        jl.setLocation(0,0);
	        jl.setHorizontalAlignment(JLabel.CENTER);

	        this.add(jl); 
	        try {
				Connection con = DriverManager.getConnection(url, user, passwd);
				try {
					stmt = con.createStatement();
                    String s1 = "select name from user where user_id = \'" + id + "\' ";
                    rs = stmt.executeQuery(s1);
                    while(rs.next())
			           {
                    String name = rs.getString("name");
                    JLabel un = new JLabel(name); 
        	        un.setFont(font2);
        	        un.setSize(100,50);
        	        un.setLocation(80,140);
        	        un.setHorizontalAlignment(JLabel.CENTER);
        	        this.add(un); 
			           }
				}catch(SQLException E) {
					E.printStackTrace();
				}
			}catch(SQLException E) {
				E.printStackTrace();
			}
	        
	       
	        Image changeImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
	        ImageIcon changeIcon = new ImageIcon(changeImg);

	        JLabel profile = new JLabel(changeIcon);
	        profile.setBounds(80,50,100,100);
	        this.add(profile);
	        
	        JLabel jk = new JLabel("Write a Post!");
	        jk.setFont(font1);
	        jk.setSize(100,50);
	        jk.setLocation(200,10);
	        jk.setHorizontalAlignment(JLabel.CENTER);
	        
	        this.add(jk); 
	        
	        
	        JTextField jt = new JTextField();
	        jt.setSize(700,100);
	        jt.setLocation(200,50);
	      

	        this.add(jt);
	        
	        LocalDate now = LocalDate.now();
	        
	       
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	 
	        
	        String formatedNow = now.format(formatter);
	        JLabel time = new JLabel(formatedNow);
	        time.setSize(100,50);
	        time.setLocation(800,0);
	        time.setHorizontalAlignment(JLabel.CENTER);
	        
	        this.add(time);
	        
	        tweet = new JButton("TWEET");  
	        tweet.setSize(90,45);
	        tweet.setLocation(810,150);
	        this.add(tweet); 
	        this.setVisible(true);
	        tweet.addActionListener(this);;
	        tweet.addActionListener(new ActionListener()
	        {
	            @Override
	            public void actionPerformed(ActionEvent e)
	            {
	            	  String content = jt.getText();
	            	try {
						Connection con = DriverManager.getConnection(url, user, passwd);
						try {
							stmt = con.createStatement();
	                        String s1 = "insert into posts (content,writer_id,num_of_likes,date) values (\'" + content + "\' ,\'"+id+"\', default , default )";
	                        pstm = con.prepareStatement(s1);
							pstm.executeUpdate();
							
						}catch(SQLException E) {
							E.printStackTrace();
						}
					}catch(SQLException E) {
						E.printStackTrace();
					}
	            	aa.showMessageDialog(null, "Uploaded!"); 
	            }
	        });
	       
	       String post[] = {"id", "create_time", "post_content"};
	       String data[][] = new String[100][3];
	      
	       try {
				Connection con = DriverManager.getConnection(url, user, passwd);
				try {
					String s2 = "select followed_id from following where follower_id = \'" + id + "\'";
					   stmt = con.createStatement();
			           rs = stmt.executeQuery(s2);
			           while(rs.next())
			           {
			        String fid = rs.getString("followed_id");
			        String s3 = "select writer_id, date, content from posts where writer_id = \'" + fid + "\'";
					   stmt = con.createStatement();
			           rs = stmt.executeQuery(s3);

			           int i = 0;
			           while(rs.next())
			           {
			               data[i][0] = rs.getString("writer_id");
			               data[i][1] = rs.getString("date");
			               data[i][2] = rs.getString("content");
			               i++;
			           }
			           }
					
				}catch(SQLException E) {
					E.printStackTrace();
				}
			}catch(SQLException E) {
				E.printStackTrace();
			}
	      
	       
	       table = new JTable(data, post) {
	    	   public boolean isCellEditable(int row, int column) {
	    		   return false;
	    	   }
	       };
	       table.setSize(700,300);
	       table.setLocation(200,200);
	       JScrollPane scroll = new JScrollPane(table);
	       scroll.setPreferredSize(new Dimension(700,100));
	       this.add(scroll);
	       this.add(table);
	       table.addMouseListener(new MouseAdapter () { 
	    	   @Override
	    	   public void mouseClicked(MouseEvent e) {
	    	   if (e.getButton() == 1) {
	    		  // new post_page(eid);
	    		   setVisible(false);
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

