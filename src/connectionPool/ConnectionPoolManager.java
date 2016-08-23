package connectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;

public class ConnectionPoolManager {
	
	public Hashtable<String,IConnectionPool> pools = new Hashtable<String,IConnectionPool>();
	private ConnectionPoolManager(){
		init();
	}
	//单例的获取
	public static ConnectionPoolManager getInstance(){
		return Singtonle.instance;
	}
	private static class Singtonle{
		private static ConnectionPoolManager instance = new ConnectionPoolManager();
	}
	
	public void  init(){
		for(int i = 0;i<DbInfo.beans.size();i++){
			DbBean bean = DbInfo.beans.get(i);
			ConnectionPool pool = new ConnectionPool(bean);
			if(pool !=null){
				pools.put(bean.getPoolName(),pool);
				System.out.println("Info:init connection succeed->"+bean.getPoolName());
			}
		}
	}
	public Connection getConnection(String poolName){
		Connection conn = null;
		if(pools.size()>0&&pools.containsKey(poolName)){
			conn = getPool(poolName).getConnection();
		} else {
			System.out.println("error:can't find this connection pool->"+poolName);
		}
		return conn;
	}
	   public void close(String poolName,Connection conn){  
           IConnectionPool pool = getPool(poolName);  
           try {  
               if(pool != null){  
                   pool.releaseConnection(conn);  
               }  
           } catch (SQLException e) {  
               System.out.println("连接池已经销毁");  
               e.printStackTrace();  
           }  
   }  
     
   // 清空连接池  
   public void destroy(String poolName){  
       IConnectionPool pool = getPool(poolName);  
       if(pool != null){  
           pool.destroy();  
       }  
   }  
	public IConnectionPool getPool(String poolName){
		IConnectionPool pool = null;
		if(pools.size()>0){
			pool = pools.get(poolName);
		}
		return pool; 
	}
}