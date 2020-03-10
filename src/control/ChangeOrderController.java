package control;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;

public class ChangeOrderController {
    private String SQL_username;
    private String SQL_password;

    private Connection connection;

    private int[] order_id_list = new int[10];

    @FXML
    private TextField order_id;
    @FXML
    private TextField name;
    @FXML
    private TextField phone;
    @FXML
    private TextField vip;
    @FXML
    private TextField price;
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

    public void getOrderIdList() {
        try {
            connection.setAutoCommit(false);
            String sql = "select order_id from Hotel_Manager.book_order";
            PreparedStatement psmt = connection.prepareStatement(sql);
            ResultSet rs = psmt.executeQuery();
            connection.commit();
            int p = 0;
            while(rs.next()){
                order_id_list[p] = rs.getInt(1);
                p++;
            }
            rs.close();
            psmt.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //获取内容
    public void ChangeOrder() throws SQLException {
        connection.setAutoCommit(false);
        //得到order id
        int orderId = Integer.parseInt( order_id.getText());
        if (orderId==0){
            Alert error = new Alert(Alert.AlertType.ERROR,"订单id错误，请检查后重试。");
            error.showAndWait();
        }
        int flag = 0;
        for (int i:order_id_list){
            if (orderId == i) flag++;
        }
        if (flag == 0 || order_id_list.length == 0){
            Alert error = new Alert(Alert.AlertType.ERROR,"订单id错误，请检查后重试。");
            error.showAndWait();
        }

        //获取修改内容
        String _name = name.getText();
        String _phone = phone.getText();
        String _vip = vip.getText();
        String _price = price.getText();

        //第一步修改名字
        if (_name != null ){
            String sql = "update Hotel_manager.book_order set cus_name = ? where order_id = "+"'"+orderId+"'";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1,_name);
            pst.executeUpdate();
            connection.commit();
        }
        if (_phone != null ){
            String sql = "update Hotel_manager.book_order set cus_phone_number = ? where order_id = "+"'"+orderId+"'";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1,_phone);
            pst.executeUpdate();
            connection.commit();
        }
        if (_vip != null ){
            String sql = "update Hotel_manager.book_order set vip_id = ? where order_id = "+"'"+orderId+"'";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1,_vip);
            pst.executeUpdate();
            connection.commit();
        }
        if (_price != null ){
            String sql = "update Hotel_manager.book_order set origin_price = ? where order_id = "+"'"+orderId+"'";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1,_price);
            pst.executeUpdate();
            connection.commit();
        }

        Stage stage = (Stage)ok.getScene().getWindow();
        stage.close();

    }


}
