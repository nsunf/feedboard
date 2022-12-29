package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import dto.Comment;

public class CommentsDao extends DAO {
	public ArrayList<Comment> getComments(String comment_id) { 
		Connection conn = getConnection();
		ArrayList<Comment> comments = new ArrayList<>();
		
		String sql = "select "
				+ "p.post_id, "
				+ "c.comment_id, "
				+ "p.member_uuid as post_author_id, "
				+ "c.member_uuid as comm_author_uuid, "
				+ "m.member_id, "
				+ "c.comment_content, "
				+ "c.comment_regdate, "
				+ "c.comment_editdate "
				+ "from comments c "
				+ "left join posts p "
				+ "on p.post_id = c.post_id "
				+ "left join members m "
				+ "on c.member_uuid = m.member_uuid "
				+ "where p.post_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, comment_id);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Comment c = new Comment();
				c.setPost_id(rs.getString(1));
				c.setUuid(rs.getString(2));
				c.setPost_author_id(rs.getString(3));
				c.setComm_author_uuid(rs.getString(4));
				c.setComm_author_id(rs.getString(5));
				c.setContent(rs.getString(6));
				c.setRegDate(rs.getString(7));
				c.setEditDate(rs.getString(8));
				
				comments.add(c);
			}
			
			conn.close();
			ps.close();
			rs.close();
		} catch (Exception e) {
			System.out.println("===> CommentsDao.getComments()");
			e.printStackTrace();
		}
		
		return comments;
	}
	
	public void addComment(HttpServletRequest req) {
		Connection conn = getConnection();
		
		String sql = "insert into comments values(?, ?, ?, ?, default, null)";
		String comment_id = UUID.randomUUID().toString();
		String post_id = req.getParameter("post_id");
		String comment_content = req.getParameter("comment_text");
		
		try {
			String member_id = (String)req.getSession().getAttribute("user_uuid");
			if (member_id == null) throw new Exception("please login to add comment");

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, comment_id);
			ps.setString(2, post_id);
			ps.setString(3, member_id);
			ps.setString(4, comment_content);
			
			ps.executeUpdate();
			
			conn.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteComment(HttpServletRequest req) {
		Connection conn = getConnection();
		
		String sql = "delete from comments where comment_id = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, req.getParameter("comment_id"));
			
			ps.executeUpdate();
			
			conn.close();
			ps.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
