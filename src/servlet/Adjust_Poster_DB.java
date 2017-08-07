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
@WebServlet("/Adjust_Poster_DB")
public class Adjust_Poster_DB extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Adjust_Poster_DB() {
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
			request.setCharacterEncoding("euc-kr");
			Path p = factory.getBean("Server_image_path", Path.class);
			String realFolder = p.getImage_path();
			String encType = "euc-kr";
			String filename1 = "Null";
			int maxSize = 1024 * 1024 * 5;
			
			// jsp에서 enctype="multipart/form-data" 를 썼기때문에 그냥 request.getParm
			// 으로는 받을 수 없음
			MultipartRequest multi = new MultipartRequest(request, realFolder, maxSize, encType,
					new DefaultFileRenamePolicy());
			Enumeration<?> files = multi.getFileNames();
			
			
			String Record_no = multi.getParameter("Record_No");
			rs = st.executeQuery("select Filename from test where Primary_key="+Record_no);
			rs.next();
			
			/*파일삭제*/
			File f = new File(p.getImage_path()+"/"+rs.getString(1));
			f.delete();
			
			String Type = multi.getParameter("Type");
			String Beacon_no = multi.getParameter("Beacon_no");
			String URL = multi.getParameter("URL");
			String file1 = (String) files.nextElement();
			filename1 = multi.getFilesystemName(file1);
			
			String File_Path = "null";
			
			//포스터 타입인 경우 파일경로와 파일 이름을 합침
			if(Type.equals("Poster"))
				File_Path = realFolder + "/" + filename1;


			
			st.executeUpdate("update test set Beacon='" + Beacon_no + "',File_Path='" + File_Path + "',URL='" + URL
					+ "', Filename='"+ filename1 +"' where Primary_key=" + Record_no);

			request.setAttribute("primary_key", Record_no);
			request.setAttribute("beacon_no", Beacon_no);
			request.setAttribute("path_file", File_Path);
			request.setAttribute("filename", filename1);
			request.setAttribute("url", URL);
			request.setAttribute("type", Type);

			/* 결과 출력하는 jsp로 넘어감 */
			String page = "/jsps/results/adjust_Poster_result.jsp";
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
