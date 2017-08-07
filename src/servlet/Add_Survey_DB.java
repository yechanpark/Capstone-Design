package servlet;

import java.io.IOException;
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
 * Servlet implementation class Add_Poster_DB
 */
@WebServlet("/Add_Survey_DB")
public class Add_Survey_DB extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Add_Survey_DB() {
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
		try {
			/* factory 정의, xml파일의 빈네임을 참조한다 */
			BeanFactory factory = new XmlBeanFactory(
					new FileSystemResource("C:/Users/Administrator/workspace/CapstoneDesign/WebContent/db.xml"));

			/* DB커넥션 설정 */
			ConnectionDAO connMaker = factory.getBean("connMaker", ConnectionDAO.class);
			Connection con = connMaker.getConn();;
			Statement st = con.createStatement();
			
			String Type =request.getParameter("Type");
			String Beacon_no = request.getParameter("Beacon_no");
			String URL = request.getParameter("URL");
			String Writer = request.getParameter("Writer");
			String RewardType = request.getParameter("RewardType");
			String Reward = request.getParameter("Reward");
			
			//날짜
			String sub_dates = request.getParameter("dates");
			String[] date = sub_dates.split("/");
			String year = date[2];
			String month = date[0];
			String day = date[1];
			String dates = year+month+day;
			
			//// * DB저장 *////
			// 레코드 기본키 설정
			ResultSet rs = st.executeQuery("select max(Primary_key) max_key from test");
			rs.next();
			int primary_key = rs.getInt("max_key") + 1;
			
			st.executeUpdate("insert into test values('" + primary_key + "','" + Beacon_no + "','null','"+URL+"','null','"+Type+"','"+Writer+"','"+Reward+"','"+RewardType+"','"+dates+"')");
			//// *DB 저장 끝*////
			
			request.setAttribute("primary_key", primary_key);
			request.setAttribute("beacon_no", Beacon_no);
			request.setAttribute("RewardType", RewardType);
			request.setAttribute("Reward", Reward);
			request.setAttribute("url", URL);
			request.setAttribute("type", Type);
			request.setAttribute("Writer", Writer);


			/* 결과 출력하는 jsp로 넘어감 */
			String page = "/jsps/results/add_Survey_result.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(page);
			dispatcher.forward(request, response);

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
