package connectionPool;

import java.util.ArrayList;
import java.util.List;

public class DbInfo {
    public  static List<DbBean>  beans = null;  
    static{  
        beans = new ArrayList<DbBean>();  

        DbBean beanMysql = new DbBean();  
        beanMysql.setDriverName("com.mysql.jdbc.Driver");  
        beanMysql.setUrl("jdbc:mysql://localhost:3306/test");  
        beanMysql.setUserName("root");  
        beanMysql.setPassword("");  
          
        beanMysql.setMinConnection(5);  
        beanMysql.setMaxConnection(100);  
          
        beanMysql.setPoolName("testPool");  
        beans.add(beanMysql);  
    }  
}
