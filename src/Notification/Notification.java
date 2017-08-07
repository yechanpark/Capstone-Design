package Notification;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONObject;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

import DB.ConnectionDAO;

public abstract class Notification {
	
	BeanFactory factory = new XmlBeanFactory(
			new FileSystemResource("C:/Users/Administrator/workspace/CapstoneDesign/WebContent/db.xml"));
	/* DBÄ¿³Ø¼Ç ¼³Á¤ */
	ConnectionDAO connMaker = factory.getBean("connMaker", ConnectionDAO.class);
	Connection con;
	Statement st;
	ResultSet rs;
	Document doc;
	
	
	public JSONObject jsonMain = new JSONObject();
	public void connect() throws ClassNotFoundException, SQLException{
		con = connMaker.getConn();
		st = con.createStatement();
		
	}
	public void close() throws SQLException{
		rs.close();
		st.close();
		con.close();
	}
	public abstract JSONObject getJSON();
}
