package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import DB.ConnectionDAO;
import DB.Path;

/**
 * Servlet implementation class Adjust_Poster_DB
 */
@WebServlet("/Adjust_Survey_DB")
public class Adjust_Survey_DB extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Adjust_Survey_DB() {
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
		PrintWriter out = response.getWriter();

		/* factory 정의, xml파일의 빈네임을 참조한다 */
		BeanFactory factory = new XmlBeanFactory(
				new FileSystemResource("C:/Users/Administrator/workspace/CapstoneDesign/WebContent/db.xml"));

		try {
			/* DB커넥션 설정 */
			ConnectionDAO connMaker = factory.getBean("connMaker", ConnectionDAO.class);
			Connection con = connMaker.getConn();
			Statement st = con.createStatement();
			ResultSet rs;

			String Beacon_no = request.getParameter("Beacon_no");
			String Record_no = request.getParameter("Record_No");


			String Type = request.getParameter("Type");
			String URL = request.getParameter("URL");
			String Writer = request.getParameter("Writer");
			String Reward = request.getParameter("Reward");
			String RewardType = request.getParameter("RewardType");

			st.executeUpdate("update test set Beacon='" + Beacon_no + "',Writer='" + Writer + "',URL='" + URL
					+ "', Reward='" + Reward + "', RewardType='" + RewardType + "' where Primary_key=" + Record_no);

			request.setAttribute("primary_key", Record_no);
			request.setAttribute("beacon_no", Beacon_no);
			request.setAttribute("Writer", Writer);
			request.setAttribute("Reward", Reward);
			request.setAttribute("RewardType", RewardType);
			request.setAttribute("url", URL);
			request.setAttribute("type", Type);

			/* 결과 출력하는 jsp로 넘어감 */
			String page = "/jsps/results/adjust_Survey_result.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(page);
			dispatcher.forward(request, response);

			st.close();
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
