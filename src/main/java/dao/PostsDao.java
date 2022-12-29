package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import dto.Post;

public class PostsDao extends DAO {
	
	public ArrayList<Post> selectAll() {
		Connection conn = getConnection();
		ArrayList<Post> list = new ArrayList<>();
		
		try {
			String sql = "select "
					+ "a.post_id, "
					+ "a.author_uuid, "
					+ "a.author_id, "
					+ "a.post_content, "
					+ "a.post_regdate, "
					+ "a.post_editdate, "
					+ "a.likes, "
					+ "(select i.image_id||i.image_ext as file_name from posts p left join images i on p.post_id = i.post_id where p.post_id = a.post_id and i.image_order = 1) as img1, "
					+ "(select i.image_id||i.image_ext as file_name from posts p left join images i on p.post_id = i.post_id where p.post_id = a.post_id and i.image_order = 2) as img2, "
					+ "count(c.post_id) "
					+ "from posts_info a "
					+ "left join comments c "
					+ "on a.post_id = c.post_id "
					+ "group by (a.post_id, a.author_uuid, a.author_id, a.post_content, a.post_regdate, a.post_editdate, a.likes) "
					+ "order by a.post_regdate desc";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Post post = new Post();
				String postID = rs.getString(1);
				String authUUID = rs.getString(2);
				String authID = rs.getString(3);
				String postContent = rs.getString(4);
				String postRegdate = rs.getString(5);
				String postEditdate = rs.getString(6);
				int likes = rs.getInt(7);
				int comments = rs.getInt(10);
				ArrayList<String> images = new ArrayList<>();
				if (rs.getString(8) != null) images.add(rs.getString(8));
				if (rs.getString(9) != null) images.add(rs.getString(9));
				
				post.setUuid(postID);
				post.setMember_uuid(authUUID);
				post.setMember_id(authID);
				post.setContent(postContent);
				post.setRegdate(postRegdate);
				post.setEditdate(postEditdate);
				post.setLikes(likes);
				post.setComments(comments);
				post.setImages(images);
				
				list.add(post);
			}
			
			conn.close();
			ps.close();
			rs.close();
		} catch (Exception e) {
			System.out.println("===> PostsDao.selectAll()");
			e.printStackTrace();
		}

		return list;
	}
	
	public Post getPost(String post_id) {
		Connection conn = getConnection();
		Post post = new Post();
		
		try {
			String sql = "select "
					+ "p.post_id, "
					+ "p.author_uuid, "
					+ "p.author_id, "
					+ "p.post_content, "
					+ "p.post_regdate, "
					+ "p.post_editdate, "
					+ "p.likes, "
					+ "count(c.post_id) "
					+ "from posts_info p "
					+ "left join comments c "
					+ "on p.post_id = c.post_id "
					+ "group by (p.post_id, p.author_uuid, p.author_id, p.post_content, p.post_regdate, p.post_editdate, p.likes) "
					+ "having p.post_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, post_id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				post.setUuid(rs.getString(1));
				post.setMember_uuid(rs.getString(2));
				post.setMember_id(rs.getString(3));
				post.setContent(rs.getString(4));
				post.setRegdate(rs.getString(5));
				post.setEditdate(rs.getString(6));
				post.setLikes(rs.getInt(7));
				post.setComments(rs.getInt(8));
			}
			
			sql = "select "
					+ "i.image_id||i.image_ext as file_name "
					+ "from posts p "
					+ "join images i "
					+ "on p.post_id = i.post_id "
					+ "where p.post_id = ? "
					+ "order by i.image_order";
			ps = conn.prepareStatement(sql);
			ps.setString(1, post_id);
			rs = ps.executeQuery();
			
			ArrayList<String> images = new ArrayList<>();
			while (rs.next()) {
				images.add(rs.getString(1));
			}
			post.setImages(images);
			System.out.println("img count - " + images.size());
			
			conn.close();
			ps.close();
			rs.close();
		} catch (Exception e) {
			System.out.println("PostDao.getPost()");
			e.printStackTrace();
		}
		
		return post;
	}
	
	public String addPost(HttpServletRequest req) {
		Connection conn = getConnection();
		int result = 0;
		
		String post_uuid = UUID.randomUUID().toString();
		String member_uuid = (String)req.getSession().getAttribute("user_uuid");
		String post_content = req.getParameter("content");
		
		String sql = "insert into posts values (?, ?, ?, default, null)";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, post_uuid);
			ps.setString(2, member_uuid);
			ps.setString(3, post_content);
			result = ps.executeUpdate();

			conn.close();
			ps.close();
		} catch (Exception e) {
			System.out.println("===> PostsDao.addPost()");
			e.printStackTrace();
		}

		
		return result != 0 ? post_uuid : null;
	}
	
	public void updatePost(HttpServletRequest req) {
		Connection conn = getConnection();
		
		String sql = "update posts set post_content = ?, post_regdate = sysdate where post_id = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, req.getParameter("content"));
			ps.setString(2, req.getParameter("post_id"));
			
			ps.executeUpdate();
			
			conn.close();
			ps.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deletePost(HttpServletRequest req) {
		Connection conn = getConnection();
		
		String sql = "delete from posts where post_id = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, req.getParameter("post_id"));
			
			ps.executeUpdate();
			
			conn.close();
			ps.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}



