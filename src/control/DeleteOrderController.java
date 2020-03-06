package control;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DeleteOrderController {
    @FXML
    private TextField getid;
    @FXML
    private Button ok;

    private String SQL_username;
    private String SQL_password;
    private Connection connection;

    private int[] order_id_list = new int[10];

    //获取连接信息
    public void getUserAndPwd(String name, String pwd)  {
        this.SQL_username = name;
        this.SQL_password = pwd;
    }

    //获取连接
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

    //初始化时获取订单idList
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


    //确定按钮
    public void deleteOrder() throws SQLException {
        int _id = Integer.parseInt(getid.getText());
        int flag = 0;
        for (int i:order_id_list){
            if (_id == i) flag++;
        }
        if (flag == 0 || order_id_list.length == 0){
            Alert error = new Alert(Alert.AlertType.ERROR,"订单id错误，请检查后重试。");
            error.showAndWait();
        }

        String sql = "delete from Hotel_Manager.book_order where book_order.order_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,_id);
        preparedStatement.executeUpdate();
        connection.commit();
        preparedStatement.close();

        Stage stage = (Stage)ok.getScene().getWindow();
        stage.close();
    }

}
