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
        jf.setSize(300,350);
        jf.setLocation(700,300);
        jf.setTitle("로그인");
        jf.setLayout(null);
        Color b=new Color(204,229,255);
        jf.getContentPane().setBackground(b);

        Image changeImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon changeIcon = new ImageIcon(changeImg);

        JLabel twitter = new JLabel(changeIcon);
        twitter.setBounds(100,30,100,100);
        jf.add(twitter);

        JLabel id = new JLabel("아이디 : ");
        id.setSize(80,30);
        id.setLocation(30,150);
        id.setHorizontalAlignment(JLabel.CENTER);

        jf.add(id);

        final JTextField id_text = new JTextField();
        id_text.setSize(130,30);
        id_text.setLocation(110,150);

        jf.add(id_text);

        JLabel password = new JLabel("비밀번호 : ");
        password.setSize(80,30);
        password.setLocation(30,190);
        jf.add(password);

        final JTextField password_text = new JTextField();
        password_text.setSize(130,30);
        password_text.setLocation(110,190);
        jf.add(password_text);

        JButton login = new JButton("로그인");
        login.setSize(90,45);
        login.setLocation(40,240);
        jf.add(login); //jf프레임에 jb를 넣는다.

        JButton signup = new JButton("회원가입");
        signup.setSize(90,45);
        signup.setLocation(150,240);
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
                    final String url = "jdbc:mysql://localhost/twittwe_db";
        		    final String user = "root";
        			final String passwd = "anselmochung24";
                    Connection connection = DriverManager.getConnection(url, user, passwd);

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
                            jf.setVisible(false);
                            new mainPage(input_id);
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
       // new post_page("3");
    	new login();
    }
}