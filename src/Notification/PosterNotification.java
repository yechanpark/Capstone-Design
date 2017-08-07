package Notification;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PosterNotification extends Notification{

	public PosterNotification() throws SQLException, UnknownHostException, ClassNotFoundException{
		connect();
		
		rs = st.executeQuery("select filename from test where Type='Poster'"); // Äõ¸®¹®
		InetAddress loacl = InetAddress.getLocalHost();
		String ip = loacl.getHostAddress();
	
		JSONObject job_image_url = new JSONObject();
		for(;rs.next();){		
			String image_URL = "http://" + ip + "/CapstoneDesign/Images/" + rs.getString(1);
			job_image_url.put("Poster", image_URL);
		}
		jsonMain.put("Poster", job_image_url);
		jsonMain.put("Type", "Poster");
		close();
	}
	
	
	@Override
	public JSONObject getJSON() {
		
		return jsonMain;
	}

}
