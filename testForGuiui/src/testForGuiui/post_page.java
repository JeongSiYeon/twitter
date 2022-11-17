package testForGuiui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

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
	private Image prof_img = new ImageIcon(mainPage.class.getResource("../image/profile1.png")).getImage();
	private Image thumbUp_img = new ImageIcon(mainPage.class.getResource("../image/thumbUp.png")).getImage();
	private Image speechBubble_img = new ImageIcon(mainPage.class.getResource("../image/speechBubble.png")).getImage();

    public post_page(String post_idx,String user_id, int k)
    {
        JFrame post_page = new JFrame();
        post_page.setSize(500,500);
        post_page.setLocationRelativeTo(null);
        post_page.setTitle("post_page");
        post_page.setLayout(null);
        Color b=new Color(255,255,255);
        post_page.getContentPane().setBackground(b);
         
        Image changeImg = img.getScaledInstance(45, 37, Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(changeImg);

		JLabel twitter = new JLabel(changeIcon);
		twitter.setBounds(220,5,30,22);
		post_page.add(twitter);

		JOptionPane message = new JOptionPane();

		Font font1 = new Font("Aharoni 굵게",Font.PLAIN,7);
		Font font2 = new Font("Aharoni 굵게",Font.PLAIN,10);
		Font font3 = new Font("Aharoni 굵게",Font.BOLD,13);

		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pstm = null;

		try(Connection con = JDBC.connection())
		{
		   
		    Image changeImg2 = img2.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		    ImageIcon changeIcon2 = new ImageIcon(changeImg2);

		    JLabel like = new JLabel(changeIcon2);
		    like.setBounds(350,250,30,30);
		    post_page.add(like);

		    stmt = con.createStatement();

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

		    String header [] = {"Comment#", "Img1", "Img2", "Content", "Child_Comment", "Child_Comment#", "ThumbUp", "like#"};
		    
		    // 첫 번째 열에 prof_img추가하여 String->Object로 변경
		    Object contents[][] = new Object[100][8];

		    String comment_idx[] = new String[100];

		    String s3 = "select writer_id,content,date,comment_id from comment where post_id = " + post_idx + "";

		    rs = stmt.executeQuery(s3);
		    
		    
		    int i = 0;
		 
		    changeImg = prof_img.getScaledInstance(18, 20, Image.SCALE_SMOOTH);
			ImageIcon prof_icon = new ImageIcon(changeImg);
			
			changeImg = thumbUp_img.getScaledInstance(18, 20, Image.SCALE_SMOOTH);
			ImageIcon thumbUp_icon = new ImageIcon(changeImg);
			
			changeImg = speechBubble_img.getScaledInstance(15, 17, Image.SCALE_SMOOTH);
			ImageIcon speechBubble_icon = new ImageIcon(changeImg);
			
			// 댓글인 경우 - 대댓글인 경우 따로 처리 //////////////////////////////////////
		    while(rs.next())
		    {
		    	// 첫 번재 열에 사용자 prof_img추가
		    	comment_idx[i] = rs.getString("comment_id");
		    	contents[i][1] = prof_icon;
		    	contents[i][2] = "";
		        contents[i][3] = rs.getString("writer_id");
		        contents[i][4] = speechBubble_icon;
		        contents[i][5] = "";
		        contents[i][6] = thumbUp_icon;

		        i++;
		        contents[i][0] = "";
		        contents[i][1] = "";
		        contents[i][2] = "";
		        contents[i][3] = rs.getString("content");
		        contents[i][4] = "";
		        contents[i][5] = "";
		        contents[i][6] = "";
		        contents[i][7] = "";
		 
		        i++;
		        contents[i][0] = "";
		        contents[i][1] = "";
		        contents[i][2] = "";
		        contents[i][3] = rs.getString("date");
		        contents[i][4] = "";
		        contents[i][5] = "";
		        contents[i][6] = "";
		        contents[i][7] = "";
		        i++;
		        
		    }

		    for(int j = 0; j < i; j=j+3)
		    {
		        String s6 = "select count(l_c_id) from comment_like where comment_id = " + comment_idx[j] + "";
		        rs = stmt.executeQuery(s6);

		        while(rs.next())
		        {
		        	contents[j][0] = comment_idx[j];
		            contents[j][7] = rs.getString("count(l_c_id)");
		        }
		    }
		    ///////////////////////////////////////////////////////////

		    
		    DefaultTableModel dtmCommnetTable = new DefaultTableModel(contents, header) {
		    	// table 아이템 변경 막기
	              @Override
	              public boolean isCellEditable(int row, int  column) {
	                   return false;
	              }
		    };
		    
		    
		    
		    // 입력된 클래스가 그대로 Cell(Column)에  표현되도록 method를 Override해야 함
		    JTable comment_table = new JTable(dtmCommnetTable) {
		    	@Override
		        public Class<?> getColumnClass(int column) {
		             // 어떤 행이든 간에 입력된 column의 class를  반환하도록 한 것
		             return getValueAt(0, column).getClass();
		        }
	          };
	  		
	        //comment_table.getColumn("Img").setCellRenderer(celAlignLeft);
	        comment_table.getColumn("Comment#").setPreferredWidth(3);
	        comment_table.getColumn("Img1").setPreferredWidth(1);  
	        comment_table.getColumn("Img2").setPreferredWidth(1); 
	        comment_table.getColumn("Content").setPreferredWidth(350); 
	        comment_table.getColumn("Child_Comment").setPreferredWidth(1); 
	        comment_table.getColumn("Child_Comment#").setPreferredWidth(1); 
	        comment_table.getColumn("ThumbUp").setPreferredWidth(1);
	        comment_table.getColumn("like#").setPreferredWidth(1); 
		    
		    JScrollPane scrollpane = new JScrollPane(comment_table);
		    comment_table.setFont(font3);
		    scrollpane.setSize(450,150);
		    scrollpane.setLocation(20,310);
		    
		    // commnet_table 열(column), 행(row) 제거
		    comment_table.setShowVerticalLines(false);
		    comment_table.setShowHorizontalLines(false);
		    
		    // header 제거
		    comment_table.setTableHeader(null);
		    
		    post_page.add(scrollpane);
		    
		    comment_table.addMouseListener(new MouseAdapter()
		    {
		        @Override
		        public void mouseClicked(MouseEvent e)
		        {
		        	PreparedStatement pstm = null;
		            Statement stmt = null;
		            ResultSet rs = null;
		                
		        	// 대댓글 루틴
		        	if(comment_table.getSelectedColumn() == 4)		            	
		            {
		        		JOptionPane message = new JOptionPane();
						int isChildComment = message.showConfirmDialog(null, "대댓글을 작성 하시겠습니까?", "Reply", JOptionPane.YES_NO_CANCEL_OPTION);
						if(isChildComment == 0) {
							// 예 버튼 클릭됨
							String comment_id = (String) comment_table.getModel().getValueAt(comment_table.getSelectedRow(),0);
							String writer_id = (String) comment_table.getModel().getValueAt(comment_table.getSelectedRow(),3);
							String message2 = JOptionPane.showInputDialog("대댓글을 입력하세요");
							
							String s1 = "insert into child_comment (writer_id,comment_id,content) " +
	                                " values ( \'" + comment_id + "\', \'" + writer_id + "\',message2)";
							System.out.println(s1);
	                        try {
								pstm = con.prepareStatement(s1);
								pstm.executeUpdate();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
	                        
						}

							
		            }
		        	
		        	// 좋아요 루틴
		            if(comment_table.getSelectedColumn() == 6)		            	
		            {
		            	JOptionPane message = new JOptionPane();
		                String ci = (String) comment_table.getModel().getValueAt(comment_table.getSelectedRow(),0);
		                try
		                {
		                    stmt = con.createStatement();
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
		                        pstm = con.prepareStatement(s1);
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
		            post_page.setVisible(false);
		        	}
		        	else
		        	{
		        		new Board(user_id);
		        		post_page.setVisible(false);
		        	}
		            
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
		                pstm = con.prepareStatement(s1);
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
		                stmt = con.createStatement();
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
		                    pstm = con.prepareStatement(s1);
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
		
        post_page.setVisible(true);

    }
}