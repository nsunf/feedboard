package dao;

import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;

import dto.Member;

public class MembersDao extends DAO {
	
	public String login(String user_id, String user_pw) {
		conn = getConnection();
		String uuid = null;
		
		String sql = "select "
				+ "m.member_uuid "
				+ "from members m "
				+ "left join posts p "
				+ "on m.member_uuid = p.member_uuid "
				+ "where m.member_id = ? and m.member_pw = ? ";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user_id);
			ps.setString(2, user_pw);
			rs = ps.executeQuery();

			if (rs.next())
				uuid = rs.getString(1);
		} catch (Exception e) {
			System.out.println("===> MembersDao.login()");
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return uuid;
	}
	
	public Member getMember(String userUUID) {
		conn = getConnection();
		Member member = null;
		
		String sql = "select "
				+ "m.member_uuid, "
				+ "m.member_id, "
				+ "m.member_name, "
				+ "count(p.post_id), "
				+ "substr(m.member_ssn, 1, 6) as birth, "
				+ "m.member_phone, "
				+ "m.member_regdate, "
				+ "m.member_image "
				+ "from members m "
				+ "left join posts p "
				+ "on m.member_uuid = p.member_uuid "
				+ "where m.member_uuid = ?"
				+ "group by (m.member_uuid, m.member_id, m.member_name, substr(m.member_ssn, 1, 6), m.member_phone, m.member_regdate, m.member_image)";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, userUUID);
			rs = ps.executeQuery();

			if (rs.next()) {
				member = new Member();
				member.setUuid(rs.getString(1));
				member.setId(rs.getString(2));
				member.setName(rs.getString(3));
				member.setNumOfPost(rs.getInt(4));
				member.setBirth(rs.getString(5));
				member.setPhone(rs.getString(6));
				member.setRegDate(rs.getString(7));
				member.setImgurl(rs.getString(8));
			}
		} catch (Exception e) {
			System.out.println("===> MembersDao.login()");
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return member;
	}
	
	public boolean signUp(Member m) {
		conn = getConnection();
		int result = 0;
		
		String sql = "insert into members values (?, ?, ?, ?, ?, ?, default, null)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, m.getUuid());
			ps.setString(2, m.getId());
			ps.setString(3, m.getSsn());
			ps.setString(4, m.getPw());
			ps.setString(5, m.getName());
			ps.setString(6, m.getPhone());
			
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return result == 1;
	}
	
	public String converToSHA512(String pw) {
		String result = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(pw.getBytes("UTF-8"));
			result = DatatypeConverter.printBase64Binary(md.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
