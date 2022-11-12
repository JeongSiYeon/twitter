package testForGuiui;
import java.time.LocalDate;

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
	JPanel panel;
	JTable table;
	JTable table1;
	Color b = new Color(204,229,255);
	Color c = new Color(102,153,204);
	Font font = new Font("Serif", Font.BOLD, 30);
	Font font1 = new Font("Aharoni 굵게", Font.BOLD, 15);
	Font font2 = new Font("Aharoni 굵게 ", Font.BOLD, 20);
	JOptionPane aa=new JOptionPane();
	
	static String url = "jdbc:mysql://localhost/twittwe_db";
    static String user = "root";
	static String passwd = "anselmochung24";
	static Statement stmt = null;
	static ResultSet rs = null;
	static ResultSet rs2 = null;
    static PreparedStatement pstm = null;
    private Image img=new ImageIcon(mainPage.class.getResource("../image/twitter.png")).getImage();
    private Image sear=new ImageIcon(mainPage.class.getResource("../image/search.png")).getImage();
		
	    Board(String id)
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
			
			
			Image changeImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
	        ImageIcon changeIcon = new ImageIcon(changeImg);

	        JLabel twitter = new JLabel(changeIcon);
	        twitter.setBounds(80,50,100,100);
	        this.add(twitter);
	  
	        JLabel jl = new JLabel("#Explore Board");
	        jl.setFont(font2);
	        jl.setSize(300,100);
	        jl.setLocation(0,0);
	        jl.setHorizontalAlignment(JLabel.CENTER);

	        this.add(jl); 
	        
    
	        
	        JLabel jk = new JLabel("Search ID: ");
	        jk.setFont(font1);
	        jk.setSize(100,200);
	        jk.setLocation(410,20);
	       
	        
	        this.add(jk); 
	        
	        
	        JTextField jt = new JTextField();
	        jt.setSize(100,25);
	        jt.setLocation(500,107);
	      

	        this.add(jt);
	        
	        
	        LocalDate now = LocalDate.now();
	        
	       
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	 
	        
	        String formatedNow = now.format(formatter);
	        JLabel time = new JLabel(formatedNow);
	        time.setSize(100,50);
	        time.setLocation(800,0);
	        time.setHorizontalAlignment(JLabel.CENTER);
	        
	        this.add(time);
	        
	        Image changeImg2 = sear.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
	        ImageIcon changeIcon2 = new ImageIcon(changeImg2);
	        
	        search = new JButton(changeIcon2);  
	        search.setSize(25,25);
	        search.setLocation(600,107);
	        this.add(search); 
	        this.setVisible(true);
	        search.addActionListener(this);;
	        String found[] = {"id", "name", "post_content"};
		      String fdata[][] = new String[100][3];
	        search.addActionListener(new ActionListener()
	        {
	            @Override
	            public void actionPerformed(ActionEvent e)
	            {
	            	String sid = jt.getText();
	            	try {
						Connection con = DriverManager.getConnection(url, user, passwd);
						try {
							String s2 = "select user_id, name, create_at from user where user_id = \'" + sid + "\'";
							   stmt = con.createStatement();
					           rs = stmt.executeQuery(s2);
					           int i = 0;
							while(rs.next()) {
								fdata[i][0] = rs.getString("user_id");
					            fdata[i][1] = rs.getString("name");
					            fdata[i][2] = rs.getString("create_at");
					            i++;
					            aa.showMessageDialog(null, "Founded!"); 
							}
							
						}catch(SQLException E) {
							E.printStackTrace();
						}
					}catch(SQLException E) {
						E.printStackTrace();
					}
	            	
	            }
	        });
	        table1 = new JTable(fdata, found) {
		    	   public boolean isCellEditable(int row, int column) {
		    		   return false;
		    	   }
		       };
		       table1.setSize(700,20);
		       table1.setLocation(200,150);
		       JScrollPane scroll1 = new JScrollPane(table1);
		       scroll1.setPreferredSize(new Dimension(700,100));
		       this.add(scroll1);
		       this.add(table1);
		       
		       String post[] = {"id", "create_time", "post_content"};
			   String data[][] = new String[100][3];
			     
		        table1.addMouseListener(new MouseAdapter () { 
			    	   @Override
			    	   public void mouseClicked(MouseEvent e) {
			    	   if (e.getButton() == 1) {
			    		   try {
			   				Connection con = DriverManager.getConnection(url, user, passwd);
			   				try {
			   					String sid = fdata[0][0];
			   			        String s3 = "select writer_id, date, content from posts where writer_id = \'" + sid + "\'";
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
			   			           
			   					
			   				}catch(SQLException E) {
			   					E.printStackTrace();
			   				}
			   			}catch(SQLException E) {
			   				E.printStackTrace();
			   			}
			    	   } 
			    	   }
			    	   });
		        
		       JLabel list = new JLabel("User's Board");
		        list.setFont(font1);
		        list.setSize(300,20);
		        list.setLocation(100,175);
		        list.setHorizontalAlignment(JLabel.CENTER);

		        this.add(list); 
		        
		        
	      
	       
	      
	       
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

