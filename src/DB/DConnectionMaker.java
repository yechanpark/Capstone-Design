package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DConnectionMaker implements ConnectionMaker {
	private String url;
	private String id;
	private String pwd;
	
	//XML�κ��� ������ �޴� ������ �Լ�
	public DConnectionMaker(String url, String id, String pwd){
		this.url = url;
		this.id = id;
		this.pwd = pwd;
	}
	public Connection getConnection() throws ClassNotFoundException, SQLException 
	{
		Class.forName("com.mysql.jdbc.Driver");
		//db.xml���Ͽ�������
		Connection con = DriverManager.getConnection(url, id, pwd);		
		return con ;
	}

}