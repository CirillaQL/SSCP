package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.*;
import javax.xml.soap.Text;
import java.sql.*;

public class CheckOrderController {
    private String SQL_username;
    private String SQL_password;
    private Connection connection;
    private String orderId;


    @FXML
    private TextField getorder;
    @FXML
    private Button quit;
    @FXML
    private TextField name;
    @FXML
    private TextField phone;
    @FXML
    private TextField room;
    @FXML
    private TextField vip;

    public void getUserAndPwd(String name, String pwd)  {
        this.SQL_username = name;
        this.SQL_password = pwd;
    }

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

    //获取输入订单id号
    public void getOrderId() throws InterruptedException {
        this.orderId = getorder.getText();
        Thread.sleep(2);
        setOrderInformation();
    }

    //退出
    public void quit(){
        Stage stage = (Stage)quit.getScene().getWindow();
        stage.close();
    }

    //显示内容
    protected void setOrderInformation(){
        String _name = "";
        String _phone = "";
        String _room = "";
        String _vip = "";
        try {
            String sql = "select cus_name, cus_phone_number, room_number, vip_id from Hotel_Manager.book_order where order_id = " + "'" + orderId + "'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                _name = resultSet.getString(1);
                _phone = resultSet.getString(2);
                _room = resultSet.getString(3);
                _vip = resultSet.getString(4);
            }
            name.setText(_name);
            phone.setText(_phone);
            room.setText(_room);
            vip.setText(_vip);
            resultSet.close();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
