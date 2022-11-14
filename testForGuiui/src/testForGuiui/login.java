package testForGuiui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;


public class login
{
	private Image img=new ImageIcon(mainPage.class.getResource("../image/twitter.png")).getImage();


    public login()
    {
        JFrame jf = new JFrame();
        jf.setSize(400,450);
        jf.setLocation(700,300);
        jf.setTitle("로그인");
        jf.setLayout(null);
        Color b=new Color(255,255,255);
        Color c = new Color(0,172,238);
        jf.getContentPane().setBackground(b);

        Font font = new Font("Aharoni 굵게",Font.BOLD,30);
        Font font2 = new Font("Aharoni 굵게",Font.BOLD,17);
        Font font3 = new Font("Aharoni 굵게",Font.BOLD,13);

        
        Image changeImg = img.getScaledInstance(50, 45, Image.SCALE_SMOOTH);
        ImageIcon changeIcon = new ImageIcon(changeImg);

        JLabel twitter = new JLabel(changeIcon);
        twitter.setBounds(10,15,50,45);
        jf.add(twitter);

        JLabel login_text = new JLabel("See what's happening in");
        login_text.setSize(400,50);
        login_text.setLocation(10,70);
        login_text.setFont(font);
        jf.add(login_text);


        JLabel login_text2 = new JLabel("the world right now");
        login_text2.setSize(400,50);
        login_text2.setLocation(10,100);
        login_text2.setFont(font);
        jf.add(login_text2);

        JLabel login_text3 = new JLabel("Join Twitter today.");
        login_text3.setSize(400,50);
        login_text3.setLocation(10,170);
        login_text3.setFont(font2);
        jf.add(login_text3);


        JLabel id = new JLabel("ID : ");
        id.setSize(30,30);
        id.setLocation(90,320);
        id.setHorizontalAlignment(JLabel.CENTER);
        id.setForeground(new Color(128,128,128));
        jf.add(id);

        final JTextField id_text = new JTextField();
        id_text.setSize(200,30);
        id_text.setLocation(120,320);
        jf.add(id_text);

        JLabel password = new JLabel("PASSWORD : ");
        password.setSize(90,30);
        password.setLocation(36,360);
        password.setForeground(new Color(128,128,128));
        jf.add(password);

        final JTextField password_text = new JTextField();
        password_text.setSize(200,30);
        password_text.setLocation(120,360);
        jf.add(password_text);

        JButton login = new JButton("Log In");
        login.setSize(350,35);
        login.setLocation(15,265);
        login.setFont(font3);
        login.setBackground(b);
        login.setForeground(c);
        jf.add(login);

        JButton signup = new JButton("Sign up");
        signup.setSize(350,35);
        signup.setLocation(15,220);
        signup.setFont(font3);
        signup.setBackground(new Color(0,172,238));
        signup.setForeground(b);
        signup.setOpaque(true);
        signup.setBorderPainted(false);
        jf.add(signup);

        jf.setVisible(true);

        signup.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                jf.setVisible(false);
                new sign_up();
            }
        });

        login.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent E)
            {
                String input_id = id_text.getText();
                String input_password = password_text.getText();

                try
                {
                    JOptionPane message = new JOptionPane();
                     String url = "jdbc:mysql://localhost/twittwe_db";
                    String userName = "root";
                	 String user_password = "anselmochung24";
                    Connection connection = DriverManager.getConnection(url, userName, user_password);

                    Statement stmt = null;
                    ResultSet rs = null;
                    PreparedStatement pstm = null;
                    try
                    {
                        stmt = connection.createStatement();
                        String s1 = "select user_id from user where user_id = \"" + input_id + "\" and password = \"" +input_password +  "\"";
                        rs = stmt.executeQuery(s1);

                        if(rs.next())
                        {
                            message.showMessageDialog(null, "로그인 성공!");
                            new mainPage(input_id);
                            jf.setVisible(false);
                        }
                        else
                        {
                            message.showMessageDialog(null, "잘못된 정보입니다");
                        }
                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }

                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        });

    }

    public static void main(String[] args)
    {
        new login();
    }
}