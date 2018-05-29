package dao;
 
import java.sql.*;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
 
public class ConnectionManager {
 
    // URL・ユーザ名・パスワードの設定
    private final static String URL = "jdbc:mysql://localhost:3306/test_db?useSSL=false";
    private final static String USER = "administrator";
    private final static String PASSWORD = "2335Cube!";
    // コネクションオブジェクト
    private Connection connection = null;
 
     // このクラスに唯一のインスタンス
    private static ConnectionManager instance = new ConnectionManager();
 
    /*
     * static初期化子
     */
    static {
        // JDBCドライバのロード
        String drv = "com.mysql.jdbc.Driver";
        try {
        	System.out.println(drv);
//            Class.forName(drv);
        	System.out.println("newInstance");
//        	Class.forName("com.mysql.jdbc.Driver").newInstance(); 
        	Class.forName(drv).newInstance();
        } catch (Exception e) {
        	System.out.println("Oh my GOD!");
        	System.out.println(drv);
            System.out.println("ドライバがありません" + e.getMessage());
        }
    }
 
    /**
     * コンストラクタ
     */
    private ConnectionManager() {   }
    /*
     * インスタンス取得メソッド
     */
    public static ConnectionManager getInstance() { return instance; }
 
    /**
     * DBの接続     *
     * @return コネクション
     * @throws Exception
     */
    public synchronized Connection getConnection() throws DAOException {
        //  コネクションの確立
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            connection = null;
            throw new DAOException("[conect]異常", e);
        }
        return connection;
    }
 
    /**
     * DBの切断
     */
    public void closeConnection() throws DAOException{
        try {
            if (connection != null) {   connection.close(); }
        } catch (SQLException e) {
            throw new DAOException("[closeConnection]異常", e);
        } finally {
            connection = null;
        }
    }
}