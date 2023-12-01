package mybatis;

import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {

    public static final String RESOURCE = "mybatis/mybatis-config.xml";

    
    public static final String RESOURCES = "mybatis/mybatis-config.xml";
  
    public static final String ENVIRONMENT = "development";
    
    public static SqlSession getSession(){
        SqlSession session = null;

        try {
            Reader reader = Resources.getResourceAsReader(RESOURCE);
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader, ENVIRONMENT);  //Patrones de diseños
            session = sqlMapper.openSession();
            
        }catch (IOException e){ 
            e.printStackTrace();
        }
        return session;
    }   

    public static Object getSqlSessionFactory() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}    


        try{
          Reader reader = Resources.getResourceAsReader(RESOURCES);
          SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader, ENVIRONMENT);
          session = sqlMapper.openSession();
        }catch (IOException e){
            e.printStackTrace();
        };
        return session;
    }
    
    
}

