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
import dao.MembersDao;
import dao.PostsDao;
import dto.Comment;
import dto.Member;
import dto.Post;

@WebServlet("/")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10, location = "d:/project/java/FeedBoard/src/main/webapp/public/images")
public class MainController extends HttpServlet {
	private MembersDao mDao = null;
	private PostsDao pDao = null;
	private CommentsDao cDao = null;
	private ImageDao iDao = null;
	
	@Override
	public void init() throws ServletException {
		mDao = new MembersDao();
		pDao = new PostsDao();
		cDao = new CommentsDao();
		iDao = new ImageDao();
		super.init();
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String context = req.getContextPath();
		String path = req.getServletPath();

		System.out.println(context + path);
		System.out.println(isLoggedin(req, resp));

		String view = "views/index.jsp";

		switch (path) {
		case "/login":
			login(req, resp);
			return;
		case "/logout":
			view = logout(req);
			break;
		case "/signup":
			view = signup(req, resp);
			break;
		case "/post":
			view = postInfo(req);
			break;
		case "/newpost":
			view = "edit.jsp";
			break;
		case "/addpost":
			view = addPost(req, resp);
			return;
		case "/editpost":
			req.setAttribute("post", pDao.getPost(req.getParameter("post_id")));
			view = "views/edit.jsp";
			break;
		case "/updatepost":
			pDao.updatePost(req);
			view = "redirect:/home";
			break;
		case "/deletepost":
			pDao.deletePost(req);
			view = "redirect:/home";
			break;
		case "/addcomment":
			cDao.addComment(req);
			view = "redirect:/post?id=" + req.getParameter("post_id");
			break;
		case "/deletecomment":
			cDao.deleteComment(req);
			view = "redirect:/post?id=" + req.getParameter("post_id");
			break;
		case "/home":
		default:
			req.setAttribute("posts", pDao.selectAll());
			break;
		}

		if (view.startsWith("redirect:/")) {
			resp.sendRedirect(view.substring("redirect:/".length()));
		} else {
			getServletContext().getRequestDispatcher("/" + view).forward(req, resp);
		}
	}

	public void login(HttpServletRequest req, HttpServletResponse resp) {
		String user_id = req.getParameter("user_id");
		String user_pw = req.getParameter("user_pw");
		String user_uuid = mDao.login(user_id, user_pw);

		try {
			PrintWriter out = resp.getWriter();
			String msg = "login failed";
			if (user_uuid != null) {
				msg = "login succeed";
				HttpSession session = req.getSession();
				String key = UUID.randomUUID().toString();
				session.setAttribute("login_uuid", key);
				session.setAttribute("user_uuid", user_uuid);
				Cookie cookie = new Cookie("login_uuid", key);
				cookie.setMaxAge(60 * 10);
				resp.addCookie(cookie);
			}

			out.println("<script>alert('" + msg + "');location.href='/FeedBoard'</script>");
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String logout(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.removeAttribute("login_uuid");
		session.removeAttribute("user_uuid");
		
		Cookie[] cookies = req.getCookies();
		for (Cookie c: cookies) {
			c.setMaxAge(0);
		}

		return "redirect:/home";
	}

	public boolean isLoggedin(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		String key = (String)session.getAttribute("login_uuid");
		String cookieKey = null;
		
		Cookie[] cookies = req.getCookies();
		if (cookies == null) return false;
		Cookie login_uuid_cookie = null;
		for (Cookie c : cookies) {
			if (c.getName().equals("login_uuid")) {
				cookieKey = c.getValue();
				login_uuid_cookie = c;
				login_uuid_cookie.setMaxAge(60 * 10);
			}
		}

		boolean result = key != null && cookieKey != null && key.equals(cookieKey); 
		if (result) {
			String user_uuid = (String) session.getAttribute("user_uuid");
			req.setAttribute("user", mDao.getMember(user_uuid));
			resp.addCookie(login_uuid_cookie);
		}
		return result;
	}

	public String signup(HttpServletRequest req, HttpServletResponse resp) { 
		Member m = new Member();
		String user_id = req.getParameter("user_id");
		String user_pw = req.getParameter("user_pw");
		String user_name = req.getParameter("user_name");
		String user_ssn = req.getParameter("user_ssn").replace("-", "");
		String user_phone = req.getParameter("user_phone");
		m.setUuid(UUID.randomUUID().toString());
		m.setId(user_id);
		m.setPw(user_pw);
		m.setName(user_name);
		m.setSsn(user_ssn);
		m.setPhone(user_phone);
		
		boolean result = mDao.signUp(m);
		
		try {
			PrintWriter out = resp.getWriter();
			String msg = "signup failed";
			if (result) {
				msg = "signup succeed";
			}
			out.println("<script>alert('" + msg + "');location.href='/FeedBoard'</script>");
			out.flush();
			out.close();
		} catch (Exception e) {
			
		}
		
		return "redirect:/home";
	}

	public String postInfo(HttpServletRequest req) {
		String postId = req.getParameter("id");
		Post post = pDao.getPost(postId);
		ArrayList<Comment> comments = cDao.getComments(postId);
		req.setAttribute("post", post);
		req.setAttribute("comments", comments);
		return "views/index.jsp";
	}
	
	public String addPost(HttpServletRequest req, HttpServletResponse resp) {
		String post_id = pDao.addPost(req);
		saveImages(post_id, req);
		try {
			PrintWriter out = resp.getWriter();
			String msg = post_id != null ?  "posting succeed" : "posting failed";

			out.println("<script>alert('" + msg + "');location.href='/FeedBoard'</script>");
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "views/index.jsp";
	}


	public ArrayList<String> saveImages(String post_id, HttpServletRequest req) {
		ArrayList<String> filenames = new ArrayList<>();

		try {
			Collection<Part> parts = req.getParts();
			for (Part p : parts) {
				String contentDispositionHeader = p.getHeader("content-disposition");
				if (!p.getName().equals("images")) continue;

				String[] elements = contentDispositionHeader.split(";");
				String filename = null;
				for (String el : elements) {
					if (el.trim().startsWith("filename")) {
						filename = el.split("=")[1].replace("\"", "");
						break;
					}
				}
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
		iDao.addImages(post_id, filenames);
		
		return filenames;
	}
}
