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

public class SCprofilehome
{
	private Image img2 =new ImageIcon(mainPage.class.getResource("../image/profile.png")).getImage();
	private Image img3 =new ImageIcon(mainPage.class.getResource("../image/calendar.png")).getImage();
	
    public SCprofilehome(String id, String ssid) throws SQLException
    {
        JFrame profile = new JFrame();
        profile.setSize(500,350);
        profile.setLocationRelativeTo(null);
        profile.setTitle("profile");
        profile.setLayout(null);
        Color b=new Color(255,255,255);
        profile.getContentPane().setBackground(b);

        Font font1 = new Font("Aharoni 굵게",Font.BOLD,22);
        Font font2 = new Font("Aharoni 굵게",Font.BOLD,12);
        Font font3 = new Font("Aharoni 굵게",Font.BOLD,13);
        
       
        Image changeImg2 = img2.getScaledInstance(500, 162, Image.SCALE_SMOOTH);
        ImageIcon changeIcon2 = new ImageIcon(changeImg2);

        JLabel profile_image = new JLabel(changeIcon2);
        profile_image.setBounds(0,0,500,162);
        profile.add(profile_image);

        try (Connection con = JDBC.connection())
        {
            JOptionPane message = new JOptionPane();
            

            Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement pstm = null;

            stmt = con.createStatement();

            String s4 = "select user_id,name,create_at from user where user_id = \'" + ssid + "\'";
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
            String s5 = "select count(follower_id) from following where follower_id = \'" + ssid + "\'";
            rs = stmt.executeQuery(s5);

            while(rs.next())
            {
                following = rs.getString("count(follower_id)");
            }

            String s6 = "select count(followed_id) from following where followed_id = \'" + ssid + "\'";
            rs = stmt.executeQuery(s6);

            while(rs.next())
            {
                follower = rs.getString("count(followed_id)");
            }

            JLabel profile_id = new JLabel(user_id);
            profile_id.setSize(100,25);
            profile_id.setLocation(20,170);
            profile_id.setFont(font1);
            profile.add(profile_id);

            JLabel profile_name = new JLabel("@" + name);
            profile_name.setSize(80,25);
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

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }


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
                new Board(id);
                profile.setVisible(false);
            }
        });

       

       profile.setVisible(true);
    }
}
