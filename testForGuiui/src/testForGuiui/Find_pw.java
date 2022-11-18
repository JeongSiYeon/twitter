package testForGuiui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Find_pw
{
	 public Find_pw()
	 {
		 JFrame find_pw = new JFrame();
		 find_pw.setSize(400,300);
		 find_pw.setLocationRelativeTo(null);
		 find_pw.setTitle("pw_change");
		 find_pw.setLayout(null);
		 Color b=new Color(255,255,255);
		 find_pw.getContentPane().setBackground(b);
		 
		 Font font1 = new Font("Aharoni 굵게",Font.BOLD,17);
	     Font font2 = new Font("Aharoni 굵게",Font.BOLD,12);
	     Font font3 = new Font("Aharoni 굵게",Font.BOLD,13);
		 
		 ImageIcon icon = new ImageIcon("twitter.png");
	     Image img = icon.getImage();
	     Image changeImg = img.getScaledInstance(20, 15, Image.SCALE_SMOOTH);
	     ImageIcon changeIcon = new ImageIcon(changeImg);

	     JLabel twitter = new JLabel(changeIcon);
	     twitter.setBounds(180,15,20,15);
	     find_pw.add(twitter);

	     JLabel main = new JLabel("Find Password");
	     main.setSize(200,30);
	     main.setLocation(90,40);
	     main.setHorizontalAlignment(JLabel.CENTER);
	     main.setFont(font1);
	     find_pw.add(main);
	     
	     JLabel id = new JLabel("Enter your ID : ");
	     id.setSize(130,30);
	     id.setLocation(50,90);
	     id.setForeground(new Color(128,128,128));
	     id.setFont(font2);
	     id.setHorizontalAlignment(JLabel.CENTER);

	     find_pw.add(id);

	     JTextField id_text = new  JTextField();
	     id_text.setSize(150,30);
	     id_text.setLocation(170,90);
	     
	     find_pw.add(id_text);

	     JLabel email = new JLabel("Enter your E-mail : ");
	     email.setSize(170,30);
	     email.setLocation(50,130);
	     email.setForeground(new Color(128,128,128));
	     email.setFont(font2);
	     email.setHorizontalAlignment(JLabel.LEFT);

	     find_pw.add(email);
	     
	     JTextField email_text = new JTextField();
	     email_text.setSize(150,30);
	     email_text.setLocation(170,130);
	     
	     find_pw.add(email_text);
	     
	     JButton done = new JButton("Find Password");
	     done.setSize(350,30);
	     done.setLocation(15,180);
	     done.setFont(font3);
	     done.setBackground(new Color(0,172,238));
	     done.setForeground(new Color(255,255,255));
	     done.setOpaque(true);
	     done.setBorderPainted(false);
	     find_pw.add(done);

	     JButton back = new JButton("Back");
	     back.setSize(350,30);
	     back.setLocation(15,220);
	     back.setFont(font3);
	     back.setBackground(new Color(255,255,255));
	     back.setForeground(new Color(0,172,238));
	     find_pw.add(back);
	     
	     back.addActionListener(new ActionListener()
	        {
	            @Override
	            public void actionPerformed(ActionEvent E)
	            {
	            	find_pw.setVisible(false);
	            	new login();
	            }
	        });
	     
	     done.addActionListener(new ActionListener()
	        {
	            @Override
	            public void actionPerformed(ActionEvent E)
	            {
	                String input_id = id_text.getText();
	                String input_email = email_text.getText();

	                JOptionPane message = new JOptionPane();
	                
					Statement stmt = null;
					ResultSet rs = null;
					PreparedStatement pstm = null;
					
					try (Connection con = JDBC.connection())
					{
						stmt = con.createStatement();
	                    String s2 = "select password from user where user_id = \'" + input_id + "\' and email = \'" + input_email + "\' ";
	                    rs = stmt.executeQuery(s2);
	                    if(rs.next())
	                    {
	                    	String pw = rs.getString("password");
	                        message.showMessageDialog(null, "Your password is '" + pw + "'");
	                    }
	                    else
	                    {
	                    	message.showMessageDialog(null, "Invalid information!");

	                    }
					   
					 
					}
					catch (SQLException e)
					{
					    e.printStackTrace();
					}
	            }
	        });
	     
	     find_pw.setVisible(true);
	 }
}
