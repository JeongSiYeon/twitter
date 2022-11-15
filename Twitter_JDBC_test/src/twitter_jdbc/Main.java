package twitter_jdbc;

import java.sql.*;
import java.util.*;

public class Main {
	public static void main(String args[]) {

		Statement stmt = null;
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Scanner sc = new Scanner(System.in);
		String skip = null;
		


		try {
			String dbURL = "jdbc:mysql://localhost/twitter_db?autoReconnect=true";
			String dbID = "root";
			String dbPassword = "1234";
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(dbURL, dbID, dbPassword);

			try {
				while (true) {
					stmt = con.createStatement();

					int menu;
					String id = null;
					String pw = null;
					String name = null;
					String phonenumber = null;
					String email = null;
					String desc = null; //자기소개
					String pidx = null;
					String cidx = null;
					String qpidx = null;
					String oid = null;
					
					System.out.println("Input zero to sign up, one to login");
					menu = sc.nextInt();
					
					 if(menu == 0) {
					 	String s1 = null;
					 	System.out.println("Input userid / password / name/ phone-number/email");
                     	id = sc.next();
                        pw = sc.next();
                        name = sc.next();
                        phonenumber = sc.next();
                        email = sc.next();
                        stmt = con.createStatement();

                        String s2 = "select user_id from user where user_id = \"" +  id + "\"";

                        rs = stmt.executeQuery(s2);

                        if(rs.next())
                        {
                            System.out.println("User id already exists. Please try again!!!");
                        }
                        else
                        {
                            s1 = "insert into user values ( \'" + id + "\', \'" + pw + "\', \'" + name + "\', \'"+ phonenumber + "\', \'" + email + "\',NULL, default)";
                            System.out.println(s1);
                            pstm = con.prepareStatement(s1);
                            pstm.executeUpdate();
                            
                            System.out.println("Do you want to add an account description? 0 to yes, 1 to no");
                            menu = sc.nextInt();
                            if(menu == 0) {
                            	desc = sc.next();
                            	s1 = "update user set description = \'" +desc+ "\' where user_id = \"" +  id + "\"";
                                System.out.println(s1);
                                pstm = con.prepareStatement(s1);
                                pstm.executeUpdate();
                            }
                        }
					 }
					 else if(menu == 1) //Login
	                    {
						 	System.out.println("Input userid / password");
	                        id = sc.next();
	                        pw = sc.next();

	                        stmt = con.createStatement();
	                        String s1 = "select user_id from user where user_id = \"" + id + "\" and password = \"" +pw +  "\"";

	                        rs = stmt.executeQuery(s1);
	                        
	                        if(rs.next()) //Success login
	                        {
	                            System.out.println("Success Log in !!! ");
	                            do {
	                            	System.out.println("0 to write post, 1 to write comment, 2 to write child_comment, 3 to like post, 4 to like comment,5 to see my follower,\n" +
                                            "6 to see my following, 7 to follow someone, 8 to retweet, 9 to see my post and edit ,10 to see my comment and edit,11 to modify my information,12 to see other users' boards, 13 to write quote_post, 14 to block user,  15 to logout");
	                            	menu = sc.nextInt();
	                            	
	                            	switch(menu) {
	                            	case 0:
	                            		System.out.println("-- Post Writing --");
	                            		skip = sc.nextLine();
	                            		String text = sc.nextLine();
	                            		s1 = "insert into posts (content,writer_id,date) values (\'" + text + "\' ,\'"+id+"\', default )";
	                            		System.out.println(s1);

	                            		pstm = con.prepareStatement(s1,Statement.RETURN_GENERATED_KEYS);
	                                    pstm.executeUpdate();
	                                    
	                                    rs = pstm.getGeneratedKeys(); // 쿼리 실행 후 생성된 키 값 반환
	                        			if (rs.next()) {
	                        				pidx = rs.getString(1); // 키값 초기화
	                        				System.out.println("autoIncrement: " + pidx); // 출력
	                        			}
	                        			
	                        			do {

	                                    System.out.println("ADD 0 to Hashtag 1 to mention 2 to exit?");
	                                    menu = sc.nextInt();
	                                    switch(menu) {
	                					case 0:
	                						
                							System.out.println("write your hashtag");
                							skip =sc.nextLine();
                							String hash =sc.nextLine();
                							
                							s1 = "insert into post_hash(p_content,post_id) value(\'"+hash+"\',\'"+pidx+"\')";
                							System.out.println(s1);
                							pstm= con.prepareStatement(s1);
                							pstm.executeUpdate();
                						    break;
	                					case 1:
	                						stmt = con.createStatement();
	                                    	s1 = "select user_id from user";
		                                    rs = stmt.executeQuery(s1);
		                                    System.out.println("mentioner user id list" );	   
	                                    	System.out.println("---------------------------------");
		                                    while(rs.next()) {
			                                    String result1 = rs.getString("user_id");
		                                        System.out.println("user id : " + result1 + " ");	                                        
		                                    }
                							System.out.println("write your mentioner id");
                							//mention기능의 경우 userid를 통해 호출하므로 user테이블을 통해 존재하는 유저인지 확인후 진행한다
                							skip =sc.nextLine();
                							String m_id =sc.nextLine();
                							s1 = "select user_id from user where user_id = \"" +m_id+"\"";
                							rs=stmt.executeQuery(s1);
                							if(rs.next()) {
                								s1 = "insert into post_mention(post_id,mentioner_id) value(\'"+pidx+"\','"+m_id+"\')";
                								System.out.println(s1);
                								pstm= con.prepareStatement(s1);
                								pstm.executeUpdate();
                							}
                							else {
                								System.out.println("not exist mentioner id");
                							}
                							    break;
	                					case 2:
	                							break;
	                							
	                					default :
	                						System.out.println("This command does not exist");
	                						break;
	                				    
	                							}
	                        			}while(menu!=2);

	                            		break;
	                            		
	                            	case 1:
	                            		skip = sc.nextLine();
	                            		System.out.println("Enter post_idxt");
	                            		
	                            		pidx = sc.nextLine();
	                            		
	                            		s1 = "select post_id from posts where post_id = \"" +pidx+"\"";
	                            		rs=stmt.executeQuery(s1);
            							if(rs.next()) {
	                            		
	                            		System.out.println("Enter Comment content");
	                            		text = sc.nextLine();
	                            		
	                            		s1 = "insert into comment (writer_id,post_id,content,child_comment_id,date) values (\'" + id + "\' ,\'" + pidx + "\' ,\'" + text + "\' ,NULL, default )";
	                            		System.out.println(s1);
	                  
	                            		pstm = con.prepareStatement(s1,Statement.RETURN_GENERATED_KEYS);
	                                    pstm.executeUpdate();
	                                    
	                                    rs = pstm.getGeneratedKeys(); // 쿼리 실행 후 생성된 키 값 반환
	                        			if (rs.next()) {
	                        				cidx = rs.getString(1); // 키값 초기화
	                        				System.out.println("autoIncrement: " + cidx); // 출력
	                        			}               
	                                    
	                                    do {

		                                    System.out.println("ADD 0 to Hashtag 1 to mention 2 to exit?");
		                                    menu = sc.nextInt();
		                                    switch(menu) {
		                					case 0:
	                							System.out.println("write your hashtag");
	                							skip =sc.nextLine();
	                							String hash =sc.nextLine();
	                							
	                							s1 = "insert into comment_hash(h_content,comment_id) value(\'"+hash+"\',\'"+cidx+"\')";
	                							System.out.println(s1);
	                							pstm= con.prepareStatement(s1);
	                							pstm.executeUpdate();
	                						    break;
		                					case 1:
		                						stmt = con.createStatement();
		                                    	s1 = "select user_id from user";
			                                    rs = stmt.executeQuery(s1);
			                                    System.out.println("mentioner user id list" );	   
		                                    	System.out.println("---------------------------------");
			                                    while(rs.next()) {
				                                    String result1 = rs.getString("user_id");
			                                        System.out.println("user id : " + result1 + " ");	                                        
			                                    }
	                							System.out.println("write your mentioner id");
	                							//mention기능의 경우 userid를 통해 호출하므로 user테이블을 통해 존재하는 유저인지 확인후 진행한다
	                							skip =sc.nextLine();
	                							String m_id =sc.nextLine();
	                							s1 = "select user_id from user where user_id = \"" +m_id+"\"";
	                							rs=stmt.executeQuery(s1);
	                							if(rs.next()) {
	                								s1 = "insert into comment_mention(comment_id,mentioner_id) value(\'"+cidx+"\','"+m_id+"\')";
	                								System.out.println(s1);
	                								pstm= con.prepareStatement(s1);
	                								pstm.executeUpdate();
	                							}
	                							else {
	                								System.out.println("not exist mentioner id");
	                							}
	                							break;
		                					case 2:
		                						break;
		                					default :
		                						System.out.println("This command does not exist");
		                						break;
		                							}
		                                    
		                        			}while(menu!=2);
            							}
            							else {
            								System.out.println("This Post does not exist");
            							}
	                            		break;
	                            		
	                            	case 2:
	                            		skip = sc.nextLine();
	                            		System.out.println("Enter comment_idx");
	                            		
	                            		cidx = sc.nextLine();
	                            		
	                            		
	                            		s1 = "SELECT post_id FROM comment WHERE comment_id = \""+cidx+"\"";
	                        			rs = stmt.executeQuery(s1);
	                        			
	                        			if(rs.next()) {
	                        				System.out.println("Enter comment");
	                        				text = sc.nextLine();
	                        				s1 = "insert into comment (writer_id,post_id,content,child_comment_id,date) values (\'" + id + "\' ,\'" + rs.getInt(1) + "\' ,\'" + text + "\' ,\'"+cidx+"\', default )";
		                            		System.out.println(s1);
		                  
		                            		pstm = con.prepareStatement(s1,Statement.RETURN_GENERATED_KEYS);
		                                    pstm.executeUpdate();
		                                    
		                                    rs = pstm.getGeneratedKeys(); // 쿼리 실행 후 생성된 키 값 반환
		                        			if (rs.next()) {
		                        				cidx = rs.getString(1); // 키값 초기화
		                        				System.out.println("autoIncrement: " + cidx); // 출력
		                        			} 
		                                    
		                                    do {

			                                    System.out.println("ADD 0 to Hashtag 1 to mention 2 to exit?");
			                                    menu = sc.nextInt();
			                                    switch(menu) {
			                					case 0:
		                							System.out.println("write your hashtag");
		                							skip =sc.nextLine();
		                							String hash =sc.nextLine();
		                							
		                							s1 = "insert into comment_hash(h_content,comment_id) value(\'"+hash+"\',\'"+cidx+"\')";
		                							System.out.println(s1);
		                							pstm= con.prepareStatement(s1);
		                							pstm.executeUpdate();
		                						    break;
			                					case 1:
			                						stmt = con.createStatement();
			                                    	s1 = "select user_id from user";
				                                    rs = stmt.executeQuery(s1);
				                                    System.out.println("mentioner user id list" );	   
			                                    	System.out.println("---------------------------------");
				                                    while(rs.next()) {
					                                    String result1 = rs.getString("user_id");
				                                        System.out.println("user id : " + result1 + " ");	                                        
				                                    }
		                							System.out.println("write your mentioner id");
		                							//mention기능의 경우 userid를 통해 호출하므로 user테이블을 통해 존재하는 유저인지 확인후 진행한다
		                							skip =sc.nextLine();
		                							String m_id =sc.nextLine();
		                							s1 = "select user_id from user where user_id = \"" +m_id+"\"";
		                							rs=stmt.executeQuery(s1);
		                							if(rs.next()) {
		                								s1 = "insert into comment_mention(comment_id,mentioner_id) value(\'"+cidx+"\','"+m_id+"\')";
		                								System.out.println(s1);
		                								pstm= con.prepareStatement(s1);
		                								pstm.executeUpdate();
		                							}
		                							else {
		                								System.out.println("not exist mentioner id");
		                							}
		                							break;
			                					case 2:
			                						break;
			                					default :
			                						System.out.println("This command does not exist");
			                						break;
			                							}
			                                    
			                        			}while(menu!=2);
	                        			}
	                        			else{
	                        				System.out.println("Not exist comment id");
	                        			}
	                            		
	                            		break;
	                            	case 3:
	                            		skip = sc.nextLine();
	                            		System.out.println("Please enter the POSTID");
	                            		pidx = sc.nextLine();
	                            		
	                            		s1 = "select liker_id from post_like where liker_id=\"" + id + "\" and post_id =" + pidx + "";
	                            		rs=stmt.executeQuery(s1);
	                            		if(rs.next()) {
	                						System.out.println("Already liked post please try again");
	                					}
	                            		else {
	                							                						
                							s1= "insert into post_like(post_id,liker_id,create_at) value(\'" + pidx + "\','" +id +"\',DEFAULT)";
                							System.out.println(s1);
                							pstm = con.prepareStatement (s1); 
                							pstm.executeUpdate();
                					
	                						
	                					}
	                            		
	                            		break;
	                            	
	                            	case 4:
	                            		skip = sc.nextLine();
	                            		System.out.println("Please enter the COMMENTID");
	                            		cidx = sc.nextLine();
	                            		
	                            		s1 = "select liker_id from comment_like where liker_id=\"" + id + "\" and comment_id =" + cidx + "";
	                            		rs=stmt.executeQuery(s1);
	                            		if(rs.next()) {
	                						System.out.println("Already liked comment please try again");
	                					}
	                            		else {
	                							                						
                							s1= "insert into comment_like(comment_id,liker_id,create_at) value(\'" + cidx + "\','" +id +"\',DEFAULT)";
                							System.out.println(s1);
                							pstm = con.prepareStatement (s1); 
                							pstm.executeUpdate();
            
	                						
	                					}
	                            		
	                            		break;
	                            		
	                            	case 5:
	                            		stmt =con.createStatement();
	                            		s1 = "select follower_id  from following where followed_id =\""+id+"\"";
	                					rs = stmt.executeQuery(s1);
	                					while(rs.next()) {
	                						String result = rs.getString(1);
	                						System.out.println(result);
	                					}
	                            		break;
	                            		
	                            	case 6:
	                            		stmt =con.createStatement();
	                            		s1 = "select followed_id from following where follower_id =\""+id+"\"";
	                					rs = stmt.executeQuery(s1);
	                					while(rs.next()) {
	                						String result = rs.getString(1);
	                						System.out.println(result);
	                					}
	                            		break;
	                            	case 7:
	                            		skip = sc.nextLine();
	                					String f_id=null;
	                					System.out.println("Input user ID to follow"); 
	                					f_id=sc.nextLine();
	                					if(f_id.equals(id))
	                						System.out.println("Can't follow yourself");
	                					else {
	                						stmt = con.createStatement();
	                						s1 = "select follower_id from following where followed_id =\"" + f_id + "\" and follower_id = \'"+id+"\'" ;
	                						rs = stmt.executeQuery(s1);
	                						if(rs.next()) {
	                							
	                							String result = rs.getString(1);
	                							if(result.equals(id)) {
	                							System.out.println("Already follow the user. Please try again!");
	                							}
	                							}
	                						else {
	                							s1="insert into following(follower_id,followed_id,create_at) value(\'"+id+"\', \'" + f_id + "\', DEFAULT)"; 
	                							pstm = con.prepareStatement (s1); 
	                							pstm.executeUpdate();
	                						}
	                					}
	                            		break;
	                            	case 8:
	                            		skip = sc.nextLine();
	                            		System.out.println("Please enter the POSTID");
	                            		pidx = sc.nextLine();
	                            		
	                            		s1 = "select user_id from re_tweet_post where user_id=\"" + id + "\" and post_id =" + pidx + "";
	                            		rs=stmt.executeQuery(s1);
	                            		if(rs.next()) {
	                						System.out.println("Already retweet post please try again");
	                					}
	                            		else {
	                							               						
                							s1= "insert into re_tweet_post(post_id,user_id,creat_at) value(\'" + pidx + "\','" +id +"\',DEFAULT)";
                							System.out.println(s1);
                							pstm = con.prepareStatement (s1); 
                							pstm.executeUpdate();
	                						}
	                            		break;
	                            		
	                            	case 9:

	                                    	stmt = con.createStatement();
	                                    	
	                                    	s1 = "select p.post_id, p.content,p.writer_id, COUNT(pl.post_id) as num_of_likes from posts as p left join post_like as pl on p.post_id = pl.post_id group by p.post_id having  writer_id = \"" + id + "\"";
	                                    
	                                    	System.out.println(s1);
		                                    rs = stmt.executeQuery(s1);
		                                    
		                                    while(rs.next()) {
		                                    	
			                                    String result1 = rs.getString("post_id");
		                                        System.out.print("post id : " + result1 + " ");
		                                        String result2 = rs.getString("content");
		                                        System.out.println("content : " + result2 + " ");
		                                        String result3 = rs.getString("num_of_likes");
		                                        System.out.println("num_of_likes : " + result3 + " ");	                                        
		                                        System.out.println("---------------------------------");
		                                    }
		                                    System.out.println("Is there any post that you want to edit?? 0 to yes 1 to No");
		                                    menu = sc.nextInt();
		                                    if(menu ==0) {
		                                    	skip = sc.nextLine();
			                            		System.out.println("Please enter the POSTID");
			                            		pidx = sc.nextLine();
			                            		
			                            		System.out.println("Please enter the POST Content");
			                            		String content = sc.nextLine();
			                            		
			                                	s1 = "update posts set content = \'" +content+ "\' where post_id = \"" +  pidx + "\"";
			                                	pstm = con.prepareStatement (s1); 
	                							pstm.executeUpdate();
		                                    }
		                                    break;
		                                    
	                            	case 10:
	                            		stmt = con.createStatement();
                                    	
                                    	s1 = "select c.comment_id,c.post_id, c.content,c.writer_id,c.child_comment_id ,COUNT(cl.comment_id) as num_of_likes from comment as c left join comment_like as cl on c.comment_id = cl.comment_id group by c.comment_id having  writer_id = \"" + id + "\"";
                                    
                                    	System.out.println(s1);
	                                    rs = stmt.executeQuery(s1);
	                                    
	                                    while(rs.next()) {
	                                    	
		                                    String result1 = rs.getString("comment_id");
	                                        System.out.print("comment id : " + result1 + " ");
	                                        String result2 = rs.getString("post_id");
	                                        System.out.print("post id : " + result2 + " ");
	                                        String result3 = rs.getString("content");
	                                        System.out.println("content : " + result3 + " ");
	                                        String result4 = rs.getString("child_comment_id");
	                                        System.out.println("child_comment_id : " + result4 + " ");
	                                        String result5 = rs.getString("num_of_likes");
	                                        System.out.println("num_of_likes : " + result5 + " ");	                                        
	                                        System.out.println("---------------------------------");
	                                    }
	                                    System.out.println("Is there any comment that you want to edit?? 0 to yes 1 to No");
	                                    menu = sc.nextInt();
	                                    if(menu ==0) {
	                                    	skip = sc.nextLine();
		                            		System.out.println("Please enter the commentID");
		                            		cidx = sc.nextLine();
		                            		
		                            		System.out.println("Please enter the comment Content");
		                            		String content = sc.nextLine();
		                            		
		                                	s1 = "update comment set content = \'" +content+ "\' where post_id = \"" +  cidx + "\"";
		                                	pstm = con.prepareStatement (s1); 
                							pstm.executeUpdate();
	                                    }
	                            		break;
	                            	case 11:
	                            		do {
	                            		System.out.println("0 to modify password, 1 to name, 2 to phone, 3 to email, 4 to desctiption, 5 to all, 6 to exit");
	                            		menu = sc.nextInt();
	                            		
	                            		switch(menu) {
	                            		case 0 :
	                            			System.out.println("Input password");
	                            			pw = sc.next();
	                            			s1 = "update user set password = \'" +pw+ "\' where user_id = \"" +  id + "\"";
		                                	pstm = con.prepareStatement (s1); 
                							pstm.executeUpdate();
	                            			break;
	                            		case 1 :
	                            			System.out.println("Input  name");
	                            			name = sc.next();
	                            			s1 = "update user set name = \'" +name+ "\' where user_id = \"" +  id + "\"";
		                                	pstm = con.prepareStatement (s1); 
                							pstm.executeUpdate();
	                            			break;
	                            		case 2 :
	                            			System.out.println("Input phone");
	                            			phonenumber = sc.next();
	                            			s1 = "update user set phone = \'" +phonenumber+ "\' where user_id = \"" +  id + "\"";
		                                	pstm = con.prepareStatement (s1); 
                							pstm.executeUpdate();
	                            			break;
	                            		case 3 :
	                            			System.out.println("Input email");
	                            			email = sc.next();
	                            			s1 = "update user set email = \'" +email+ "\' where user_id = \"" +  id + "\"";
		                                	pstm = con.prepareStatement (s1); 
                							pstm.executeUpdate();
	                            			break;
	                            		case 4:
	                            			System.out.println("Input description");
	                            			desc = sc.next();
	                                    	s1 = "update user set description = \'" +desc+ "\' where user_id = \"" +  id + "\"";
	                                        pstm = con.prepareStatement(s1);
	                                        pstm.executeUpdate();
	                                        break;
	                            		case 5:
	                            			System.out.println("Input password / name/ phone-number/email/description");
	                                        pw = sc.next();
	                                        name = sc.next();
	                                        phonenumber = sc.next();
	                                        email = sc.next();
	                                        desc = sc.next();
	                                        stmt = con.createStatement();
	                                        
	                                        s1 = "update user set password = \'" +pw+ "\', name = \'" +name+ "\', phone = \'" +phonenumber+ "\', email = \'" +email+ "\',description = \'" +desc+ "\'  where user_id = \"" +  id + "\"";
	                                        System.out.println(s1);
	                                        pstm = con.prepareStatement(s1);
	                                        pstm.executeUpdate();
	                                        break;
	                            		case 6 :
	                            			break;
	                            		}
	                            		}while(menu != 6);
	                        
	                            		break;
	                            		
	                            	case 12:
	                            		skip = sc.nextLine();
	                            		System.out.println("Enter user_id");
	                            		
	                            		oid = sc.nextLine();
	                            		
	                            		s1 = "select user_id from user where user_id = \"" +oid+"\"";
	                            		rs=stmt.executeQuery(s1);
            							if(rs.next()) {
                                    	
	                                    	s1 = "select p.post_id, p.content,p.writer_id, COUNT(pl.post_id) as num_of_likes from posts as p left join post_like as pl on p.post_id = pl.post_id group by p.post_id having  writer_id = \"" + oid + "\"";
	                                    
	                                    	System.out.println(s1);
		                                    rs = stmt.executeQuery(s1);
		                                    
		                                    while(rs.next()) {
		                                    	
			                                    String result1 = rs.getString("post_id");
		                                        System.out.print("post id : " + result1 + " ");
		                                        String result2 = rs.getString("content");
		                                        System.out.println("content : " + result2 + " ");
		                                        String result3 = rs.getString("num_of_likes");
		                                        System.out.println("num_of_likes : " + result3 + " ");	                                        
		                                        System.out.println("---------------------------------");
		                                    }
		                                    
	            						}
            							else {
            								System.out.println("not exist user");
            							}
	                                    break;
	                                    
	                            	case 13:
	                            		skip = sc.nextLine();
	                            		System.out.println("Enter quote_post_idx");
	                            		
	                            		pidx = sc.nextLine();
	                            		
	                            		s1 = "SELECT post_id FROM posts WHERE post_id = \""+pidx+"\"";
	                        			rs = stmt.executeQuery(s1);
	                        			
	                        			if(rs.next()) {
	                        				System.out.println("Enter post content");
	                        				text = sc.nextLine();
	                        				s1 = "insert into posts (content,writer_id,date) values (\'" + text + "\' ,\'"+id+"\', default )";
		                            		System.out.println(s1);

		                            		pstm = con.prepareStatement(s1,Statement.RETURN_GENERATED_KEYS);
		                                    pstm.executeUpdate();
		                                    
		                                    rs = pstm.getGeneratedKeys(); // 쿼리 실행 후 생성된 키 값 반환
		                        			if (rs.next()) {
		                        				qpidx = rs.getString(1); // 키값 초기화
		                        				System.out.println("autoIncrement: " + qpidx); // 출력
		                        			} 
		                                    
		                                    do {

			                                    System.out.println("ADD 0 to Hashtag 1 to mention 2 to exit?");
			                                    menu = sc.nextInt();
			                                    switch(menu) {
			                					case 0:
		                							System.out.println("write your hashtag");
		                							skip =sc.nextLine();
		                							String hash =sc.nextLine();
		                							
		                							s1 = "insert into post_hash(p_content,comment_id) value(\'"+hash+"\',\'"+qpidx+"\')";
		                							System.out.println(s1);
		                							pstm= con.prepareStatement(s1);
		                							pstm.executeUpdate();
		                						    break;
			                					case 1:
			                						stmt = con.createStatement();
			                                    	s1 = "select user_id from user";
				                                    rs = stmt.executeQuery(s1);
				                                    System.out.println("mentioner user id list" );	   
			                                    	System.out.println("---------------------------------");
				                                    while(rs.next()) {
					                                    String result1 = rs.getString("user_id");
				                                        System.out.println("user id : " + result1 + " ");	                                        
				                                    }
		                							System.out.println("write your mentioner id");
		                							//mention기능의 경우 userid를 통해 호출하므로 user테이블을 통해 존재하는 유저인지 확인후 진행한다
		                							skip =sc.nextLine();
		                							String m_id =sc.nextLine();
		                							s1 = "select user_id from user where user_id = \"" +m_id+"\"";
		                							rs=stmt.executeQuery(s1);
		                							if(rs.next()) {
		                								s1 = "insert into post_mention(post_id,mentioner_id) value(\'"+qpidx+"\','"+m_id+"\')";
		                								System.out.println(s1);
		                								pstm= con.prepareStatement(s1);
		                								pstm.executeUpdate();
		                							}
		                							else {
		                								System.out.println("not exist mentioner id");
		                							}
		                							break;
			                					case 2:
			                						break;
			                					default :
			                						System.out.println("This command does not exist");
			                						break;
			                							}
			                                    
			                        			}while(menu!=2);
		                                    s1= "insert into quote_post(post_id,q_post_id,user_id,create_at) value(\'"+qpidx+"\', \'" + pidx + "\','" +id +"\',DEFAULT)";
	            							System.out.println(s1);
	            							pstm = con.prepareStatement (s1); 
	            							pstm.executeUpdate();
	                        			}
	                        			else{
	                        				System.out.println("Not exist Post id");
	                        			}
               						
	                            		break;
	                            		
	                            	case 14:
	                            		skip = sc.nextLine();
	                					String b_id=null;
	                					System.out.println("Input user ID to block"); 
	                					b_id=sc.nextLine();
	                					if(b_id.equals(id))
	                						System.out.println("Can't block yourself");
	                					else {
	                						stmt = con.createStatement();
	                						s1 = "select block_id from block where block_id =\'" + b_id + "\' and user_id = \'"+id+"\' ";
	                						rs = stmt.executeQuery(s1);
	                						
	                						if(rs.next()) {
	                							String result = rs.getString(1);
	                							if(result.equals(b_id)) {
	                							System.out.println("Already block the user. Please try again!");
	                							}
	                							}
	                						else {
	                							s1="insert into block(user_id,block_id,create_at) value(\'"+id+"\', \'" + b_id + "\', DEFAULT)"; 
	                							pstm = con.prepareStatement (s1); 
	                							pstm.executeUpdate();
	                						}
	                					}
	                            		break;
                        		   
	                            	
	                            	}
	                            	}while(menu!=15);
	                            }
	                        else
	                        {
	                            System.out.println("wrong id/password. Please log in again !!! ");
	                        }
	                        }
	                        
	                        
	                    }

				 } 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}

		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		 try {
	         if( con != null && !con.isClosed()) 
	            con.close();
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } 


	}
}