package dto;

public class Comment {
	private String uuid;
	private String post_id;
	private String post_author_id;
	private String comm_author_uuid;
	private String comm_author_id;
	private String content;
	private String regDate;
	private String editDate;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getPost_id() {
		return post_id;
	}
	public void setPost_id(String post_id) {
		this.post_id = post_id;
	}
	public String getPost_author_id() {
		return post_author_id;
	}
	public void setPost_author_id(String post_author_id) {
		this.post_author_id = post_author_id;
	}
	public String getComm_author_uuid() {
		return comm_author_uuid;
	}
	public void setComm_author_uuid(String comm_author_uuid) {
		this.comm_author_uuid = comm_author_uuid;
	}
	public String getComm_author_id() {
		return comm_author_id;
	}
	public void setComm_author_id(String comm_author_id) {
		this.comm_author_id = comm_author_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getEditDate() {
		return editDate;
	}
	public void setEditDate(String editDate) {
		this.editDate = editDate;
	}
}
