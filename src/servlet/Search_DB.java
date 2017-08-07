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
			
			/*factory ����, xml������ ������� �����Ѵ�*/
			BeanFactory factory = new XmlBeanFactory(new FileSystemResource("C:/Users/Administrator/workspace/CapstoneDesign/WebContent/db.xml"));
			
			/*��� �������� ������ ������ xml�� showpageDAO������� �����Ͽ� ���� */
			int showArticles;
			PageshowDao showPage = factory.getBean("showpageDAO", PageshowDao.class);
			showArticles = showPage.getshowPage();
			
			/*DBĿ�ؼ� ����*/
			ConnectionDAO connMaker = factory.getBean("connMaker", ConnectionDAO.class);
			Connection con = connMaker.getConn();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select Primary_key,Beacon,File_Path,URL,Filename,Type from test"); //������

			/*rs�� ����� ������ ����(db�� ������)Ȥ�� ������ ������ (xml�� ����)��ŭ ����*/
			//for (int i = 0; i < showArticles && rs.next(); i++) {
			//��ü��ȸ
			for (; rs.next();) {
				
				/*art�� for�� �������� DB���� ������ Article������ ����*/
				Article art = new Article();
				art.setRecord(rs.getInt(1));
				art.setBeacon_no(rs.getString(2));
				art.setPath(rs.getString(3).toString());
				art.setURL(rs.getString(4).toString());
				art.setFilename(rs.getString(5).toString());
				art.setType(rs.getString(6).toString());
				
				if(searching_Mode.equals("search_Beacon_no")){
					if( Beacon_no.equals(art.getBeacon_no())  )
						articles.add(art); //��ƼŬ ���Ϳ� ������ art���� 1�� �߰�(���� DB���� �ҷ��� ������ ������ art������ ����)
				}
				else
					articles.add(art);
			}
			
			//�ݷ��� setAttribute
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
