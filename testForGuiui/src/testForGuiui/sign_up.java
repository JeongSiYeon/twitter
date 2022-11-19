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
        signup.setSize(400,470);
        signup.setLocationRelativeTo(null);
        signup.setTitle("회원가입");
        signup.setLayout(null);
        Color b=new Color(255,255,255);
        signup.getContentPane().setBackground(b);

        ImageIcon icon = new ImageIcon("twitter.png");
        Image img = icon.getImage();
        Image changeImg = img.getScaledInstance(20, 15, Image.SCALE_SMOOTH);
        ImageIcon changeIcon = new ImageIcon(changeImg);
  
        JLabel twitter = new JLabel(changeIcon);
        twitter.setBounds(180,15,20,15);
        signup.add(twitter);

        JLabel main = new JLabel("Create your account");
        main.setSize(200,30);
        main.setLocation(90,40);
        main.setHorizontalAlignment(JLabel.CENTER);
        Font font2 = new Font("Aharoni 굵게",Font.BOLD,17);

        main.setFont(font2);
        signup.add(main);

        JLabel id = new JLabel("ID : ");
        id.setSize(80,30);
        id.setLocation(63,90);
        id.setHorizontalAlignment(JLabel.CENTER);
        id.setForeground(new Color(128,128,128));
        signup.add(id);

        JTextField id_text = new JTextField();
        id_text.setSize(150,30);
        id_text.setLocation(120,90);

        signup.add(id_text);

        JLabel password = new JLabel("PASSWORD : ");
        password.setSize(80,30);
        password.setLocation(30,140);
        password.setForeground(new Color(128,128,128));
        signup.add(password);

        JPasswordField password_text = new JPasswordField();
        password_text.setSize(150,30);
        password_text.setLocation(120,140);

        signup.add(password_text);

        JLabel name = new JLabel("NAME : ");
        name.setSize(80,30);
        name.setLocation(70,190);
        name.setForeground(new Color(128,128,128));
        signup.add(name);

        JTextField name_text = new JTextField();
        name_text.setSize(150,30);
        name_text.setLocation(120,190);

        signup.add(name_text);

        JLabel ph = new JLabel("PHONE : ");
        ph.setSize(80,30);
        ph.setLocation(63,240);
        ph.setForeground(new Color(128,128,128));

        signup.add(ph);

        JTextField ph_text = new JTextField();
        ph_text.setSize(150,30);
        ph_text.setLocation(120,240);

        signup.add(ph_text);

        JLabel email = new JLabel("E-MAIL : ");
        email.setSize(80,30);
        email.setLocation(65,290);
        email.setForeground(new Color(128,128,128));

        signup.add(email);

        JTextField email_text = new JTextField();
        email_text.setSize(150,30);
        email_text.setLocation(120,290);

        signup.add(email_text);

        JButton done = new JButton("Sign up");
        done.setSize(350,30);
        done.setLocation(15,340);
        done.setFont(font2);
        done.setBackground(new Color(0,172,238));
        done.setForeground(new Color(255,255,255));
        done.setOpaque(true);
        done.setBorderPainted(false);
        signup.add(done);

        JButton back = new JButton("Back");
        back.setSize(350,30);
        back.setLocation(15,385);
        back.setFont(font2);
        back.setBackground(new Color(255,255,255));
        back.setForeground(new Color(0,172,238));
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

                JOptionPane message = new JOptionPane();
				

				Statement stmt = null;
				ResultSet rs = null;
				PreparedStatement pstm = null;
				try(Connection con = JDBC.connection())
				{
				    stmt = con.createStatement();
				    String s1 = "insert into user values ( \'" + input_id + "\', \'" + input_password + "\', \'" + input_name + "\', \'"
				            + input_phonenumber + "\', \'" + input_email + "\','',default)";
				    pstm = con.prepareStatement(s1);
				    pstm.executeUpdate();
				
				}
				catch (SQLException e)
				{
				    e.printStackTrace();
				}
				message.showMessageDialog(null, "registration completed!");
				signup.setVisible(false);
				new login();
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
