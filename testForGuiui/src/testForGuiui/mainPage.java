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
	Font font = new Font("Aharoni 굵게", Font.BOLD, 30);
	Font font1 = new Font("Aharoni 굵게", Font.BOLD, 15);
	Font font2 = new Font("Aharoni 굵게 ", Font.BOLD, 20);
	 Font font3 = new Font("Aharoni 굵게",Font.BOLD,12);
	JOptionPane aa=new JOptionPane();
	
	static String url = "jdbc:mysql://localhost/twittwe_db";
    static String user = "root";
	static String passwd = "anselmochung24";
	static Statement stmt = null;
	static ResultSet rs = null;
	static ResultSet rs2 = null;
    static PreparedStatement pstm = null;	
	
	
	private Image img2 =new ImageIcon(mainPage.class.getResource("../image/profile.png")).getImage();
	private Image img3 =new ImageIcon(mainPage.class.getResource("../image/calendar.png")).getImage();
	
	    mainPage(String id)
	    {
	    	
	       this.setSize(500,640);
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
	            	new Board(id);
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
						new profilehome(id);
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
			
			Image changeImg2 = img2.getScaledInstance(500, 162, Image.SCALE_SMOOTH);
	        ImageIcon changeIcon2 = new ImageIcon(changeImg2);

	        JLabel profile_image = new JLabel(changeIcon2);
	        profile_image.setBounds(0,0,500,162);
	        this.add(profile_image);
	  
	        JLabel jl = new JLabel("HOME");
	        jl.setFont(font1);
	        jl.setSize(200,30);
	        jl.setLocation(20,0);
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
                    JLabel un = new JLabel(id); 
        	        un.setFont(font2);
        	        un.setSize(100,50);
        	        un.setLocation(25,150);
        	        un.setHorizontalAlignment(JLabel.CENTER);
        	        this.add(un); 
        	        
        	        JLabel profile_name = new JLabel("@" + name);
                    profile_name.setSize(80,25);
                    profile_name.setLocation(30,190);
                    profile_name.setFont(font3);
                    profile_name.setForeground(new Color(128,128,128));
                    this.add(profile_name);
        	        
			           }
				}catch(SQLException E) {
					E.printStackTrace();
				}
			}catch(SQLException E) {
				E.printStackTrace();
			}
	        
	        Image changeImg3 = img3.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            ImageIcon changeIcon3 = new ImageIcon(changeImg3);
            JLabel calendar_image = new JLabel(changeIcon3);
            calendar_image.setBounds(365,102,20,20);
            this.add(calendar_image);
            this.setVisible(true);
	
	        
	        JLabel jk = new JLabel("Write a Post!");
	        jk.setFont(font3);
	        jk.setSize(100,40);
	        jk.setLocation(147,90);
	        jk.setHorizontalAlignment(JLabel.CENTER);
	        
	        this.add(jk); 
	        
	        
	        JTextField jt = new JTextField();
	        jt.setSize(345,100);
	        jt.setLocation(150,120);
	        
	        

	        this.add(jt);
	        
	        LocalDate now = LocalDate.now();
	        
	       
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	 
	        
	        String formatedNow = now.format(formatter);
	        JLabel time = new JLabel(formatedNow);
	        time.setSize(100,50);
	        time.setLocation(390,88);
	        time.setHorizontalAlignment(JLabel.CENTER);
	        
	        this.add(time);
	        
	        tweet = new JButton("TWEET");  
	        tweet.setSize(90,35);
	        tweet.setFont(font3);
	        tweet.setLocation(405,220);
	        tweet.setForeground(new Color(0,172,238));
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
			           int i = 0;
			           while(rs.next())
			           {
			        String fid = rs.getString("followed_id");
			        String s3 = "select writer_id, date, content from posts where writer_id = \'" + fid + "\' or writer_id = \'" + id + "\'";
					   stmt = con.createStatement();
			           rs2 = stmt.executeQuery(s3);
 
			           while(rs2.next())
			           {
			               data[i][0] = rs2.getString("writer_id");
			               data[i][1] = rs2.getString("date");
			               data[i][2] = rs2.getString("content");
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
	       
	       table.setRowHeight(40);
	       JScrollPane scroll = new JScrollPane(table);
	       scroll.setSize(480,300);
           scroll.setLocation(10,260);
	       
	       this.add(scroll);
	       
	       table.addMouseListener(new MouseAdapter () { 
	    	   @Override
	    	   public void mouseClicked(MouseEvent e) {
	    	   if (e.getButton() == 1) {
	    		   int row = table.getSelectedRow();
	    		   int col = table.getSelectedColumn();
	    		   
	    		   String roid = (String) table.getModel().getValueAt(row,0);
	    		   var content = table.getModel().getValueAt(row, 2);
	    		   try {
	    			   Connection con = DriverManager.getConnection(url, user, passwd);
	    		   try {
	    			   int k = 1;
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
	    		   }catch(SQLException E) {
	    			   E.printStackTrace();
	    		   }
	    		
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

