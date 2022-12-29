package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class ImageDao extends DAO {
	public void addImages(String post_id, ArrayList<String> images) {
		Connection conn = getConnection();
		PreparedStatement ps = null;

		try {
			for (int i = 0; i < images.size(); i++) {
				String img = images.get(i);
				System.out.println(img);
				String filename = img.substring(0, 36);
				String ext = "." + img.substring(37);
				String sql = "insert into images values (?, ?, ?, ?)";

				ps = conn.prepareStatement(sql);
				ps.setString(1, filename);
				ps.setString(2, post_id);
				ps.setInt(3, i + 1);
				ps.setString(4, ext);

				ps.executeUpdate();
			}

			conn.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
