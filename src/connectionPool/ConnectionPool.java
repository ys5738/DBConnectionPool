package connectionPool;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class ConnectionPool implements IConnectionPool{
    private DbBean dbBean;  
    private boolean isActive = false; // 连接池活动状态  
    private int countActive = 0;// 记录创建的总的连接数  
    
	private List <Connection> freeConnection = new Vector<Connection>();  
	private List<Connection> activeConnection = new Vector<Connection>();
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

	public ConnectionPool(DbBean bean){
		super();
		this.dbBean = bean;
		init();
		checkPool();
	}
	public void init() {  
	        try {  
	            Class.forName(dbBean.getDriverName());  
	            for (int i = 0; i < dbBean.getInitConnections(); i++) {  
	                Connection conn;  
	                conn = newConnection();  
	                // 初始化最小连接数  
	                if (conn != null) {  
	                    freeConnection.add(conn);  
	                    countActive++;  
	                }  
	            }  
	            isActive = true;  
	        } catch (ClassNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        }  
	}
	
	@Override
	public Connection getCurrentConnection(){
		Connection conn = threadLocal.get();
		if(!isValid(conn)){
			conn = this.getConnection();
		}
		return conn;
	}
	
	private boolean isValid(Connection conn) {
		try {
			if(conn == null || conn.isClosed()){
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public Connection newConnection() throws ClassNotFoundException, SQLException{
		Connection conn = null;
		if(dbBean != null){
			Class.forName(dbBean.getDriverName());
			conn = DriverManager.getConnection(dbBean.getUrl(),dbBean.getUserName(),dbBean.getPassword());
		}
		return conn;
	}
	
	@Override
	public synchronized Connection getConnection() {
		Connection conn = null;
		try{
			if(countActive<this.dbBean.getMaxActiveConnections()){
				if(freeConnection.size()>0){
					conn = freeConnection.get(0);
					freeConnection.remove(0);
					if(conn!=null){
						this.threadLocal.set(conn);
					}
				} else {
					conn = newConnection();
				}
			} else {
				wait(dbBean.getConnTimeOut());
				getConnection();
			}
		}catch (SQLException e) {  
            e.printStackTrace();  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
		return conn;
	}


	@Override
	public synchronized void releaseConnection(Connection conn) throws SQLException {
		if(isValid(conn)&&!(freeConnection.size()>dbBean.getMaxConnection())){
			freeConnection.add(conn);
			activeConnection.remove(conn);
			countActive--;
			this.threadLocal.remove();
			notifyAll();
		}
	}

	@Override
	public void destroy() {
		for(Connection conn : freeConnection){
			if(isValid(conn)){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		for(Connection conn : activeConnection){
			if(isValid(conn)){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		countActive = 0;
		isActive = false;
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public void checkPool() {
		   if(dbBean.isCheckPool()){  
	            new Timer().schedule(new TimerTask() {  
	            @Override  
	            public void run() {  
	            // 1.对线程里面的连接状态  
	            // 2.连接池最小 最大连接数  
	            // 3.其他状态进行检查，因为这里还需要写几个线程管理的类，暂时就不添加了  
	            System.out.println("空线池连接数："+freeConnection.size());  
	            System.out.println("活动连接数：："+activeConnection.size());  
	            System.out.println("总的连接数："+countActive);  
	                }  
	            },dbBean.getLazyCheck(),dbBean.getPeriodCheck());  
	        }  
	}
	
}
