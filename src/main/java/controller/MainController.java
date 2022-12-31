package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import dao.CommentsDao;
import dao.ImageDao;
import dao.LikesDao;
import dao.MembersDao;
import dao.PostsDao;
import dto.Comment;
import dto.Member;
import dto.Post;

@WebServlet("/")
//@MultipartConfig(maxFileSize = 1024 * 1024 * 10, location = "d:/project/java/FeedBoard/src/main/webapp/public/images")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10, location = "/Users/yjs/eclipse-workspace/FeedBoard/src/main/webapp/public/images")
public class MainController extends HttpServlet {
	private MembersDao mDao = null;
	private PostsDao pDao = null;
	private CommentsDao cDao = null;
	private ImageDao iDao = null;
	private LikesDao lDao = null;

	@Override
	public void init() throws ServletException {
		mDao = new MembersDao();
		pDao = new PostsDao();
		cDao = new CommentsDao();
		iDao = new ImageDao();
		lDao = new LikesDao();
		super.init();
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String context = req.getContextPath();
		String path = req.getServletPath();

		System.out.println("MainController : " + context + path);
		loginCheck(req, resp);

		String view = null;

		switch (path) {
		case "/post":
			view = postInfo(req);
			break;
		case "/newpost":
			view = "views/edit.jsp";
			break;
		case "/addpost":
			view = addPost(req, resp);
			return;
		case "/editpost":
			view = editPost(req);
			break;
		case "/updatepost":
			view = updatePost(req);
			break;
		case "/deletepost":
			view = deletePost(req);
			break;
		case "/addcomment":
			view = addComment(req);
			break;
		case "/deletecomment":
			view = deleteComment(req);
			break;
		case "/like":
			view = toggleLike(req);
			break;
		case "/home":
		default:
			view = getAllPosts(req);
			break;
		}

		if (view.startsWith("redirect:/")) {
			resp.sendRedirect(view.substring("redirect:/".length()));
		} else {
			getServletContext().getRequestDispatcher("/" + view).forward(req, resp);
		}
	}
	
	public String getUserUUID(HttpServletRequest req) {
		Object attr = req.getAttribute("user_uuid");
		String userUUID = attr == null ? null : (String)attr;
		return userUUID;
	}

	public void loginCheck(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		String sessionId = session.getId();
		req.removeAttribute("user_uuid");

		Cookie[] cookies = req.getCookies();
		if (cookies == null) return;
		for (Cookie c : cookies) {
			if (c.getName().equals("sessionId") && sessionId.equals(c.getValue())) {
				String user_uuid = (String) session.getAttribute(sessionId);
				req.setAttribute("user_uuid", user_uuid);
				req.setAttribute("user", mDao.getMember(user_uuid));
				c.setPath("/");
				c.setMaxAge(60 * 10);
				resp.addCookie(c);
				break;
			}
		}
	}
	
	public String getAllPosts(HttpServletRequest req) {
		String userUUID = getUserUUID(req);
		ArrayList<Post> posts = pDao.selectAll(userUUID);
		req.setAttribute("posts", posts);
		return "views/index.jsp";
	}

	public String postInfo(HttpServletRequest req) {
		String userUUID = getUserUUID(req);
		String postUUID = req.getParameter("id");
		Post post = pDao.getPost(postUUID, userUUID);
		ArrayList<Comment> comments = cDao.getComments(postUUID);
		req.setAttribute("post", post);
		req.setAttribute("comments", comments);
		return "views/index.jsp";
	}

	public String addPost(HttpServletRequest req, HttpServletResponse resp) {
		String userUUID = getUserUUID(req);
		String content = req.getParameter("post_content");
		String post_id = pDao.addPost(userUUID, content);
		saveImages(post_id, req);
		try {
			PrintWriter out = resp.getWriter();
			String msg = post_id != null ? "posting succeed" : "posting failed";

			out.println("<script>alert('" + msg + "');location.href='/FeedBoard'</script>");
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "views/index.jsp";
	}
	
	public String editPost(HttpServletRequest req) {
		String userUUID = getUserUUID(req);
		String postUUID = req.getParameter("post_uuid");
		Post post = pDao.getPost(postUUID, userUUID);
		req.setAttribute("post", post);
		return "views/edit.jsp";
	}
	
	public String updatePost(HttpServletRequest req) {
		String content = req.getParameter("post_content");
		String postUUID = req.getParameter("post_uuid");
		
		pDao.updatePost(content, postUUID);
		
		return "redirect:/home";
	}
	
	public String deletePost(HttpServletRequest req) {
		String postUUID = req.getParameter("post_uuid");
		pDao.deletePost(postUUID);
		
		return "redirect:/home";
	}
	
	public String addComment(HttpServletRequest req) {
		String postUUID = req.getParameter("post_uuid");
		String userUUID = getUserUUID(req);
		String content = req.getParameter("comment_content"); 
		
		cDao.addComment(postUUID, userUUID, content);
		return "redirect:/post?id=" + postUUID;
	}
	
	public String deleteComment(HttpServletRequest req) {
		String postUUID = req.getParameter("post_id");
		String commentUUID = req.getParameter("comment_id");
		cDao.deleteComment(commentUUID);
		
		return "redirect:/post?id=" + postUUID;
	}
	
	public String toggleLike(HttpServletRequest req) {
		String postUUID = req.getParameter("post_uuid");
		String userUUID = getUserUUID(req);
		boolean on = req.getParameter("on").equals("1");
		lDao.toggleLike(postUUID, userUUID, on);
		
		return "redirect:/post?id=" + postUUID;
	}

	public ArrayList<String> saveImages(String postUUID, HttpServletRequest req) {
		ArrayList<String> filenames = new ArrayList<>();

		try {
			Collection<Part> parts = req.getParts();
			for (Part p : parts) {
				if (!p.getName().equals("images"))
					continue;
				String contentDispositionHeader = p.getHeader("content-disposition");

				String[] elements = contentDispositionHeader.split(";");
				String filename = null;
				for (String el : elements) {
					if (el.trim().startsWith("filename")) {
						filename = el.split("=")[1].replace("\"", "");
						break;
					}
				}
				if (filename.equals("")) return filenames;
				String[] splittedFileName = filename.split("\\.");
				String ext = splittedFileName[splittedFileName.length - 1];
				String uuid = UUID.randomUUID().toString();
				String resultFileName = uuid + "." + ext;
				filenames.add(resultFileName);
				p.write(resultFileName);
			}
		} catch (Exception e) {
			System.out.println("Fck");
			e.printStackTrace();
		}
		iDao.addImages(postUUID, filenames);

		return filenames;
	}
}
