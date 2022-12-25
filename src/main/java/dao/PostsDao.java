package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.Post;

public class PostsDao extends DAO {
	
	public ArrayList<Post> selectAll() {
		Connection conn = getConnection();
		
		try {
			String sql = "select "
					+ "    p.post_id, "
					+ "    m.member_uuid, "
					+ "    m.member_id, "
					+ "    p.post_content, "
					+ "    to_char(p.post_regdate, 'YYYY/MM/DD HH:mm') as regdate, "
					+ "    to_char(p.post_editdate, 'YYYY/MM/DD HH:mm'), "
					+ "    count(l.member_uuid) "
					+ "from posts p "
					+ "join members m "
					+ "on p.member_uuid = m.member_uuid "
					+ "join likes l "
					+ "on l.member_uuid = m.member_uuid "
					+ "group by (p.post_id, m.member_uuid, m.member_id, p.post_content, p.post_regdate, p.post_editdate) "
					+ "order by regdate desc";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				String postID = rs.getString(1);
				String memUUID = rs.getString(2);
				String memberID = rs.getString(3);
				String postContent = rs.getString(4);
				String postRegdate = rs.getString(5);
				String postEditdate = rs.getString(6);
				String likes = rs.getString(7);
			}
			
			conn.close();
			ps.close();
			rs.close();
		} catch (Exception e) {
			System.out.println("===> PostsDao.selectAll()");
			e.printStackTrace();
		}

		return null;
	}
}
