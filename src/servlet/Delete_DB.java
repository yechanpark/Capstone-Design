package servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

import DB.Article;
import DB.ConnectionDAO;

/**
 * Servlet implementation class Delete_DB
 */
@WebServlet("/Delete_DB")
public class Delete_DB extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Delete_DB() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {

			String Record_no = request.getParameter("Record_no");

			/* factory 정의, xml파일의 빈네임을 참조한다 */
			BeanFactory factory = new XmlBeanFactory(
					new FileSystemResource("C:/Users/Administrator/workspace/CapstoneDesign/WebContent/db.xml"));

			/* DB커넥션 설정 */
			ConnectionDAO connMaker = factory.getBean("connMaker", ConnectionDAO.class);
			Connection con;
			con = connMaker.getConn();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from test where Primary_key='" + Record_no + "'");
			rs.next();
			Article art = new Article();
			art.setRecord(rs.getInt(1));
			art.setBeacon_no(rs.getString(2));
			art.setPath(rs.getString(3));
			art.setURL(rs.getString(4));
			request.setAttribute("article", art);

			System.out.println("Good!! Delete_DB open ");

			/* 결과 출력하는 jsp로 넘어감 */
			String page = "/jsps/results/delete_result.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(page);
			dispatcher.forward(request, response);

			String filePath = art.getPath();
			File f = new File(filePath); // 파일 객체생성
			if (f.exists())
				f.delete(); // 파일이 존재하면 파일을 삭제한다.

			st.executeUpdate("delete from test where Primary_Key='" + Record_no + "'");
			
			
			st.close();
			con.close();

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block

			String page = "/jsps/results/error_DB.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(page);
			dispatcher.forward(request, response);

			e.printStackTrace();
		}
	}

}
