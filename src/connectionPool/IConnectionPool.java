package connectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionPool {
	public Connection getConnection();
	public Connection getCurrentConnection();
	public void releaseConnection(Connection conn) throws SQLException;
	public void destroy();
	public boolean isActive();
	public void checkPool();
}
