package dao;

import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import dto.Post;

public class PostsDao extends DAO {
	
	public ArrayList<Post> selectAll(String userUUID) {
		conn = getConnection();
		ArrayList<Post> list = new ArrayList<>();
		String sql = "";
		if (userUUID == null) {
			sql = "select "
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
		} else {
			sql = "select "
				+ "a.post_id, "
				+ "a.author_uuid, "
				+ "a.author_id, "
				+ "a.post_content, "
				+ "a.post_regdate, "
				+ "a.post_editdate, "
				+ "a.likes, "
				+ "(select i.image_id||i.image_ext as file_name from posts p left join images i on p.post_id = i.post_id where p.post_id = a.post_id and i.image_order = 1) as img1, "
				+ "(select i.image_id||i.image_ext as file_name from posts p left join images i on p.post_id = i.post_id where p.post_id = a.post_id and i.image_order = 2) as img2, "
				+ "count(c.post_id), "
				+ "(select count(l.post_id) from likes l where l.post_id = a.post_id and l.member_uuid = ?) as islike "
				+ "from posts_info a "
				+ "left join comments c "
				+ "on a.post_id = c.post_id "
				+ "group by (a.post_id, a.author_uuid, a.author_id, a.post_content, a.post_regdate, a.post_editdate, a.likes) "
				+ "order by a.post_regdate desc";
		}

		
		try {
			ps = conn.prepareStatement(sql);
			if (userUUID != null) ps.setString(1, userUUID);
			rs = ps.executeQuery();
			
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
				if (userUUID != null) post.setLiked(rs.getInt(11) != 0);
				list.add(post);
			}
			
		} catch (Exception e) {
			System.out.println("===> PostsDao.selectAll()");
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return list;
	}
	
	public Post getPost(String postUUID, String userUUID) {
		conn = getConnection();
		Post post = new Post();
		String sql = "";
		
		if (userUUID == null) {
			sql = "select "
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
		} else {
			sql = "select "
					+ "p.post_id, "
					+ "p.author_uuid, "
					+ "p.author_id, "
					+ "p.post_content, "
					+ "p.post_regdate, "
					+ "p.post_editdate, "
					+ "p.likes, "
					+ "count(c.post_id), "
					+ "(select count(l.post_id) from likes l where l.post_id = p.post_id and l.member_uuid = ?) as islike "
					+ "from posts_info p "
					+ "left join comments c "
					+ "on p.post_id = c.post_id "
					+ "group by (p.post_id, p.author_uuid, p.author_id, p.post_content, p.post_regdate, p.post_editdate, p.likes) "
					+ "having p.post_id = ?";
		}
		
		try {
			ps = conn.prepareStatement(sql);
			if (userUUID == null) {
				ps.setString(1, postUUID);
			} else {
				ps.setString(1, userUUID);
				ps.setString(2, postUUID);
			}
			rs = ps.executeQuery();
			
			if (rs.next()) {
				post.setUuid(rs.getString(1));
				post.setMember_uuid(rs.getString(2));
				post.setMember_id(rs.getString(3));
				post.setContent(rs.getString(4));
				post.setRegdate(rs.getString(5));
				post.setEditdate(rs.getString(6));
				post.setLikes(rs.getInt(7));
				post.setComments(rs.getInt(8));
				if (userUUID != null) post.setLiked(rs.getInt(9) != 0);
			}
			
			sql = "select "
					+ "i.image_id||i.image_ext as file_name "
					+ "from posts p "
					+ "join images i "
					+ "on p.post_id = i.post_id "
					+ "where p.post_id = ? "
					+ "order by i.image_order";
			ps = conn.prepareStatement(sql);
			ps.setString(1, postUUID);
			rs = ps.executeQuery();
			
			ArrayList<String> images = new ArrayList<>();
			while (rs.next()) {
				images.add(rs.getString(1));
			}
			post.setImages(images);
		} catch (Exception e) {
			System.out.println("PostDao.getPost()");
			e.printStackTrace();
		} finally {
			closeAll();
		}
		
		System.out.println(post.getMember_uuid());
		return post;
	}
	
	public String addPost(String userUUID, String content) {
		conn = getConnection();
		int result = 0;
		
		String postUUID = UUID.randomUUID().toString();
		
		String sql = "insert into posts values (?, ?, ?, default, null)";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, postUUID);
			ps.setString(2, userUUID);
			ps.setString(3, content);
			result = ps.executeUpdate();

			conn.close();
			ps.close();
		} catch (Exception e) {
			System.out.println("===> PostsDao.addPost()");
			e.printStackTrace();
		} finally {
			closeAll();
		}

		
		return result != 0 ? postUUID : null;
	}
	
	public void updatePost(String content, String postUUID) {
		conn = getConnection();
		
		String sql = "update posts set post_content = ?, post_regdate = sysdate where post_id = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, content);
			ps.setString(2, postUUID);
			
			ps.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
	}
	
	public void deletePost(String postUUID) {
		conn = getConnection();
		
		String sql = "delete from posts where post_id = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, postUUID);
			
			ps.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
	}
}



