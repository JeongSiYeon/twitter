package testForGuiui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class sign_up
{
    public sign_up()
    {
        JFrame signup = new JFrame();
        signup.setSize(400,450);
        signup.setLocation(700,300);
        signup.setTitle("회원가입");
        signup.setLayout(null);
        Color b=new Color(204,229,255);
        signup.getContentPane().setBackground(b);


        JLabel main = new JLabel("< 회원가입 >");
        main.setSize(130,30);
        main.setLocation(130,40);
        main.setHorizontalAlignment(JLabel.CENTER);
        Font myFont1 = new Font("Times", Font.BOLD, 20);

        main.setFont(myFont1);
        signup.add(main);

        JLabel id = new JLabel("아이디 : ");
        id.setSize(80,30);
        id.setLocation(70,90);
        id.setHorizontalAlignment(JLabel.CENTER);

        signup.add(id);

        JTextField id_text = new JTextField();
        id_text.setSize(150,30);
        id_text.setLocation(140,90);

        signup.add(id_text);

        JLabel password = new JLabel("비밀번호 : ");//안보이
        password.setSize(80,30);
        password.setLocation(70,140);
        password.setHorizontalAlignment(JLabel.CENTER);

        signup.add(password);

        JTextField password_text = new JTextField();
        password_text.setSize(150,30);
        password_text.setLocation(140,140);

        signup.add(password_text);

        JLabel name = new JLabel("이름 : ");
        name.setSize(80,30);
        name.setLocation(70,190);
        name.setHorizontalAlignment(JLabel.CENTER);

        signup.add(name);

        JTextField name_text = new JTextField();
        name_text.setSize(150,30);
        name_text.setLocation(140,190);

        signup.add(name_text);

        JLabel ph = new JLabel("전화번호 : ");
        ph.setSize(80,30);
        ph.setLocation(70,240);
        ph.setHorizontalAlignment(JLabel.CENTER);

        signup.add(ph);

        JTextField ph_text = new JTextField();
        ph_text.setSize(150,30);
        ph_text.setLocation(140,240);

        signup.add(ph_text);

        JLabel email = new JLabel("이메일 : ");
        email.setSize(80,30);
        email.setLocation(70,290);
        email.setHorizontalAlignment(JLabel.CENTER);

        signup.add(email);

        JTextField email_text = new JTextField();
        email_text.setSize(150,30);
        email_text.setLocation(140,290);

        signup.add(email_text);

        JButton done = new JButton("완료");
        done.setSize(70,30);
        done.setLocation(110,350);
        signup.add(done);

        JButton back = new JButton("취소");
        back.setSize(70,30);
        back.setLocation(200,350);
        signup.add(back);

        done.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent E)
            {
                String input_id = id_text.getText();
                String input_password = password_text.getText();
                String input_name = name_text.getText();
                String input_phonenumber = ph_text.getText();
                String input_email = email_text.getText();

                try
                {
                    JOptionPane message = new JOptionPane();
                    final String url = "jdbc:mysql://localhost/twittwe_db";
        		    final String user = "root";
        			final String passwd = "anselmochung24";
                    String date = "2000-06-24";
                    Connection connection = DriverManager.getConnection(url, user, passwd);

                    Statement stmt = null;
                    ResultSet rs = null;
                    PreparedStatement pstm = null;
                    try
                    {
                        stmt = connection.createStatement();
                        String s1 = "insert into user values ( \'" + input_id + "\', \'" + input_password + "\', \'" + input_name + "\', \'"
                                + input_phonenumber + "\', \'" + input_email + "\','',default)";
                        pstm = connection.prepareStatement(s1);
                        pstm.executeUpdate();
                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }
                    message.showMessageDialog(null, "회원가입 완료!");
                    signup.setVisible(false);
                    new login();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        });

        back.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                signup.setVisible(false);
                new login();
            }
        });
        signup.setVisible(true);
    }
}
