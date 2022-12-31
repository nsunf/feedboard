package dao;

import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import dto.Comment;

public class CommentsDao extends DAO {
	public ArrayList<Comment> getComments(String commentUUID) { 
		conn = getConnection();
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
			ps = conn.prepareStatement(sql);
			ps.setString(1, commentUUID);
			rs = ps.executeQuery();
			
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
		} catch (Exception e) {
			System.out.println("===> CommentsDao.getComments()");
			e.printStackTrace();
		} finally {
			closeAll();
		}
		
		return comments;
	}
	
	public void addComment(String postUUID, String memberUUID, String content) {
		conn = getConnection();
		
		String sql = "insert into comments values(?, ?, ?, ?, default, null)";
		String comment_id = UUID.randomUUID().toString();
		
		try {
			if (memberUUID == null) throw new Exception("please login to add comment");

			ps = conn.prepareStatement(sql);
			ps.setString(1, comment_id);
			ps.setString(2, postUUID);
			ps.setString(3, memberUUID);
			ps.setString(4, content);
			
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
	}
	
	public void deleteComment(String commentUUID) {
		conn = getConnection();
		
		String sql = "delete from comments where comment_id = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, commentUUID);
			
			ps.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
	}
}
