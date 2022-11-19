package testForGuiui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class pw_change
{
    public pw_change(String id)
    {  
        JFrame pw_change = new JFrame();
        pw_change.setSize(400,300);
        pw_change.setLocationRelativeTo(null);
        pw_change.setTitle("pw_change");
        pw_change.setLayout(null);
        Color b=new Color(255,255,255);
        pw_change.getContentPane().setBackground(b);

        Font font1 = new Font("Aharoni 굵게",Font.BOLD,17);
        Font font2 = new Font("Aharoni 굵게",Font.BOLD,12);
        Font font3 = new Font("Aharoni 굵게",Font.BOLD,13);

        ImageIcon icon = new ImageIcon("twitter.png");
        Image img = icon.getImage();
        Image changeImg = img.getScaledInstance(20, 15, Image.SCALE_SMOOTH);
        ImageIcon changeIcon = new ImageIcon(changeImg);

        JLabel twitter = new JLabel(changeIcon);
        twitter.setBounds(180,15,20,15);
        pw_change.add(twitter);

        JLabel main = new JLabel("Password Change");
        main.setSize(200,30);
        main.setLocation(90,40);
        main.setHorizontalAlignment(JLabel.CENTER);
        main.setFont(font1);
        pw_change.add(main);

        JLabel new_pw = new JLabel("New password : ");
        new_pw.setSize(130,30);
        new_pw.setLocation(50,90);
        new_pw.setForeground(new Color(128,128,128));
        new_pw.setFont(font2);
        new_pw.setHorizontalAlignment(JLabel.CENTER);

        pw_change.add(new_pw);

        JPasswordField new_pw_text = new JPasswordField();
        new_pw_text.setSize(150,30);
        new_pw_text.setLocation(170,90);

        pw_change.add(new_pw_text);

        JLabel new_pw_check = new JLabel("Confirm new password : ");
        new_pw_check.setSize(170,30);
        new_pw_check.setLocation(22,130);
        new_pw_check.setForeground(new Color(128,128,128));
        new_pw_check.setFont(font2);
        new_pw_check.setHorizontalAlignment(JLabel.LEFT);

        pw_change.add(new_pw_check);

        JPasswordField new_pw_check_text = new JPasswordField();
        new_pw_check_text.setSize(150,30);
        new_pw_check_text.setLocation(170,130);

        pw_change.add(new_pw_check_text);

        JButton done = new JButton("Password Change");
        done.setSize(350,30);
        done.setLocation(15,180);
        done.setFont(font3);
        done.setBackground(new Color(0,172,238));
        done.setForeground(new Color(255,255,255));
        done.setOpaque(true);
        done.setBorderPainted(false);
        pw_change.add(done);
 
        JButton back = new JButton("Back");
        back.setSize(350,30);
        back.setLocation(15,220);
        back.setFont(font3);
        back.setBackground(new Color(255,255,255));
        back.setForeground(new Color(0,172,238));
        pw_change.add(back);
        back.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try {
					new profilehome(id);
					pw_change.setVisible(false);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

        done.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent E)
            {
                String input_new_pw = new_pw_text.getText();
                String input_new_pw_check = new_pw_check_text.getText();

                JOptionPane message = new JOptionPane();
				

				Statement stmt = null;
				ResultSet rs = null;
				PreparedStatement pstm = null;
				try (Connection con = JDBC.connection())
				{
				    if(input_new_pw.equals(input_new_pw_check))
				    {
				        String s1 = "update user set password = \'" + input_new_pw + "\' where user_id = \'" + id + "\' ";
				        pstm = con.prepareStatement(s1);
				        pstm.executeUpdate();
				        message.showMessageDialog(null, "Password change complete!");
				    }
				    else
				    {
				        message.showMessageDialog(null, "Password doesn't match!");
				    }
				 
				}
				catch (SQLException e)
				{
				    e.printStackTrace();
				}
            }
        });

        pw_change.setVisible(true);
    }
}