package testForGuiui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.sql.*;

public class profilehome
{
	private Image img2 =new ImageIcon(mainPage.class.getResource("../image/profile.png")).getImage();
	private Image img3 =new ImageIcon(mainPage.class.getResource("../image/calendar.png")).getImage();
	
    public profilehome(String id) throws SQLException
    {
        JFrame profile = new JFrame();
        profile.setSize(505,620);
        profile.setLocationRelativeTo(null);
        profile.setTitle("profile");
        profile.setLayout(null);
        Color b=new Color(255,255,255);
        profile.getContentPane().setBackground(b);
        JTable table;
      
        
        Font font1 = new Font("Aharoni 굵게",Font.BOLD,22);
        Font font2 = new Font("Aharoni 굵게",Font.BOLD,12);
        Font font3 = new Font("Aharoni 굵게",Font.BOLD,13);
        
    	Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstm = null;
        
        Image changeImg2 = img2.getScaledInstance(500, 167, Image.SCALE_SMOOTH);
        ImageIcon changeIcon2 = new ImageIcon(changeImg2);

        JLabel profile_image = new JLabel(changeIcon2);
        profile_image.setBounds(0,0,500,167);
        profile.add(profile_image);

        try(Connection con = JDBC.connection())
        {
            JOptionPane message = new JOptionPane();
            
            


            stmt = con.createStatement();

            String s4 = "select user_id,name,create_at from user where user_id = \'" + id + "\'";
            rs = stmt.executeQuery(s4);

            String user_id = "";
            String name = "";
            String create_time = "";

            while(rs.next())
            {
                user_id = rs.getString("user_id");
                name = rs.getString("name");
                create_time = rs.getString("create_at");
            }

            String following = "";
            String follower = "";
            String s5 = "select count(follower_id) from following where follower_id = \'" + id + "\'";
            rs = stmt.executeQuery(s5);

            while(rs.next())
            {
                following = rs.getString("count(follower_id)");
            }

            String s6 = "select count(followed_id) from following where followed_id = \'" + id + "\'";
            rs = stmt.executeQuery(s6);

            while(rs.next())
            {
                follower = rs.getString("count(followed_id)");
            }

            JLabel profile_id = new JLabel(user_id);
            profile_id.setSize(200,25);
            profile_id.setLocation(20,170);
            profile_id.setFont(font1);
            profile.add(profile_id);

            JLabel profile_name = new JLabel("@" + name);
            profile_name.setSize(100,25);
            profile_name.setLocation(20,195);
            profile_name.setFont(font2);
            profile_name.setForeground(new Color(128,128,128));
            profile.add(profile_name);

            
            Image changeImg3 = img3.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            ImageIcon changeIcon3 = new ImageIcon(changeImg3);
            JLabel calendar_image = new JLabel(changeIcon3);
            calendar_image.setBounds(20,240,20,20);
            profile.add(calendar_image);

            JLabel profile_create = new JLabel("joined " + create_time);
            profile_create.setSize(200,25);
            profile_create.setLocation(47,238);
            profile_create.setFont(font3);
            profile_create.setForeground(new Color(128,128,128));
            profile.add(profile_create);

            JLabel following_num = new JLabel(following);
            following_num.setSize(80,25);
            following_num.setLocation(20,265);
            following_num.setFont(font3);
            profile.add(following_num);
            following_num.addMouseListener(new MouseAdapter()
            {  
            	@Override
                public void mouseClicked(MouseEvent e)
                {
                    if (e.getButton() == 1)
                    {
                        try {
							new Profile(id);
							profile.setVisible(false);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                    }
                }
            });
            JLabel following_text = new JLabel("Following");
            following_text.setSize(80,25);
            following_text.setLocation(30,265);
            following_text.setForeground(new Color(128,128,128));
            following_text.setFont(font3);
            profile.add(following_text);

            JLabel follower_num = new JLabel(follower);
            follower_num.setSize(80,25);
            follower_num.setLocation(115,265);
            follower_num.setFont(font3);
            profile.add(follower_num);

            JLabel follower_text = new JLabel("Followers");
            follower_text.setSize(80,25);
            follower_text.setLocation(123,265);
            follower_text.setForeground(new Color(128,128,128));
            follower_text.setFont(font3);
            profile.add(follower_text);
            
            JLabel todelete = new JLabel("* Click to delete your Post");
            todelete.setSize(300,25);
            todelete.setLocation(25,535);
            todelete.setForeground(new Color(128,128,128));
            todelete.setFont(font3);
            profile.add(todelete);
            
 
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        JButton CP = new JButton("Change Password");
        CP.setSize(150,25);
        CP.setLocation(320,120);
        CP.setFont(font3);
        CP.setBackground(new Color(255,255,255));
        CP.setForeground(new Color(0,172,238));
        profile.add(CP);

        JButton back = new JButton("HOME");
        back.setSize(90,25);
        back.setLocation(400,265);
        back.setFont(font3);
        back.setBackground(new Color(0,172,238));
        back.setForeground(new Color(255,255,255));
        back.setOpaque(true);
        back.setBorderPainted(false);
        profile.add(back);

        back.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new mainPage(id);
                profile.setVisible(false);
            }
        });

        CP.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new pw_change(id);
                profile.setVisible(false);
            }
        });
        
        String post[] = {"id", "create_time", "post_content"};
	       String data[][] = new String[100][3];
	      
	       
			
			try (Connection con = JDBC.connection()){
				
		        String s3 = "select writer_id, date, content from posts where writer_id = \'" + id + "\'";
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
	      
	       
	       table = new JTable(data, post) {
	    	   public boolean isCellEditable(int row, int column) {
	    		   return false;
	    	   }
	       };
	       
	       table.setRowHeight(40);
	       JScrollPane scroll = new JScrollPane(table);
	       scroll.setSize(480,235);
        scroll.setLocation(20,300);
	       
	       profile.add(scroll);
	       
	       table.addMouseListener(new MouseAdapter () { 
	    	   @Override
	    	   public void mouseClicked(MouseEvent e) {
	    	   if (e.getButton() == 1) {
	    		   JOptionPane message1;
	    		   message1 = new JOptionPane();
	    		   int row = table.getSelectedRow();
	    		   int col = table.getSelectedColumn();
	    		   int allow = message1.showConfirmDialog(null, "Sure to delete?", "Yes/No", JOptionPane.YES_NO_CANCEL_OPTION);
	    		   
	    		   if(allow == 0)
	    		   {
	    		   String roid = (String) table.getModel().getValueAt(row,0);
	    		   var content = table.getModel().getValueAt(row, 2);
	    		   
				   
 		   try (Connection con = JDBC.connection()){
 			   		PreparedStatement pstm = null;
				   String s3 = "delete from posts where writer_id = \'" + roid + "\' and content = \'" + content + "\'";
				   pstm = con.prepareStatement (s3);
				   pstm.executeUpdate();
					message1.showMessageDialog(null, "Deleted!"); 
					new profilehome(id);
					profile.setVisible(false);
					
 		   }catch(SQLException E) {
				   E.printStackTrace();
 		   }
	    		   }
	    	   } 
	    	   
	    	   }
	    	   });
	  
		   
	       
	    

       profile.setVisible(true);
    }

	private void add(JScrollPane scroll) {
		// TODO Auto-generated method stub
		
	}
}
