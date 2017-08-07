package Parser;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parse_Library extends Parse {

	JSONArray jArray_seats = new JSONArray();

	public Parse_Library(String url) throws IOException {

		doc = Jsoup.connect(url).get();
		doParsetoJSON();

	}

	private void doParsetoJSON() {

		Elements seats = doc.select("div[id=maptemp] td");

		/* 자리정보 json에 입력 */
		for (Element e : seats) {
			JSONObject jObject = new JSONObject();
			jObject.put("chairNo", e.text().replace("\u00a0", ""));
			jObject.put("isSeat", e.attr("bgcolor"));
			jArray_seats.add(jObject);
			
		}
		jsonMain.put("seats", jArray_seats);
	}

	@Override
	public JSONObject getJSON() {
		// TODO Auto-generated method stub
		return jsonMain;
	}

}
