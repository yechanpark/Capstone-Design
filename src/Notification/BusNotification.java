package Notification;
import java.io.IOException;
import java.sql.SQLException;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

import DB.Path;
import Notification.Notification;

public class BusNotification extends Notification {

	public BusNotification() throws IOException, ClassNotFoundException, SQLException {
		/* factory 정의, xml파일의 빈네임을 참조한다 */
		BeanFactory factory = new XmlBeanFactory(
				new FileSystemResource("C:/Users/Administrator/workspace/CapstoneDesign/WebContent/db.xml"));
		Path p = factory.getBean("serviceKey", Path.class);
		String serviceKey = p.getPath();

		// openAPI - 특정 노선(busRouteId == 107900003 == 성북02)의 전체 버스 도착 예정 정보 조회
		Document doc = Jsoup.connect("http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll?serviceKey=" + serviceKey
				+ "&busRouteId=107900003").get();
		
		Elements arrMsg1 = doc.select("arrmsg1");
		

		int count=0;
		for (Element e : arrMsg1) {
			count++;
			//한양슈퍼
			if(count==35){
				jsonMain.put("Type", "bus");
				jsonMain.put("bus", e.text());
			}
		}
		
	}

	@Override
	public JSONObject getJSON() {
		// TODO Auto-generated method stub
		return jsonMain;
	}

}
