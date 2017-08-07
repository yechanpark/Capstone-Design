package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

import DB.Article;
import DB.ConnectionDAO;
import DB.PageshowDao;

/**
 * Servlet implementation class Search_DB
 */
@WebServlet("/Search_DB")
public class Search_DB extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Search_DB() {
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
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		responseAjax(request,response);


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		responseAjax(request,response);
		
	}
	private void responseAjax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			String platform = request.getParameter("platform");
			String searching_Mode = request.getParameter("searching_mode");
			String Beacon_no = "";
			if(searching_Mode.equals("search_Beacon_no"))
				Beacon_no = request.getParameter("Beacon_no");
			
			ArrayList<Article> articles = new ArrayList<Article>();
			
			/*factory 정의, xml파일의 빈네임을 참조한다*/
			BeanFactory factory = new XmlBeanFactory(new FileSystemResource("C:/Users/Administrator/workspace/CapstoneDesign/WebContent/db.xml"));
			
			/*몇개의 페이지를 보여줄 것인지 xml의 showpageDAO빈네임을 참조하여 설정 */
			int showArticles;
			PageshowDao showPage = factory.getBean("showpageDAO", PageshowDao.class);
			showArticles = showPage.getshowPage();
			
			/*DB커넥션 설정*/
			ConnectionDAO connMaker = factory.getBean("connMaker", ConnectionDAO.class);
			Connection con = connMaker.getConn();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select Primary_key,Beacon,File_Path,URL,Filename,Type from test"); //쿼리문

			/*rs의 결과가 끝날때 까지(db의 끝까지)혹은 보여줄 페이지 (xml에 지정)만큼 돈다*/
			//for (int i = 0; i < showArticles && rs.next(); i++) {
			//전체조회
			for (; rs.next();) {
				
				/*art에 for가 돌때마다 DB에서 가져온 Article정보를 저장*/
				Article art = new Article();
				art.setRecord(rs.getInt(1));
				art.setBeacon_no(rs.getString(2));
				art.setPath(rs.getString(3).toString());
				art.setURL(rs.getString(4).toString());
				art.setFilename(rs.getString(5).toString());
				art.setType(rs.getString(6).toString());
				
				if(searching_Mode.equals("search_Beacon_no")){
					if( Beacon_no.equals(art.getBeacon_no())  )
						articles.add(art); //아티클 벡터에 현재의 art변수 1개 추가(현재 DB에서 불러온 정보를 저장한 art변수를 저장)
				}
				else
					articles.add(art);
			}
			
			//콜렉션 setAttribute
			request.setAttribute("articles", articles);

			String page;
			page = "/jsps/results/search_result.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(page);
			dispatcher.forward(request, response);

			rs.close();
			st.close();
			con.close();
			System.out.println("Good!! Search_DB open ");

		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("error!! Search_DB open ");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
