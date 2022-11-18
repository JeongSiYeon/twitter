package testForGuiui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.*;
import java.sql.*;


public class login
{
	private Image img=new ImageIcon(mainPage.class.getResource("../image/twitter.png")).getImage();


    public login()
    {
        JFrame jf = new JFrame();
        jf.setSize(400,450);
        jf.setLocationRelativeTo(null);
        jf.setTitle("login");
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

        final JPasswordField password_text = new JPasswordField();
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
        
        JLabel find_password = new JLabel("Forgot Password ");
        find_password.setSize(120,30);
        find_password.setLocation(260,190);
        find_password.setForeground(new Color(128,128,128));
        jf.add(find_password);
        
        find_password.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if (e.getButton() == 1)
                {
                	jf.setVisible(false);
                    new Find_pw();
                }
            }
        });

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

                JOptionPane message = new JOptionPane();

				Statement stmt = null;
				ResultSet rs = null;
				PreparedStatement pstm = null;
				try (Connection con = JDBC.connection())
				{
				    stmt = con.createStatement();
				    String s1 = "select user_id from user where user_id = \"" + input_id + "\" and password = \"" +input_password +  "\"";
				    rs = stmt.executeQuery(s1);

				    if(rs.next())
				    {
				        new mainPage(input_id);
				        jf.setVisible(false);
				    }
				    else
				    {
				        message.showMessageDialog(null, "Invalid login information!");
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