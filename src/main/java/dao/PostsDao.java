package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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
					+ "group by (a.post_id, a.author_uuid, a.author_id, a.post_content, a.post_regdate, a.post_editdate, a.likes)";
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
}



