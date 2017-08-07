package Parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import DB.ParteInfo;

public class Parse_Parte extends Parse {

	JSONArray jArray_rows = new JSONArray();
	JSONArray jArray_menus = new JSONArray();
	JSONArray jArray_dates = new JSONArray();
	ArrayList<ParteInfo> pis = new ArrayList<ParteInfo>();
	int service_count = 2; // 2개의 정보 서비스
	Calendar oCalendar = Calendar.getInstance();

	public Parse_Parte(String url) throws IOException {

		doc = Jsoup.connect(url).get();
		doParsetoJSON();

	}

	private void doParsetoJSON() {
		// row값들 가져옴
		// 중식
		Elements table_row1 = doc.select("table[class=table-b table-b-menu] tbody tr:eq(0)")
				.not("tr[style=display:none;]").select("th[scope=row]");
		// 석식
		Elements table_row2 = doc.select("table[class=table-b table-b-menu] tbody tr:eq(2)")
				.not("tr[style=display:none;]").select("th[scope=row]");

		// 메뉴값 가져옴(5개 tr)
		Elements table_menu = doc.select("table[class=table-b table-b-menu] tbody tr").not("tr[style=display:none;]")
				.select("td").not("td[style=display:none;]");

		// 날짜 가져옴(5개 th)
		Elements table_date = doc.select("table[class=table-b table-b-menu] thead th").not("th[style=display:none;]");

		/* row 입력 */
		// 중식
		for (Element e : table_row1) {
			String row = e.text().replace("\u00a0", "");
			JSONObject jObject = new JSONObject();
			jObject.put("row", row);
			jArray_rows.add(jObject);
			jsonMain.put("rows", jArray_rows);

		}
		
		// 석식
		for (Element e : table_row2) {
			String row2 = e.text().replace("\u00a0", "");
			JSONObject jObject = new JSONObject();
			jObject.put("row", row2);
			jArray_rows.add(jObject);
			jsonMain.put("rows", jArray_rows);

		}
		
		/* 날짜 입력 */
		for (Element e : table_date) {
			String date = e.text().replace("\u00a0", "");
			if (!date.equals("")) {
				JSONObject jObject = new JSONObject();
				jObject.put("date", date);
				jArray_dates.add(jObject);
				jsonMain.put("dates", jArray_dates);
			}
		}

		/*실제 메뉴 내용 입력 - 기존소스 {"menu":"1번메뉴 2번메뉴 3번메뉴"}*/
		for (Element e : table_menu) {
			String menu = e.text().replace("\u00a0", "");
			if (!menu.equals("")) {
				JSONObject jObject = new JSONObject();
				jObject.put("menu", menu);
				jArray_menus.add(jObject);
				jsonMain.put("menus", jArray_menus);
			}
		}
		 /*실제 메뉴 내용 입력 - 변경소스 {"menu":"1번메뉴"},{"menu":"2번메뉴"},{"menu":"3번메뉴"}
		for (Element e : table_menu) {
			String menus = 
			String menu = e.text().replace("\u00a0", "");
			if (!menu.equals("")) {
				JSONObject jObject = new JSONObject();
				jObject.put("menu", menu);
				jArray_menus.add(jObject);
				jsonMain.put("menus", jArray_menus);
			}
		}*/
		
	}

	@Override
	public JSONObject getJSON() {
		return jsonMain;
	}

}
