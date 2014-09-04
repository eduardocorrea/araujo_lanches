package banco;

import java.sql.*;

/**
 *
 * @author eduardo
 */
public class Conexao {  
    private Connection connection;
    public Conexao() throws ClassNotFoundException, SQLException{
       Class.forName("com.mysql.jdbc.Driver");
       connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/araujo_lanches", "root","adminadmin");
    }
    
    public static void main() {
       try{
           new Conexao();
           
       } catch(Exception e){
           System.out.println(e);
           System.out.println("Nao Conectado");
       }
    }

    /**
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * @param connection the connection to set
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
}  