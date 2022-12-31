package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MembersDao;
import dto.Member;

@WebServlet("/auth/*")
public class AuthController extends HttpServlet {
	private MembersDao mDao = null;
	
	@Override
	public void init() throws ServletException {
		mDao = new MembersDao();
		super.init();
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String context = req.getContextPath();
		String path = req.getPathInfo();

		System.out.println("AuthController : " + context + path);

		String view = "";
		
		switch (path) {
		case "/login":
			login(req, resp);
			return;
		case "/logout":
			view = logout(req, resp);
			break;
		case "/signup":
			view = signup(req, resp);
			return;
		default:
			break;
		}

		if (view.startsWith("redirect:/")) {
			resp.sendRedirect(context + view.substring("redirect:".length()));
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
				req.setAttribute("user_uuid", user_uuid);
				HttpSession session = req.getSession();
				String sessionId = session.getId();
				session.setAttribute(sessionId, user_uuid);
				Cookie cookie = new Cookie("sessionId", sessionId);
				cookie.setPath("/");
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

	public String logout(HttpServletRequest req, HttpServletResponse resp) {
		Cookie[] cookies = req.getCookies();
		for (Cookie c : cookies) {
			if (c.getName().equals("sessionId")) {
				c.setPath("/");
				c.setMaxAge(0);
				resp.addCookie(c);
				break;
			}
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
			e.printStackTrace();
		}

		return "redirect:/home";
	}
}
