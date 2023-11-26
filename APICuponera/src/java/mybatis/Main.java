package mybatis;

import org.apache.ibatis.session.SqlSession;

public class Main {
    public static void main(String[] args) {
        SqlSession session = MyBatisUtil.getSession();
        
        // Aquí puedes realizar operaciones en la base de datos utilizando la sesión
        
        // No olvides cerrar la sesión cuando hayas terminado
        session.close();
    }
}

