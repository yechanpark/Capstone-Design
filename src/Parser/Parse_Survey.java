package Parser;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

public class Parse_Survey extends Parse{
	
	public Parse_Survey(String url, String writer) throws IOException {
		doc = Jsoup.connect(url).get();	
		
		doParsetoJSON(url, writer);

	}

	private void doParsetoJSON(String url, String writer) {
		//JSONObject job_title = new JSONObject();
		
		Elements e = doc.select("meta[property=og:title]");
		String title = e.attr("content");
		String rewardType=null;
		String reward=null;
		
		jsonMain.put("Title", title);
		jsonMain.put("RewardType", rewardType);
		jsonMain.put("Reward", reward);
		jsonMain.put("Writer", writer);
		jsonMain.put("URL", url);
		
	}

	@Override
	public JSONObject getJSON() {
		
		return jsonMain;
	}
	
}
