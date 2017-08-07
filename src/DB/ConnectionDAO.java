package DB;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionDAO {
	ConnectionMaker connMaker ;

	//XML�κ��� ������ �޴� ������ �Լ�
	public ConnectionDAO (ConnectionMaker connMaker) {
		this.connMaker = connMaker ;
	}
	public Connection getConn() throws ClassNotFoundException, SQLException{
		return connMaker.getConnection();
	}
}