package mybatis;

import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {
    
    public static final String RESOURCES = "mybatis/mybatis-config.xml";
    public static final String ENVIRONMENT = "development";
    
    public static SqlSession getSession(){
        SqlSession session = null;
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
