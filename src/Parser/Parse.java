package Parser;

import org.json.simple.JSONObject;
import org.jsoup.nodes.Document;


public abstract class Parse {
	Document doc;
	JSONObject jsonMain = new JSONObject();
	public abstract JSONObject getJSON();
	
}
