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

		/* factory ����, xml������ ������� �����Ѵ� */
		BeanFactory factory = new XmlBeanFactory(
				new FileSystemResource("C:/Users/Administrator/workspace/CapstoneDesign/WebContent/db.xml"));

		/* DBĿ�ؼ� ���� */
		ConnectionDAO connMaker = factory.getBean("connMaker", ConnectionDAO.class);
		Connection con = connMaker.getConn();
		;
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
			HttpResponse response = httpclient.execute(httppost);

			// Response ���� ��Ű �� (response���� ��� ������ �Ҵ� (���� �������� ����))
			int a, b;
			a = response.toString().indexOf("JSESSIONID=") + "JSESSIONID=".length();
			b = response.toString().substring(a).indexOf(";");

			// ��Ⱚ
			cookie = response.toString().substring(a).substring(0, b);

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
						BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "euc-kr"));

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
		System.out.println("http parsing complete");
		
		
		System.out.println("db delete start...");
		// ���� �ð�ǥ db ��ü ����
		st.executeUpdate("delete * from Siganpyo");
		System.out.println("db delete complete");

		
		System.out.println("db re-insert start... ");
		ArrayList<String> weeks = si.getWeekArrlist(); // ����
		ArrayList<String> periods = si.getPeriodArrlist();// ����
		ArrayList<String> classrooms = si.getClassroomArrlist();// ���ǽ�
		ArrayList<String> timeslots = si.getTimeslotArrlist();// ���ǽ�

		// �ٽ� db�� �ð�ǥ ����
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