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
	int service_count = 2; // 2���� ���� ����
	Calendar oCalendar = Calendar.getInstance();

	public Parse_Parte(String url) throws IOException {

		doc = Jsoup.connect(url).get();
		doParsetoJSON();

	}

	private void doParsetoJSON() {
		// row���� ������
		// �߽�
		Elements table_row1 = doc.select("table[class=table-b table-b-menu] tbody tr:eq(0)")
				.not("tr[style=display:none;]").select("th[scope=row]");
		// ����
		Elements table_row2 = doc.select("table[class=table-b table-b-menu] tbody tr:eq(2)")
				.not("tr[style=display:none;]").select("th[scope=row]");

		// �޴��� ������(5�� tr)
		Elements table_menu = doc.select("table[class=table-b table-b-menu] tbody tr").not("tr[style=display:none;]")
				.select("td").not("td[style=display:none;]");

		// ��¥ ������(5�� th)
		Elements table_date = doc.select("table[class=table-b table-b-menu] thead th").not("th[style=display:none;]");

		/* row �Է� */
		// �߽�
		for (Element e : table_row1) {
			String row = e.text().replace("\u00a0", "");
			JSONObject jObject = new JSONObject();
			jObject.put("row", row);
			jArray_rows.add(jObject);
			jsonMain.put("rows", jArray_rows);

		}
		
		// ����
		for (Element e : table_row2) {
			String row2 = e.text().replace("\u00a0", "");
			JSONObject jObject = new JSONObject();
			jObject.put("row", row2);
			jArray_rows.add(jObject);
			jsonMain.put("rows", jArray_rows);

		}
		
		/* ��¥ �Է� */
		for (Element e : table_date) {
			String date = e.text().replace("\u00a0", "");
			if (!date.equals("")) {
				JSONObject jObject = new JSONObject();
				jObject.put("date", date);
				jArray_dates.add(jObject);
				jsonMain.put("dates", jArray_dates);
			}
		}

		/*���� �޴� ���� �Է� - �����ҽ� {"menu":"1���޴� 2���޴� 3���޴�"}*/
		for (Element e : table_menu) {
			String menu = e.text().replace("\u00a0", "");
			if (!menu.equals("")) {
				JSONObject jObject = new JSONObject();
				jObject.put("menu", menu);
				jArray_menus.add(jObject);
				jsonMain.put("menus", jArray_menus);
			}
		}
		 /*���� �޴� ���� �Է� - ����ҽ� {"menu":"1���޴�"},{"menu":"2���޴�"},{"menu":"3���޴�"}
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
