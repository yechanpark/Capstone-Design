package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NConnectionMaker implements ConnectionMaker {
	private String url;
	
	//XML로부터 주입을 받는 생성자 함수
	public NConnectionMaker(String url){
		this.url = url;
	}
	public Connection getConnection() throws ClassNotFoundException, SQLException 
	{
		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver"); 
		Connection con = DriverManager.getConnection(url) ;
		return con ;
	}

}
