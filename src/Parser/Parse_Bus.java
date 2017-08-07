package Parser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

import DB.BusInfo;
import DB.ClassBuilding;
import DB.ConnectionDAO;
import DB.Path;
import DB.Siganpyo_info;
import DB.Total_siganpyo;

public class Parse_Bus extends Parse {
	
	ArrayList<BusInfo> bi = new ArrayList<BusInfo>();
	
	public Parse_Bus() throws IOException, ClassNotFoundException, SQLException {
		
		/* factory 정의, xml파일의 빈네임을 참조한다 */
		BeanFactory factory = new XmlBeanFactory(
				new FileSystemResource("C:/Users/Administrator/workspace/CapstoneDesign/WebContent/db.xml"));
		Path p = factory.getBean("serviceKey", Path.class);
		String serviceKey = p.getPath();

		// openAPI - 특정 노선(busRouteId == 107900003 == 성북02)의 전체 버스 도착 예정 정보 조회
		Document doc = Jsoup.connect("http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll?serviceKey=" + serviceKey
				+ "&busRouteId=107900003").get();

		// 버스정류장 고유 ID
		Elements stationNames = doc.select("stNm");
		Elements arrMsg1s = doc.select("arrmsg1");
		//Elements arrMsg2s = doc.select("arrmsg2");
		//Elements makeTimes = doc.select("mkTm");
		Elements stationOrds = doc.select("staOrd");
		//Elements arrBus1Numbers = doc.select("plainNo1");
		//Elements arrBus2Numbers = doc.select("plainNo2");
		Elements nextord1s = doc.select("nstnOrd1");
		
		for (int i=0; i<stationNames.size(); i++){
			BusInfo sub_bi = new BusInfo();
			Element stationName = stationNames.get(i);
			sub_bi.setStationName(stationName.text());
			
			Element arrMsg1 = arrMsg1s.get(i);
			String exe_arrMsg1 = arrMsg1.text();
			
			
			String[] result_arrMsg1 = new String[2];
			result_arrMsg1[0] = new String("");
			result_arrMsg1[1] = new String("");
			if(!(exe_arrMsg1.equals("곧 도착")))
				result_arrMsg1 = exe_arrMsg1.split("\\[");
			else
				result_arrMsg1[0] = exe_arrMsg1; 
			sub_bi.setArrmsg1(result_arrMsg1[0]);
			
			//Element arrMsg2 = arrMsg2s.get(i);
			//sub_bi.setArrmsg2(arrMsg2.text());
			
			/*Element makeTime = makeTimes.get(i);
			sub_bi.setMakeTm(makeTime.text());*/
		
			Element stationOrd = stationOrds.get(i);
			sub_bi.setStationOrd(stationOrd.text());
			
			/*Element arrBus1Number = arrBus1Numbers.get(i);
			sub_bi.setArrBus1Number(arrBus1Number.text());
			
			Element arrBus2Number = arrBus2Numbers.get(i);
			sub_bi.setArrBus2Number(arrBus2Number.text());*/
			
			Element nextorder = nextord1s.get(i);
			sub_bi.setNextord1(nextorder.text());
			
			//18번부터만 (상행선, 학교올라오는 길) 출력
			if(Integer.parseInt(sub_bi.getStationOrd())>=18)
				bi.add(sub_bi);
		}
		
		doParsetoJSON();
	}

	private void doParsetoJSON() {
		JSONArray jrr_busInfos = new JSONArray();
		for(int i=0; i<bi.size();i++){
			JSONObject job_busInfo = new JSONObject();
			job_busInfo.put("StationName", bi.get(i).getStationName());
			//job_busInfo.put("ArrBus1Number", bi.get(i).getArrBus1Number());
			//job_busInfo.put("ArrBus2Number", bi.get(i).getArrBus2Number());
			job_busInfo.put("ArrMsg1", bi.get(i).getArrmsg1());
			//job_busInfo.put("ArrMsg2", bi.get(i).getArrmsg2());
			job_busInfo.put("StationOrd", bi.get(i).getStationOrd());
			job_busInfo.put("nextord1", bi.get(i).getNextord1());
			jrr_busInfos.add(job_busInfo);
		}
		jsonMain.put("BusInfo", jrr_busInfos);
		//jsonMain.put("MakeTm", bi.get(0).getMakeTm());
	}
	

	@Override
	public JSONObject getJSON() {
		// TODO Auto-generated method stub
		return jsonMain;
	}

}
