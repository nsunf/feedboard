package dao;

import java.util.ArrayList;

public class ImageDao extends DAO {
	public void addImages(String postUUID, ArrayList<String> images) {
		conn = getConnection();
		ps = null;

		try {
			for (int i = 0; i < images.size(); i++) {
				String img = images.get(i);
				System.out.println(img);
				String filename = img.substring(0, 36);
				String ext = "." + img.substring(37);
				String sql = "insert into images values (?, ?, ?, ?)";

				ps = conn.prepareStatement(sql);
				ps.setString(1, filename);
				ps.setString(2, postUUID);
				ps.setInt(3, i + 1);
				ps.setString(4, ext);

				ps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
	}
}
