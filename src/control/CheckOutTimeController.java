package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CheckOutTimeController {
    private String SQL_username;
    private String SQL_password;

    private Connection connection;

    @FXML
    private TextField id;
    @FXML
    private Button ok;
    @FXML
    private Button quit;

    //获取用户名和密码
    public void getUserAndPwd(String name, String pwd)  {
        this.SQL_username = name;
        this.SQL_password = pwd;
    }

    //创建连接
    public void getConnection(String _name, String _pwd){
        String driverClass = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        try {
            Class.forName(driverClass);
            connection = DriverManager.getConnection(url,_name, _pwd);
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    //确认
    public void checkin() throws SQLException {
        connection.setAutoCommit(false);
        java.util.Date date = new java.util.Date();
        java.sql.Date date1 = new java.sql.Date(date.getTime());
        String _id = id.getText();
        String sql = "update Hotel_manager.book_order set check_out_time = ? where order_id = "+"'"+_id+"'";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setDate(1,date1);
        pst.executeUpdate();
        connection.commit();

        Stage stage = (Stage)ok.getScene().getWindow();
        stage.close();
    }

    //取消
    public void Quit(){
        Stage stage = (Stage)quit.getScene().getWindow();
        stage.close();
    }
}
