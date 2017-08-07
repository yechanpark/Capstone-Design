package Notification;

import java.io.IOException;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LibraryNotification extends Notification {

	public LibraryNotification() throws SQLException, ClassNotFoundException, IOException {
		connect();
		rs = st.executeQuery("select URL from test where Type='Library'");
		
		JSONArray jrr = new JSONArray();
		for (; rs.next();) {
			
			String url = rs.getString(1);
			doc = Jsoup.connect(url).get();
			
			Elements seats = doc.select("div[id=maptemp] td");
			Elements roomnames = doc.select("table tbody tr td[height=30]");
			String roomname = "";
			int count = 0;
			
			/* 자리정보 json에 입력 */
			JSONObject lib = new JSONObject();
			for (Element e : seats) {
				if (e.attr("bgcolor").equals("gray"))
					count++;
				lib.put("empty", count);
			}
			
			for (Element e : roomnames) {	
				roomname = e.text();
				lib.put("libname", roomname);
			}
			jrr.add(lib);
			
		}//for끝
		jsonMain.put("lib", jrr);
		jsonMain.put("Type", "lib");
		close();
	}

	@Override
	public JSONObject getJSON() {
		// TODO Auto-generated method stub
		return jsonMain;
	}

}
