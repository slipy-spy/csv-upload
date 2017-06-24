package com.spy.csv.upload.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.mail.internet.MimeUtility;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 文件下载
 * 
 * @author shipy
 *
 */
public class DownloadServlet extends HttpServlet {
	private final Log log = LogFactory.getLog(DownloadServlet.class);

	/**
	 * Constructor of the object.
	 */
	public DownloadServlet() {
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
		log.info("开始触发下载文件操作");

		File file = null;
		FileInputStream fis = null;
		ServletOutputStream sos = null;
		// path
		String path = "F:/book/";
		String filename = "重生之娱乐鬼才.txt";
		try {
			file = new File(path + filename);
			if (file.exists()) {
				// 设置相应类型，让浏览器知道用什么打开
				response.setContentType("application/x-download charset=UTF-8");

				String contentDisposition = "", browser = getBrowser(request);
				if ("IE".equals(browser)) {
					contentDisposition = "attachment; filename="
							+ URLEncoder.encode(filename, "UTF-8").replace("+", "%20");
				} else if ("CH".equals(browser)) {
					contentDisposition = "attachment; filename=" + MimeUtility.encodeText(filename, "UTF-8", "B");
				} else if ("SF".equals(browser)) {
					contentDisposition = "attachment; filename=" + new String(filename.getBytes("UTF-8"), "ISO8859-1");
				} else {
					contentDisposition = "attachment; filename=*=UTF-8''"
							+ URLEncoder.encode(filename, "UTF-8").replace("+", "%20");
				}
				// 设置头信息
				response.setHeader("Content-Disposition", contentDisposition);

				fis = new FileInputStream(file);
				sos = response.getOutputStream();

				int byteCount = 0;
				if (fis != null) {
					byte[] buff = new byte[1024];
					int byteRead;
					while (-1 != (byteRead = fis.read(buff, 0, buff.length))) {
						sos.write(buff, 0, byteRead);
						sos.flush();
						byteCount += byteRead;
					}
				}
				sos.flush();

				log.info("文件下载成功！");
			} else {
				log.info("文件不存在，下载失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("文件下载出错！");
		} finally {
			fis.close();
			sos.close();
		}
	}

	/**
	 * 获取客户端的信息
	 * 
	 * @param request
	 * @return
	 */
	private String getBrowser(HttpServletRequest request) {
		// 获取客户端的浏览器信息
		String userAgent = request.getHeader("USER-AGENT").toLowerCase();
		log.info("客户端浏览器类型：" + userAgent);
		// 获取客户端的IP地址
		String remoteAddr = request.getRemoteAddr();
		log.info("客户端的IP：" + remoteAddr);
		if (userAgent.indexOf("msie") >= 0) {
			return "IE";
		} else if (userAgent.indexOf("mozilla") >= 0) {
			return "FF";
		} else if (userAgent.indexOf("applewebkit") >= 0) {
			return "CH";
		} else if (userAgent.indexOf("safari") >= 0) {
			return "SF";
		} else if (userAgent.indexOf("opera") >= 0) {
			return "OP";
		}
		return null;
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
