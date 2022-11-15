package testForGuiui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class post_page
{
	private Image img=new ImageIcon(mainPage.class.getResource("../image/twitter.png")).getImage();
	private Image img2=new ImageIcon(mainPage.class.getResource("../image/like.png")).getImage();

    public post_page(String post_idx,String user_id, int k)
    {
        JFrame post_page = new JFrame();
        post_page.setSize(500,500);
        post_page.setLocation(700,300);
        post_page.setTitle("post_page");
        post_page.setLayout(null);
        Color b=new Color(255,255,255);
        post_page.getContentPane().setBackground(b);
        
        try
        {
            
            Image changeImg = img.getScaledInstance(45, 37, Image.SCALE_SMOOTH);
            ImageIcon changeIcon = new ImageIcon(changeImg);

            JLabel twitter = new JLabel(changeIcon);
            twitter.setBounds(220,5,30,22);
            post_page.add(twitter);

            JOptionPane message = new JOptionPane();
            String url = "jdbc:mysql://localhost/twittwe_db";
            String userName = "root";
        	 String user_password = "anselmochung24";
            Connection connection = DriverManager.getConnection(url, userName, user_password);
            Font font3 = new Font("Aharoni 굵게",Font.BOLD,13);

            Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement pstm = null;

            try
            {
               
                Image changeImg2 = img2.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                ImageIcon changeIcon2 = new ImageIcon(changeImg2);

                JLabel like = new JLabel(changeIcon2);
                like.setBounds(350,250,30,30);
                post_page.add(like);

                stmt = connection.createStatement();

                String s4 = "select count(l_p_id) from post_like where post_id = " + post_idx + "";
                rs = stmt.executeQuery(s4);
                String count_like = "";

                while(rs.next())
                {
                    count_like = rs.getString("count(l_p_id)");
                }

                JLabel count_like_num = new JLabel(count_like);
                count_like_num.setSize(80,30);
                count_like_num.setLocation(325,270);
                count_like_num.setHorizontalAlignment(JLabel.CENTER);
                post_page.add(count_like_num);

                JButton post_like_button = new JButton("Like!");
                post_like_button.setSize(70,30);
                post_like_button.setLocation(390,257);
                post_like_button.setFont(font3);
                post_like_button.setBackground(new Color(255,255,255));
                post_like_button.setForeground(new Color(0,172,238));
                post_page.add(post_like_button);

                JLabel Writer = new JLabel("Writer : ");
                Writer.setSize(80,30);
                Writer.setLocation(10,40);
                Writer.setFont(font3);
                Writer.setForeground(new Color(128,128,128));
                Writer.setHorizontalAlignment(JLabel.CENTER);
                post_page.add(Writer);

                TextField new_comment = new TextField();
                new_comment.setSize(150,25);
                new_comment.setLocation(20,260);
                post_page.add(new_comment);

                JButton write_comment = new JButton("Write Comment");
                write_comment.setSize(150,25);
                write_comment.setLocation(180,260);
                write_comment.setFont(font3);
                write_comment.setBackground(new Color(0,172,238));
                write_comment.setForeground(new Color(255,255,255));
                write_comment.setOpaque(true);
                write_comment.setBorderPainted(false);
                post_page.add(write_comment);

                String user_idx = "";
                String post_content = "";
                String post_time = "";

                String s1 = "select writer_id,content,date from posts where post_id = " + post_idx + "";
                rs = stmt.executeQuery(s1);

                while(rs.next())
                {
                    user_idx = rs.getString("writer_id");
                    post_content = rs.getString("content");
                    post_time = rs.getString("date");
                }

                TextField writer_text = new TextField(user_idx);
                writer_text.setSize(80,25);
                writer_text.setLocation(100,45);
                writer_text.setFont(font3);
                writer_text.setEnabled(false);
                post_page.add(writer_text);

                JLabel content = new JLabel("Content : ");
                content.setSize(80,30);
                content.setLocation(10,80);
                content.setForeground(new Color(128,128,128));
                content.setHorizontalAlignment(JLabel.CENTER);
                content.setFont(font3);
                post_page.add(content);

                TextField content_text = new TextField(post_content);
                content_text.setSize(200,100);
                content_text.setLocation(100,90);
                content_text.setFont(font3);
                content_text.setEnabled(false);
                post_page.add(content_text);

                JLabel postT = new JLabel("Writing Time : ");
                postT.setSize(100,30);
                postT.setLocation(0,200);
                postT.setFont(font3);
                postT.setForeground(new Color(128,128,128));
                postT.setHorizontalAlignment(JLabel.CENTER);
                post_page.add(postT);

                TextField postT_text = new TextField(post_time);
                postT_text.setSize(125,25);
                postT_text.setLocation(100,205);
                postT_text.setFont(font3);
                postT_text.setEnabled(false);
                post_page.add(postT_text);


                String header [] = {"Writer","Content","Writing Time","like","comment_idx"};
                String contents[][] = new String[100][5];

                String comment_idx[] = new String[100];

                String s3 = "select writer_id,content,date,comment_id from comment where post_id = " + post_idx + "";

                rs = stmt.executeQuery(s3);
                int i = 0;

                while(rs.next())
                {
                    contents[i][0] = rs.getString("writer_id");
                    contents[i][1] = rs.getString("content");
                    contents[i][2] = rs.getString("date");
                    comment_idx[i] = rs.getString("comment_id");
                    i++;
                }

                for(int j = 0; j < i; j++)
                {
                    String s6 = "select count(l_c_id) from comment_like where comment_id = " + comment_idx[j] + "";
                    rs = stmt.executeQuery(s6);

                    while(rs.next())
                    {
                        contents[j][3] = rs.getString("count(l_c_id)");
                        contents[j][4] = comment_idx[j];
                    }
                }



                JTable comment_table = new JTable(contents,header);
                JScrollPane scrollpane = new JScrollPane(comment_table);
                comment_table.setFont(font3);
                scrollpane.setSize(450,150);
                scrollpane.setLocation(20,310);
                post_page.add(scrollpane);

                comment_table.addMouseListener(new MouseAdapter()
                {
                    @Override
                    public void mouseClicked(MouseEvent e)
                    {

                        if(e.getButton() == 1)
                        {
                            JOptionPane message = new JOptionPane();
                            PreparedStatement pstm = null;
                            Statement stmt = null;
                            ResultSet rs = null;

                            String ci = (String) comment_table.getModel().getValueAt(comment_table.getSelectedRow(),4);
                            try
                            {
                                stmt = connection.createStatement();
                                String s2 = "select liker_id from comment_like where comment_id = \'" + ci + "\' and liker_id = \'" + user_id + "\' ";
                                rs = stmt.executeQuery(s2);
                                if(rs.next())
                                {
                                    message.showMessageDialog(null, "You already did like!");
                                }
                                else
                                {
                                    String s1 = "insert into comment_like (comment_id,liker_id,create_at) " +
                                            " values ( \'" + ci + "\', \'" + user_id + "\',default)";
                                    pstm = connection.prepareStatement(s1);
                                    pstm.executeUpdate();
                                    message.showMessageDialog(null, "You did it like!");
                                    post_page.setVisible(false);
                                    new post_page(post_idx,user_id,k);

                                }
                            }
                            catch (SQLException E)
                            {
                                E.printStackTrace();
                            }





                        }
                    }
                });


                JButton back = new JButton("Back");
                back.setSize(80,25);
                back.setLocation(390,7);
                back.setFont(font3);
                back.setBackground(new Color(0,172,238));
                back.setForeground(new Color(255,255,255));
                back.setOpaque(true);
                back.setBorderPainted(false);
                post_page.add(back);

                back.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {	
                    	if(k == 1)
                    	{
                        new mainPage(user_id);
                    	}
                    	else
                    	{
                    		new Board(user_id);
                    	}
                        post_page.setVisible(false);
                    }
                });

                write_comment.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        PreparedStatement pstm = null;
                        try
                        {
                            String comment_text = new_comment.getText();

                            String s1 = "insert into comment (writer_id,post_id,content,num_of_likes,child_comment_id,date) " +
                                    " values ( \'" + user_id + "\', \'" + post_idx + "\', \'" + comment_text + "\',0,0,default)";
                            pstm = connection.prepareStatement(s1);
                            pstm.executeUpdate();
                            
                            post_page.setVisible(false);
                            new post_page(post_idx,user_id,k);

                        }
                        catch (SQLException E)
                        {
                            E.printStackTrace();
                        }

                    }
                });

                post_like_button.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        JOptionPane message = new JOptionPane();
                        PreparedStatement pstm = null;
                        Statement stmt = null;
                        ResultSet rs = null;
                        try
                        {
                            stmt = connection.createStatement();
                            String s2 = "select liker_id from post_like where post_id = \'" + post_idx + "\' and liker_id = \'" + user_id + "\' ";
                            rs = stmt.executeQuery(s2);
                            if(rs.next())
                            {
                                message.showMessageDialog(null, "You already did like!");
                            }
                            else
                            {
                                String s1 = "insert into post_like (post_id,liker_id,create_at) " +
                                        " values ( \'" + post_idx + "\', \'" +user_id + "\',default)";
                                pstm = connection.prepareStatement(s1);
                                pstm.executeUpdate();

                                post_page.setVisible(false);
                                new post_page(post_idx,user_id,k);
                            }


                        }
                        catch (SQLException E)
                        {
                            E.printStackTrace();
                        }

                    }

                });

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

        post_page.setVisible(true);

    }
}