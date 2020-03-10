package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;
import java.text.SimpleDateFormat;

public class CreateVIPController {
    private String SQL_username;
    private String SQL_password;
    private Connection connection;

    @FXML
    private TextField name;
    @FXML
    private TextField phone;
    @FXML
    private TextField blance;
    @FXML
    private TextField vipid;

    @FXML
    private Button ok;
    @FXML
    private Button quit;

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

    //创建VIP
    public void createvip() throws SQLException {
        connection.setAutoCommit(false);

        SimpleDateFormat df = new SimpleDateFormat("yyyymmddss");
        String m = df.format(new java.util.Date());
        int l = (Integer.parseInt(m)%7)+123;
        String _id = l +"";

        String _name = name.getText();
        String _phone = phone.getText();
        double _blance = Double.parseDouble(blance.getText()) ;

        java.util.Date date = new java.util.Date();
        java.sql.Date date1 = new java.sql.Date(date.getTime());

        String sql = "insert into Hotel_manager.vip (id, vip_name, phone_number, apply_date, balance) values(?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,_id);
        pstm.setString(2,_name);
        pstm.setString(3,_phone);
        pstm.setDate(4,date1);
        pstm.setDouble(5,_blance);
        pstm.executeUpdate();
        connection.commit();

        vipid.setText(_id);
    }

    public void Quit(){
        Stage stage = (Stage)quit.getScene().getWindow();
        stage.close();
    }
}
