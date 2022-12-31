package dao;


public class LikesDao extends DAO {

	public void toggleLike(String postUUID, String userUUID, boolean on) {
		conn = getConnection();
		
		String sql = "";
		
		if (on)
			sql = "insert into likes values (?, ?, default)";
		else
			sql = "delete from likes where post_id = ? and member_uuid = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, postUUID);
			ps.setString(2, userUUID);
			
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
	}
}
