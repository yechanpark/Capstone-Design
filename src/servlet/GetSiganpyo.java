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

		/* factory 정의, xml파일의 빈네임을 참조한다 */
		BeanFactory factory = new XmlBeanFactory(
				new FileSystemResource("C:/Users/Administrator/workspace/CapstoneDesign/WebContent/db.xml"));

		/* DB커넥션 설정 */
		ConnectionDAO connMaker = factory.getBean("connMaker", ConnectionDAO.class);
		Connection con;

		try {
			con = connMaker.getConn();

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select Id,Password from Account");
			rs.next();

			String Id = rs.getString(1);
			String passwd = rs.getString(2);

			// 쿠키를 가져올 login Form ( 로그인 창의 url 이 아니고 로그인을 처리하는 url이어야 함)
			HttpPost httppost = new HttpPost("http://info.hansung.ac.kr/servlet/s_gong.gong_login_ssl");

			try {
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

				// parameter //

				// 앞의 id는 id의 name. <input name=id>
				nameValuePairs.add(new BasicNameValuePair("id", Id));

				// 앞의 passwd는 password의 name. <input name=passwd>
				nameValuePairs.add(new BasicNameValuePair("passwd", passwd));

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse responser = httpclient.execute(httppost);

				// Response 에서 쿠키 값 (response에서 얻어 변수에 할당 (응답 본문에서 얻어옴))
				int a, b;
				a = responser.toString().indexOf("JSESSIONID=") + "JSESSIONID=".length();
				b = responser.toString().substring(a).indexOf(";");

				// 쿠기값
				cookie = responser.toString().substring(a).substring(0, b);

				// getHeaders()로 가져오는 방법, 더 조사가 필요함
				// cookie = response.getHeaders("Set-Cookie").toString();

				// 쿠키값을 JSESSION id에 삽입
				JSESSIONID = cookie;

				// 쿠키값(JSESSIONID) 출력
				System.out.println("cookie : " + cookie);
				System.out.println("-------------------------------------");

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// 로그인 적용시 최종적으로 가져와야할 url설정
			// 현재 년도, 월, 학기 지정
			Calendar cal = Calendar.getInstance();
			int year = cal.get(cal.YEAR);
			int month = cal.get(cal.MONTH) + 1;
			int semester;

			if (month < 7)
				semester = 1;
			else
				semester = 2;

			String major_url = "http://info.hansung.ac.kr/servlet/s_jik.jik_siganpyo_s_up";

			/* majorcode를 파싱해서 받아오는 부분 */
			try {

				nURL = new URL(major_url);
				HttpURLConnection conn = (HttpURLConnection) nURL.openConnection();

				if (conn != null) {
					conn.setConnectTimeout(10000);
					conn.setUseCaches(false);
					// 로그인 후의 쿠키값을 가져와서 세팅(안하면 로그인 인식 못함)
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
			// 시간표 html 파싱 시작
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
						// 로그인 후의 쿠키값을 가져와서 세팅(안하면 로그인 인식 못함)
						conn.setRequestProperty("Cookie", "JSESSIONID=" + JSESSIONID);
						conn.getRequestProperties();

						if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
							BufferedReader br = new BufferedReader(
									new InputStreamReader(conn.getInputStream(), "euc-kr"));

							String html = "";
							while ((line = br.readLine()) != null) {
								// 각 학과에대한 시간표 html소스 그대로 출력
								// System.out.println(line);
								html = html + line;
							}
							br.close();

							// String type 변수 html을 document형에 넣어서 파싱
							Document doc = Jsoup.parse(html, "", Parser.xmlParser());

							// 표의 제목 "요일" 을 제외하고 실제 요일값
							Elements week = doc.select("tr").not("tr:eq(0)").select("td:eq(9)");
							// 표의 제목 "교시" 을 제외하고 실제 교시값
							Elements period = doc.select("tr").not("tr:eq(0)").select("td:eq(10)");
							// 표의 제목 "강의실" 을 제외하고 실제 강의실값
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
			// 시간표 파싱완료
			System.out.println("http parsing complete");

			// 기존 시간표테이블 db 전체 삭제
			System.out.println("db delete start...");
			st.executeUpdate("delete * from Siganpyo");
			System.out.println("db delete complete");

			// 파싱한 결과를 db에 다시 삽입
			System.out.println("db re-insert start... ");
			ArrayList<String> weeks = si.getWeekArrlist(); // 요일
			ArrayList<String> periods = si.getPeriodArrlist();// 교시
			ArrayList<String> classrooms = si.getClassroomArrlist();// 강의실
			ArrayList<String> timeslots = si.getTimeslotArrlist();// 강의실

			for (int i = 0; i < si.getSize(); i++) {
				String week = weeks.get(i);
				int period = Integer.parseInt(periods.get(i));
				String[] strarr = classrooms.get(i).split("관");
				String building = strarr[0] + "관";
				String classroom = strarr[1];
				String timeslot = timeslots.get(i);

				// DB에 파싱 결과 저장
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

			// 빈강의실 db에 저장 Empty_Siganpyo(Building, Classroom, Week, Time)
			Total_siganpyo ts = new Total_siganpyo();

			// 기존 db 삭제
			st.executeUpdate("delete * from Empty_Siganpyo");
			// 시간표 db를 가져옴
			rs = st.executeQuery("select Week, Period, Building, Classroom, Timeslot from Siganpyo");

			try {
				for (; rs.next();) {

					String week = rs.getString(1);
					String period = rs.getString(2);
					String building = rs.getString(3);
					String classroom = rs.getString(4);
					String timeslot = rs.getString(5);

					// 전체 강의에 대한 강의정보 세팀
					ts.addCbuilding(building, classroom, period, timeslot, week);

				}
				// 최종결과 출력
				ts.printSiganpyo();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			int record = 0;
			// 빌딩 횟수만큼
			for (int building = 0; building < ts.getBuildingSize(); building++) {
				String buildingName = ts.getCbuilding(building).getBuildingName();

				// 각 빌딩 강의실 갯수 만큼
				for (int classroom = 0; classroom < ts.getCbuilding(building).getClassroomSize(); classroom++) {
					String classroomName = ts.getCbuilding(building).getClassroom(classroom).getClassroomName();

					// 각 강의실 월~토
					for (int week = 0; week < ts.getCbuilding(building).getClassroom(classroom).getWeekSize(); week++) {
						String weekName = ts.getCbuilding(building).getClassroom(classroom).getWeekString(week);

						// 각 요일 0~15교시
						for (int time = 0; time < ts.getCbuilding(building).getClassroom(classroom)
								.getTimesSize(); time++) {

							String timeName = ts.getCbuilding(building).getClassroom(classroom).getTimes(week, time);
								//강의가 없는 것만 골라서 db에 입력 == 빈강의실
								if(timeName == null){
								st.executeUpdate("insert into Empty_siganpyo(Building, Classroom, Week, Time,Record) values('"
										+ buildingName + "','" + classroomName + "','" + weekName + "','" + time
										+ "'," + (record++) + ")");
								}
						} // 0~15교시 끝

					} // 월~토 끝

				} // 강의실갯수 끝

			} // 빌딩 끝

			/* 결과출력하는 jsp로 넘어감 */
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
