package Tests;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

import DB.BusInfo;
import DB.Path;

public class testAPI {
	public static void main(String[] args) throws IOException {

		/* factory 정의, xml파일의 빈네임을 참조한다 */
		BeanFactory factory = new XmlBeanFactory(
				new FileSystemResource("C:/Users/Administrator/workspace/CapstoneDesign/WebContent/db.xml"));
		Path p = factory.getBean("serviceKey", Path.class);
		String serviceKey = p.getPath();

		// openAPI - 특정 노선(busRouteId == 107900003 == 성북02)의 전체 버스 도착 예정 정보 조회
		Document doc = Jsoup.connect("http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll?serviceKey=" + serviceKey
				+ "&busRouteId=107900003").get();

		ArrayList<BusInfo> bi = new ArrayList<BusInfo>();

		// 버스정류장 고유 ID
		Elements stationNames = doc.select("stNm");
		Elements arrMsg1s = doc.select("arrmsg1");
		Elements arrMsg2s = doc.select("arrmsg2");
		Elements makeTimes = doc.select("mkTm");
		Elements stationOrds = doc.select("staOrd");
		//Elements arrBus1Numbers = doc.select("plainNo1");
		//Elements arrBus2Numbers = doc.select("plainNo2");

		for (int i=0; i<stationNames.size(); i++){
			BusInfo sub_bi = new BusInfo();
			Element stationName = stationNames.get(i);
			sub_bi.setStationName(stationName.text());
			
			Element arrMsg1 = arrMsg1s.get(i);
			sub_bi.setArrmsg1(arrMsg1.text());
			
			/*Element arrMsg2 = arrMsg2s.get(i);
			sub_bi.setArrmsg2(arrMsg2.text());
			
			Element makeTime = makeTimes.get(i);
			sub_bi.setMakeTm(makeTime.text());*/

			Element stationOrd = stationOrds.get(i);
			sub_bi.setStationOrd(stationOrd.text());
			
			/*Element arrBus1Number = arrBus1Numbers.get(i);
			sub_bi.setArrBus1Number(arrBus1Number.text());
			
			Element arrBus2Number = arrBus2Numbers.get(i);
			sub_bi.setArrBus2Number(arrBus2Number.text());
			*/
			bi.add(sub_bi);
		}
		
		for(int i=0; i<bi.size();i++){
			System.out.println("정류소명:"+bi.get(i).getStationName());
			//System.out.println("첫번째도착버스 번호 :"+bi.get(i).getArrBus1Number());
			System.out.println("첫번째버스 도착남은시간:"+bi.get(i).getArrmsg1());
			//System.out.println("두번째도착버스 번호 :"+bi.get(i).getArrBus2Number());
			//System.out.println("두번째버스 도착남은시간:"+bi.get(i).getArrmsg2());
			//System.out.println("갱신시간:"+bi.get(i).getMakeTm());;
			System.out.println("정류소 순번:"+bi.get(i).getStationOrd());
			System.out.println("--------------------");
		}
		
	}
}
