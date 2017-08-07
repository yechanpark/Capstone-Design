package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DConnectionMaker implements ConnectionMaker {
	private String url;
	private String id;
	private String pwd;
	
	//XML로부터 주입을 받는 생성자 함수
	public DConnectionMaker(String url, String id, String pwd){
		this.url = url;
		this.id = id;
		this.pwd = pwd;
	}
	public Connection getConnection() throws ClassNotFoundException, SQLException 
	{
		Class.forName("com.mysql.jdbc.Driver");
		//db.xml파일에서지정
		Connection con = DriverManager.getConnection(url, id, pwd);		
		return con ;
	}

}