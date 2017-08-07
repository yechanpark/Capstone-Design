package HTTP;

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

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

import DB.ConnectionDAO;
import DB.Siganpyo_info;

public class SET_Siganpyo {

	public static void main(String arg[]) throws IOException, ClassNotFoundException, SQLException {
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
		Connection con = connMaker.getConn();
		;
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
			HttpResponse response = httpclient.execute(httppost);

			// Response 에서 쿠키 값 (response에서 얻어 변수에 할당 (응답 본문에서 얻어옴))
			int a, b;
			a = response.toString().indexOf("JSESSIONID=") + "JSESSIONID=".length();
			b = response.toString().substring(a).indexOf(";");

			// 쿠기값
			cookie = response.toString().substring(a).substring(0, b);

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
						BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "euc-kr"));

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
		System.out.println("http parsing complete");
		
		
		System.out.println("db delete start...");
		// 기존 시간표 db 전체 삭제
		st.executeUpdate("delete * from Siganpyo");
		System.out.println("db delete complete");

		
		System.out.println("db re-insert start... ");
		ArrayList<String> weeks = si.getWeekArrlist(); // 요일
		ArrayList<String> periods = si.getPeriodArrlist();// 교시
		ArrayList<String> classrooms = si.getClassroomArrlist();// 강의실
		ArrayList<String> timeslots = si.getTimeslotArrlist();// 강의실

		// 다시 db에 시간표 삽입
		for (int i = 0; i < si.getSize(); i++) {
			String week = weeks.get(i);
			int period = Integer.parseInt(periods.get(i));
			String classroom = classrooms.get(i);
			String timeslot = timeslots.get(i);

			st.executeUpdate("insert into Siganpyo(Week, Period, Classroom, Timeslot, record) values('" + week + "',"
					+ period + ",'" + classroom + "','" + timeslot + "'," + i + ")");
			if((i+1)%100 == 0)
				System.out.println((i+1)+"/"+(si.getSize()+1)+"done");
		}

		System.out.println("db re-insert complete");
		System.out.println("All-Jobs done");
		con.close();
		rs.close();
	}
}