package Notification;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParteNotification extends Notification {

	public ParteNotification() throws SQLException, ClassNotFoundException, IOException {
		connect();
		rs = st.executeQuery("select URL from test where Type='Parte'");

		Calendar cal = Calendar.getInstance();
		JSONObject job = new JSONObject();
		
		JSONArray jrr_menu = new JSONArray();
		for (; rs.next();) {
			String url = rs.getString(1);

			doc = Jsoup.connect(url).get();
			
			// 날짜 가져옴(5개 th)
			Elements table_date = doc.select("table[class=table-b table-b-menu] thead th")
					.not("th[style=display:none;]");
			

			
			String month = "";
			String day = "";
			int day_count=1;
			for (Element e : table_date) {
				if (e.text().replace("\u00a0", "").length() != 0) {
					day_count++;
					String str = e.text().replace("\u00a0", "");
					String[] sub_front = str.split("월");
					
					if (Integer.parseInt(sub_front[0]) == (cal.get(Calendar.MONTH) + 1)) 
						month = sub_front[0];
					
					String[] sub_back = sub_front[1].split(" ");
					String[] date = sub_back[1].split("일");
					
					if (Integer.parseInt(date[0]) == cal.get(Calendar.DAY_OF_MONTH)){
						day = date[0];
						break;
					}
				}
			}
			
			// 메뉴값 가져옴(5개 tr)
			Elements table_menu = doc.select("table[class=table-b table-b-menu] tbody tr").not("tr[style=display:none;]")
					.select("td:eq("+day_count+")").not("td[style=display:none;]");
			
			for (Element e : table_menu) {
				String menu = e.text().replace("\u00a0", "");
				if(menu.length()!=0){
					JSONObject job_menu = new JSONObject();
					job_menu.put("menu", menu);
					jrr_menu.add(job_menu);
					
				}
			}
		}
		jsonMain.put("Type", "Parte");
		jsonMain.put("Parte", jrr_menu);
		close();
	}

	@Override
	public JSONObject getJSON() {
		// TODO Auto-generated method stub
		return jsonMain;
	}

}
