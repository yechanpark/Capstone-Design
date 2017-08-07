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
@WebServlet("/Add_Poster_DB")
public class Add_Poster_DB extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Add_Poster_DB() {
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
			/* factory ����, xml������ ������� �����Ѵ� */
			BeanFactory factory = new XmlBeanFactory(
					new FileSystemResource("C:/Users/Administrator/workspace/CapstoneDesign/WebContent/db.xml"));

			/* DBĿ�ؼ� ���� */
			ConnectionDAO connMaker = factory.getBean("connMaker", ConnectionDAO.class);
			Connection con = connMaker.getConn();;
			Statement st = con.createStatement();
			
			////* ������ �̹������� ���� *////
			request.setCharacterEncoding("euc-kr");
			Path p = factory.getBean("Server_image_path", Path.class);
			String realFolder = p.getImage_path();
			String encType = "euc-kr";
			String filename1 = "Null";
			int maxSize = 1024 * 1024 * 5;

			// jsp���� enctype="multipart/form-data" �� ��⶧���� �׳� request.getParm
			// ���δ� ���� �� ����
			MultipartRequest multi = new MultipartRequest(request, realFolder, maxSize, encType,
					new DefaultFileRenamePolicy());
			Enumeration<?> files = multi.getFileNames();
			////* ������ �̹������� ���� ��*////
			
			// ���� �̸�
			String file1 = (String) files.nextElement();
			filename1 = multi.getFilesystemName(file1);
			
			//Ÿ��
			String Type = multi.getParameter("Type");
			String path_file = "null";
			
			//��¥
			String sub_dates = multi.getParameter("dates");
			String[] date = sub_dates.split("/");
			String year = date[2];
			String month = date[0];
			String day = date[1];
			String dates = year+month+day;
			
			//������ Ÿ���� ��� ���ϰ�ο� ���� �̸��� ��ħ
			if(Type.equals("Poster"))
				path_file = realFolder + "/" + filename1;
			
			// ���ܹ�ȣ
			String Beacon_no = multi.getParameter("Beacon_no");
			String URL = multi.getParameter("URL");

			//// * DB���� *////
			// ���ڵ� �⺻Ű ����
			ResultSet rs = st.executeQuery("select max(Primary_key) max_key from test");
			rs.next();
			int primary_key = rs.getInt("max_key") + 1;
			
			System.out.println(path_file);
			st.executeUpdate("insert into test values('" + primary_key + "','" + Beacon_no + "','" + path_file + "','"+URL+"','"+filename1+"','"+Type+"','null','null','null','"+dates+"')");
			//// *DB ���� ��*////
			
			request.setAttribute("primary_key", primary_key);
			request.setAttribute("beacon_no", Beacon_no);
			request.setAttribute("filename", filename1);
			request.setAttribute("path_file", path_file);
			request.setAttribute("url", URL);
			request.setAttribute("type", Type);


			/* ��� ����ϴ� jsp�� �Ѿ */
			String page = "/jsps/results/add_Poster_result.jsp";
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
