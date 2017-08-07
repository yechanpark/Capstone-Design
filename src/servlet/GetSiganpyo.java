package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

import DB.ConnectionDAO;
import DB.Siganpyo_info;
import DB.Total_siganpyo;

/**
 * Servlet implementation class GetSiganpyo
 */
@WebServlet("/GetSiganpyo")
public class GetSiganpyo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetSiganpyo() {
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
		ArrayList<String> majors = new ArrayList<>();
		URL nURL;
		String cookie = null;
		String JSESSIONID = null;
		HttpClient httpclient = new DefaultHttpClient();
		String line;

		/* factory ����, xml������ ������� �����Ѵ� */
		BeanFactory factory = new XmlBeanFactory(
				new FileSystemResource("C:/Users/Administrator/workspace/CapstoneDesign/WebContent/db.xml"));

		/* DBĿ�ؼ� ���� */
		ConnectionDAO connMaker = factory.getBean("connMaker", ConnectionDAO.class);
		Connection con;

		try {
			con = connMaker.getConn();

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select Id,Password from Account");
			rs.next();

			String Id = rs.getString(1);
			String passwd = rs.getString(2);

			// ��Ű�� ������ login Form ( �α��� â�� url �� �ƴϰ� �α����� ó���ϴ� url�̾�� ��)
			HttpPost httppost = new HttpPost("http://info.hansung.ac.kr/servlet/s_gong.gong_login_ssl");

			try {
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

				// parameter //

				// ���� id�� id�� name. <input name=id>
				nameValuePairs.add(new BasicNameValuePair("id", Id));

				// ���� passwd�� password�� name. <input name=passwd>
				nameValuePairs.add(new BasicNameValuePair("passwd", passwd));

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse responser = httpclient.execute(httppost);

				// Response ���� ��Ű �� (response���� ��� ������ �Ҵ� (���� �������� ����))
				int a, b;
				a = responser.toString().indexOf("JSESSIONID=") + "JSESSIONID=".length();
				b = responser.toString().substring(a).indexOf(";");

				// ��Ⱚ
				cookie = responser.toString().substring(a).substring(0, b);

				// getHeaders()�� �������� ���, �� ���簡 �ʿ���
				// cookie = response.getHeaders("Set-Cookie").toString();

				// ��Ű���� JSESSION id�� ����
				JSESSIONID = cookie;

				// ��Ű��(JSESSIONID) ���
				System.out.println("cookie : " + cookie);
				System.out.println("-------------------------------------");

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// �α��� ����� ���������� �����;��� url����
			// ���� �⵵, ��, �б� ����
			Calendar cal = Calendar.getInstance();
			int year = cal.get(cal.YEAR);
			int month = cal.get(cal.MONTH) + 1;
			int semester;

			if (month < 7)
				semester = 1;
			else
				semester = 2;

			String major_url = "http://info.hansung.ac.kr/servlet/s_jik.jik_siganpyo_s_up";

			/* majorcode�� �Ľ��ؼ� �޾ƿ��� �κ� */
			try {

				nURL = new URL(major_url);
				HttpURLConnection conn = (HttpURLConnection) nURL.openConnection();

				if (conn != null) {
					conn.setConnectTimeout(10000);
					conn.setUseCaches(false);
					// �α��� ���� ��Ű���� �����ͼ� ����(���ϸ� �α��� �ν� ����)
					conn.setRequestProperty("Cookie", "JSESSIONID=" + JSESSIONID);
					conn.getRequestProperties();

					if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
						BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "euc-kr"));

						String html = "";
						while ((line = br.readLine()) != null)
							html = html + line;
						br.close();

						Document doc = Jsoup.parse(html, "", Parser.xmlParser());

						Elements majorcodes = doc.select("form[name=userForm]").select("select[name=majorcode]")
								.select("option");

						for (Element e : majorcodes)
							majors.add(e.attr("value"));

					}
					conn.disconnect();
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (StringIndexOutOfBoundsException e1) {
				e1.printStackTrace();
			}
			// �ð�ǥ html �Ľ� ����
			Siganpyo_info si = new Siganpyo_info();
			System.out.println("start http parsing");
			for (int i = 0; i < majors.size(); i++) {

				String majorcode = majors.get(i);
				String result_url = "http://info.hansung.ac.kr/servlet/s_jik.jik_siganpyo_s_list?year=" + year
						+ "&semester=" + semester + "&majorcode=" + majorcode;

				try {

					nURL = new URL(result_url);
					HttpURLConnection conn = (HttpURLConnection) nURL.openConnection();

					if (conn != null) {
						conn.setConnectTimeout(10000);
						conn.setUseCaches(false);
						// �α��� ���� ��Ű���� �����ͼ� ����(���ϸ� �α��� �ν� ����)
						conn.setRequestProperty("Cookie", "JSESSIONID=" + JSESSIONID);
						conn.getRequestProperties();

						if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
							BufferedReader br = new BufferedReader(
									new InputStreamReader(conn.getInputStream(), "euc-kr"));

							String html = "";
							while ((line = br.readLine()) != null) {
								// �� �а������� �ð�ǥ html�ҽ� �״�� ���
								// System.out.println(line);
								html = html + line;
							}
							br.close();

							// String type ���� html�� document���� �־ �Ľ�
							Document doc = Jsoup.parse(html, "", Parser.xmlParser());

							// ǥ�� ���� "����" �� �����ϰ� ���� ���ϰ�
							Elements week = doc.select("tr").not("tr:eq(0)").select("td:eq(9)");
							// ǥ�� ���� "����" �� �����ϰ� ���� ���ð�
							Elements period = doc.select("tr").not("tr:eq(0)").select("td:eq(10)");
							// ǥ�� ���� "���ǽ�" �� �����ϰ� ���� ���ǽǰ�
							Elements classroom = doc.select("tr").not("tr:eq(0)").select("td:eq(11)");

							for (Element e : week) {
								String wk = e.text().replace("\u00a0", "");
								si.setWeek(wk);
							}

							for (Element e : period) {
								String pr = e.text().replace("\u00a0", "");
								si.setPeriod(pr);
							}

							for (Element e : classroom) {
								String cs = e.text().replace("\u00a0", "");
								si.setClassroom(cs);
							}
						}
						conn.disconnect();
					}

				} catch (IOException e) {
					e.printStackTrace();
				} catch (StringIndexOutOfBoundsException e1) {
					e1.printStackTrace();
				}
			}
			// �ð�ǥ �Ľ̿Ϸ�
			System.out.println("http parsing complete");

			// ���� �ð�ǥ���̺� db ��ü ����
			System.out.println("db delete start...");
			st.executeUpdate("delete * from Siganpyo");
			System.out.println("db delete complete");

			// �Ľ��� ����� db�� �ٽ� ����
			System.out.println("db re-insert start... ");
			ArrayList<String> weeks = si.getWeekArrlist(); // ����
			ArrayList<String> periods = si.getPeriodArrlist();// ����
			ArrayList<String> classrooms = si.getClassroomArrlist();// ���ǽ�
			ArrayList<String> timeslots = si.getTimeslotArrlist();// ���ǽ�

			for (int i = 0; i < si.getSize(); i++) {
				String week = weeks.get(i);
				int period = Integer.parseInt(periods.get(i));
				String[] strarr = classrooms.get(i).split("��");
				String building = strarr[0] + "��";
				String classroom = strarr[1];
				String timeslot = timeslots.get(i);

				// DB�� �Ľ� ��� ����
				st.executeUpdate("insert into Siganpyo(Week, Period, Building, Classroom, Timeslot, record) values('"
						+ week + "'," + period + ",'" + building + "','" + classroom + "','" + timeslot + "'," + i
						+ ")");
				if ((i + 1) % 100 == 0)
					System.out.println((i + 1) + "/" + (si.getSize() + 1) + "done");
			}

			System.out.println("db re-insert complete");
			System.out.println("siganpyo renewed");
			System.out.println("--------------------------------------------");
			System.out.println("extract empty_siganpyo from siganpyo start");

			// ���ǽ� db�� ���� Empty_Siganpyo(Building, Classroom, Week, Time)
			Total_siganpyo ts = new Total_siganpyo();

			// ���� db ����
			st.executeUpdate("delete * from Empty_Siganpyo");
			// �ð�ǥ db�� ������
			rs = st.executeQuery("select Week, Period, Building, Classroom, Timeslot from Siganpyo");

			try {
				for (; rs.next();) {

					String week = rs.getString(1);
					String period = rs.getString(2);
					String building = rs.getString(3);
					String classroom = rs.getString(4);
					String timeslot = rs.getString(5);

					// ��ü ���ǿ� ���� �������� ����
					ts.addCbuilding(building, classroom, period, timeslot, week);

				}
				// ������� ���
				ts.printSiganpyo();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			int record = 0;
			// ���� Ƚ����ŭ
			for (int building = 0; building < ts.getBuildingSize(); building++) {
				String buildingName = ts.getCbuilding(building).getBuildingName();

				// �� ���� ���ǽ� ���� ��ŭ
				for (int classroom = 0; classroom < ts.getCbuilding(building).getClassroomSize(); classroom++) {
					String classroomName = ts.getCbuilding(building).getClassroom(classroom).getClassroomName();

					// �� ���ǽ� ��~��
					for (int week = 0; week < ts.getCbuilding(building).getClassroom(classroom).getWeekSize(); week++) {
						String weekName = ts.getCbuilding(building).getClassroom(classroom).getWeekString(week);

						// �� ���� 0~15����
						for (int time = 0; time < ts.getCbuilding(building).getClassroom(classroom)
								.getTimesSize(); time++) {

							String timeName = ts.getCbuilding(building).getClassroom(classroom).getTimes(week, time);
								//���ǰ� ���� �͸� ��� db�� �Է� == ���ǽ�
								if(timeName == null){
								st.executeUpdate("insert into Empty_siganpyo(Building, Classroom, Week, Time,Record) values('"
										+ buildingName + "','" + classroomName + "','" + weekName + "','" + time
										+ "'," + (record++) + ")");
								}
						} // 0~15���� ��

					} // ��~�� ��

				} // ���ǽǰ��� ��

			} // ���� ��

			/* �������ϴ� jsp�� �Ѿ */
			String page = "/jsps/siganpyo_add_result.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(page);
			dispatcher.forward(request, response);

			st.close();
			con.close();

		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}

}
