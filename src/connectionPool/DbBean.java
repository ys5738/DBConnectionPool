package connectionPool;

public class DbBean {
	private String driverName;
	private String url;
	private String userName;
	private String password;
	private String poolName;
	private int minConnection = 1;//空闲吃最小连接数
	private int maxConnection = 10;//空闲池最大连接数
	private int initConnections = 5;//初始化连接数
	private long connTimeOut = 1000;//连接频率
	private int maxActiveConnections = 100;//最大允许的连接数，和数据库对应，数据库在哪里设置？
	private long connectionTimeOut = 1000*60*20;//连接超时20min
	private boolean isCheckPool = true;//是否定时检查连接池
	private long lazyCheck = 1000*60*60;//延迟多少时间后开始 检查
	private long periodCheck = 1000*60*60;// 检查频率
	
	public DbBean(String driverName,String url,String userName,
			String password,String poolName){
		super();
		this.driverName = driverName;
		this.url = url;
		this.userName = userName;
		this.password = password;
		this.poolName = poolName;
	}
	public DbBean(){}
	
	//setter and getter
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPoolName() {
		return poolName;
	}
	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}
	public int getMinConnection() {
		return minConnection;
	}
	public void setMinConnection(int minConnections) {
		this.minConnection = minConnections;
	}
	public int getMaxConnection() {
		return maxConnection;
	}
	public void setMaxConnection(int maxConnection) {
		this.maxConnection = maxConnection;
	}
	public int getInitConnections() {
		return initConnections;
	}
	public void setInitConnections(int initConnections) {
		this.initConnections = initConnections;
	}
	public long getConnTimeOut() {
		return connTimeOut;
	}
	public void setConnTimeOut(long connTimeOut) {
		this.connTimeOut = connTimeOut;
	}
	public int getMaxActiveConnections() {
		return maxActiveConnections;
	}
	public void setMaxActiveConnections(int maxActiveConnections) {
		this.maxActiveConnections = maxActiveConnections;
	}
	public long getConnectionTimeOut() {
		return connectionTimeOut;
	}
	public void setConnectionTimeOut(long connectionTimeOut) {
		this.connectionTimeOut = connectionTimeOut;
	}
	public boolean isCheckPool() {
		return isCheckPool;
	}
	public void setCheckPool(boolean isCheckPool) {
		this.isCheckPool = isCheckPool;
	}
	public long getLazyCheck() {
		return lazyCheck;
	}
	public void setLazyCheck(long lazyCheck) {
		this.lazyCheck = lazyCheck;
	}
	public long getPeriodCheck() {
		return periodCheck;
	}
	public void setPeriodCheck(long periodCheck) {
		this.periodCheck = periodCheck;
	}
}
