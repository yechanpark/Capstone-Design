package Tests;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

import DB.Article;
import DB.ClassBuilding;
import DB.ConnectionDAO;
import DB.InClass_Total_siganpyo;
import DB.Siganpyo_info;
import DB.Total_siganpyo;
import Notification.BusNotification;
import Notification.Notification;

public class testParse {
	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
		Notification n = new BusNotification();
		
		
	}
}