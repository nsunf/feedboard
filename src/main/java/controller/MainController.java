package controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CommentsDao;
import dao.MembersDao;
import dao.PostsDao;
import dto.Comment;
import dto.Member;
import dto.Post;

@WebServlet("/")
public class MainController extends HttpServlet {
	private MembersDao mDao = new MembersDao();
	private PostsDao pDao = new PostsDao();
	private CommentsDao cDao = new CommentsDao();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String context = req.getContextPath();
		String path = req.getServletPath();

		System.out.println(context + path);
		System.out.println(isLoggedin(req, resp));

		String view = "index.jsp";

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
			saveImages(req);
//			view = addPost(req, resp);
			return;
		case "/home":
		default:
			req.setAttribute("posts", pDao.selectAll());
			break;
		}

		if (view.startsWith("redirect:/")) {
			resp.sendRedirect(view.substring("redirect:/".length()));
		} else {
			getServletContext().getRequestDispatcher("/views/" + view).forward(req, resp);
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
				Cookie cookie1 = new Cookie("login_uuid", key);
				cookie1.setMaxAge(60 * 10);
				resp.addCookie(cookie1);
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
		return "index.jsp";
	}
	
	public String addPost(HttpServletRequest req, HttpServletResponse resp) {
		boolean result = pDao.addPost(req);
		try {
			PrintWriter out = resp.getWriter();
			String msg = result ?  "posting succeed" : "posting failed";

			out.println("<script>alert('" + msg + "');location.href='/FeedBoard'</script>");
			out.flush();
			out.close();
		} catch (Exception e) {
		}
		return "index.jsp";
	}

	public boolean isLoggedin(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		String key = (String)session.getAttribute("login_uuid");
		String cookieKey = null;
		
		Cookie[] cookies = req.getCookies();
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

	public void saveImages(HttpServletRequest req) {
		System.out.println(req.getParameter("image-base64"));
		try { Thread.sleep(5000); } catch (Exception e) { }
		System.out.println("hehe");
//		for (String base64 : base64Arr) {
//			System.out.println("---> " + base64);
//			String ext = base64.split(";")[0].split("/")[1];
//			String uuid = UUID.randomUUID().toString();
//			File file = new File("public/images/" + uuid + "." + ext);
//			
//			try {
//				FileOutputStream fo = new FileOutputStream(file);
//				BufferedOutputStream bo = new BufferedOutputStream(fo);
//				
//				byte[] bytes = base64.getBytes(); 
//				
//				bo.write(bytes);
//				bo.flush();
//				bo.close();
//				fo.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}
}
