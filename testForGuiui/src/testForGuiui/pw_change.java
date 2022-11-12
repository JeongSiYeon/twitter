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
        pw_change.setLocation(700,300);
        pw_change.setTitle("회원가입");
        pw_change.setLayout(null);
        Color b=new Color(204,229,255);
        pw_change.getContentPane().setBackground(b);


        JLabel main = new JLabel("< 비밀번호 변경 >");
        main.setSize(170,30);
        main.setLocation(110,40);
        main.setHorizontalAlignment(JLabel.CENTER);
        Font myFont1 = new Font("Times", Font.BOLD, 20);
        main.setFont(myFont1);
        pw_change.add(main);

        JLabel new_pw = new JLabel("새 비밀번호 : ");
        new_pw.setSize(80,30);
        new_pw.setLocation(70,90);
        new_pw.setHorizontalAlignment(JLabel.CENTER);

        pw_change.add(new_pw);

        JTextField new_pw_text = new JTextField();
        new_pw_text.setSize(150,30);
        new_pw_text.setLocation(150,90);

        pw_change.add(new_pw_text);

        JLabel new_pw_check = new JLabel("새 비밀번호 확인 : ");
        new_pw_check.setSize(100,30);
        new_pw_check.setLocation(45,150);
        new_pw_check.setHorizontalAlignment(JLabel.LEFT);

        pw_change.add(new_pw_check);

        JTextField new_pw_check_text = new JTextField();
        new_pw_check_text.setSize(150,30);
        new_pw_check_text.setLocation(150,150);

        pw_change.add(new_pw_check_text);

        JButton done = new JButton("완료");
        done.setSize(70,30);
        done.setLocation(110,200);
        pw_change.add(done);

        JButton back = new JButton("취소");
        back.setSize(70,30);
        back.setLocation(200,200);
        pw_change.add(back);

        done.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent E)
            {
                String input_new_pw = new_pw_text.getText();
                String input_new_pw_check = new_pw_check_text.getText();

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
                        if(input_new_pw.equals(input_new_pw_check))
                        {
                            String s1 = "update user set password = \'" + input_new_pw + "\' where user_id = \'" + id + "\' ";
                            pstm = connection.prepareStatement(s1);
                            pstm.executeUpdate();
                            message.showMessageDialog(null, "비밀번호 변경완료!");
                        }
                        else
                        {
                            message.showMessageDialog(null, "비밀번호가 일치하지 않습니다!");
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

        pw_change.setVisible(true);
    }
}
