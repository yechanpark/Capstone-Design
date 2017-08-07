package Parser;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.apache.http.client.methods.HttpPost;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

public class Parse_SchoolBus extends Parse {
	JSONArray jArray_seats = new JSONArray();

	public Parse_SchoolBus(String url) {
		System.out.println(getinput(url));
	}
	public String getinput(String url) {
		try{
			URL reqUrl = new URL(url);
			HttpsURLConnection urlConn = (HttpsURLConnection) reqUrl.openConnection();
			urlConn.setRequestMethod("POST");
			urlConn.setRequestProperty("Accept", "*/*");
			
			int resCode = urlConn.getResponseCode();
			System.out.println("resCode :" + resCode);
			//if(resCode != HttpsURLConnection.HTTP_OK)
			//	return null;
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			String input;
			StringBuffer sb = new StringBuffer();
			
			while((input = reader.readLine())!=null){
				sb.append(input);
			}
			return sb.toString();
		}catch(IOException e){
			e.printStackTrace();
			
		}

		// doc = Jsoup.connect(url).get();
		//doParsetoJSON();
		return null;

	}

	private void doParsetoJSON() {
		
		// Elements buss = doc.select("");
		//System.out.println(doc.toString());

		/* 자리정보 json에 입력 */
		// for (Element e : buss) {

		// System.out.println(e.text().replace("\u00a0", ""));
		/*
		 * JSONObject jObject = new JSONObject(); jObject.put("chairNo",
		 * e.text().replace("\u00a0", "")); jObject.put("isSeat",
		 * e.attr("bgcolor")); jArray_seats.add(jObject); jsonMain.put("seats",
		 * jArray_seats);
		 */
		// }

	}

	@Override
	public JSONObject getJSON() {
		// TODO Auto-generated method stub
		return jsonMain;
	}

}
