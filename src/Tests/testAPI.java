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

		/* factory ����, xml������ ������� �����Ѵ� */
		BeanFactory factory = new XmlBeanFactory(
				new FileSystemResource("C:/Users/Administrator/workspace/CapstoneDesign/WebContent/db.xml"));
		Path p = factory.getBean("serviceKey", Path.class);
		String serviceKey = p.getPath();

		// openAPI - Ư�� �뼱(busRouteId == 107900003 == ����02)�� ��ü ���� ���� ���� ���� ��ȸ
		Document doc = Jsoup.connect("http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll?serviceKey=" + serviceKey
				+ "&busRouteId=107900003").get();

		ArrayList<BusInfo> bi = new ArrayList<BusInfo>();

		// ���������� ���� ID
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
			System.out.println("�����Ҹ�:"+bi.get(i).getStationName());
			//System.out.println("ù��°�������� ��ȣ :"+bi.get(i).getArrBus1Number());
			System.out.println("ù��°���� ���������ð�:"+bi.get(i).getArrmsg1());
			//System.out.println("�ι�°�������� ��ȣ :"+bi.get(i).getArrBus2Number());
			//System.out.println("�ι�°���� ���������ð�:"+bi.get(i).getArrmsg2());
			//System.out.println("���Žð�:"+bi.get(i).getMakeTm());;
			System.out.println("������ ����:"+bi.get(i).getStationOrd());
			System.out.println("--------------------");
		}
		
	}
}
