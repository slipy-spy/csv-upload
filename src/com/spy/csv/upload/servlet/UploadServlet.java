package com.spy.csv.upload.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spy.db.ConnectionPool;

/**
 * @author shipy
 *
 */
public class UploadServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UploadServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String method = request.getParameter("method");
		if ("upload".equalsIgnoreCase(method)) {
			upload(request, response);
		} else if ("loadDB".equalsIgnoreCase(method)) {
			loadDB(request, response);
		}
	}

	@SuppressWarnings("unused")
	private void loadDB(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet res = null;
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.createStatement();

			String str = "select * from student";
			res = stmt.executeQuery(str);
			while (res.next()) {
				String id = res.getString("id");
				String name = res.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				ConnectionPool.releaseConnection(conn);
			}
			stmt = null;
			res = null;
		}
	}

	private void upload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.getWriter().append("{\"message\":\"hello\"}");
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
