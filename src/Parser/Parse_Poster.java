package Parser;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

import DB.ConnectionDAO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Parse_Poster extends Parse {

	ResultSet rs;
	public Parse_Poster(int record) throws IOException, ClassNotFoundException, SQLException {
		/* factory 정의, xml파일의 빈네임을 참조한다 */
		BeanFactory factory = new XmlBeanFactory(
				new FileSystemResource("C:/Users/Administrator/workspace/CapstoneDesign/WebContent/db.xml"));

		/* DB커넥션 설정 */
		ConnectionDAO connMaker = factory.getBean("connMaker", ConnectionDAO.class);
		Connection con = connMaker.getConn();
		Statement st = con.createStatement();
		rs = st.executeQuery("select filename, URL, Type from test where primary_key="+record); // 쿼리문

		doParsetoJSON();
	
		st.close();
		con.close();
		rs.close();
	}
	private void doParsetoJSON() throws SQLException, UnknownHostException {
		
		InetAddress loacl = InetAddress.getLocalHost();
		String ip = loacl.getHostAddress();
		
		rs.next();
		String image_URL = "http://" + ip + "/CapstoneDesign/Images/" + rs.getString(1);
		String URL = rs.getString(2);
		String Type = rs.getString(3);


		jsonMain.put("URL", URL);
		jsonMain.put("image_URL", image_URL);
	}
	
	@Override
	public JSONObject getJSON() {

		return this.jsonMain;
	}
}
