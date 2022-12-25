package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MembersDao;
import dao.PostsDao;

@WebServlet("/")
public class MainController extends HttpServlet {
	private MembersDao mDao = new MembersDao();
	private PostsDao pDao = new PostsDao();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String context = req.getContextPath();
		String path = req.getServletPath();
		
		System.out.println(context + path);
		
		String view = "index.jsp";
		
		switch (path) {
		default :
			req.setAttribute("posts", pDao.selectAll());
			break;
		}

		getServletContext().getRequestDispatcher("/views/" + view).forward(req, resp);
	}
}
