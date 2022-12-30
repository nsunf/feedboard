package dao;

import javax.servlet.http.HttpServletRequest;

public class LikesDao extends DAO {

	public void toggleLike(HttpServletRequest req) {
		conn = getConnection();
		
		String post_uuid = req.getParameter("post_id");
		boolean on = req.getParameter("on").equals("1");
		String user_uuid = (String) req.getAttribute("user_uuid"); 
		
		System.out.println("+++" + req.getParameter("on"));
		
		String sql = "";
		
		if (on)
			sql = "insert into likes values (?, ?, default)";
		else
			sql = "delete from likes where post_id = ? and member_uuid = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, post_uuid);
			ps.setString(2, user_uuid);
			
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
	}
}
