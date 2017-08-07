package DB;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionDAO {
	ConnectionMaker connMaker ;

	//XML로부터 주입을 받는 생성자 함수
	public ConnectionDAO (ConnectionMaker connMaker) {
		this.connMaker = connMaker ;
	}
	public Connection getConn() throws ClassNotFoundException, SQLException{
		return connMaker.getConnection();
	}
}