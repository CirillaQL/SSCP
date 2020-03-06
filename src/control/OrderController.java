package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class OrderController {
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private TextField getName;
    @FXML
    private TextField getNumber;
    @FXML
    private TextField getRoomNum;
    @FXML
    private TextField VIP_id;
    @FXML
    private TextField price;
    @FXML
    private Button quit;

    //SQL信息
    private String SQL_username;
    private String SQL_password;
    private Connection connection;

    //订单信息
    private String id;
    private String employee_name;
    private String name;
    private String phone_number;
    private String room_number;
    private Date create_time;
    private Date book_begin_time;
    private Date book_end_time;
    private Date check_in_time;
    private Date check_out_time;
    private String vip_id;
    private double origin_price;
    private double actual_price;

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

    //获取当前时间
    private Date get_time_now(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date time=null;
        try {
            time= sdf.parse(sdf.format(new java.util.Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert time != null;
        long t = time.getTime();
        return new Date(t);
    }

    //生成订单id
    private String create_order_id(){
        SimpleDateFormat df = new SimpleDateFormat("yyyymmddss");
        return df.format(new java.util.Date());
    }

    //生成订单
    public void create_Order(){
        this.id = create_order_id();
        this.employee_name = this.SQL_username;
        this.name = getName.getText();
        this.phone_number = getNumber.getText();
        this.room_number = getRoomNum.getText();
        this.create_time = get_time_now();
        this.book_begin_time = Date.valueOf(startDate.getValue());
        this.book_end_time = Date.valueOf(endDate.getValue());
        this.vip_id = VIP_id.getText();
        this.origin_price = Double.parseDouble(price.getText());
        if (vip_id.equals("")){
            this.actual_price = origin_price;
        }
        else
            this.actual_price = origin_price * 0.8;
    }

    //输出测试
    public void print_date(){
        System.out.println(id);
        System.out.println(employee_name);
        System.out.println(name);
        System.out.println(phone_number);
        System.out.println(room_number);
        System.out.println(create_time);
        System.out.println(book_begin_time);
        System.out.println(book_end_time);
        System.out.println(vip_id);
        System.out.println(origin_price);
    }

    //提交到数据库
    private boolean push_to_Database() throws SQLException {
        connection.setAutoCommit(false);
        int k=0;
        try{
            String sql = "insert into Hotel_manager.book_order (order_id, employee_name, cus_name, cus_phone_number, room_number, create_time, book_time_begin, book_time_end, vip_id, origin_price, actuall_price) values (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,id);
            statement.setString(2,employee_name);
            statement.setString(3,name);
            statement.setString(4,phone_number);
            statement.setString(5,room_number);
            statement.setDate(6,create_time);
            statement.setDate(7,book_begin_time);
            statement.setDate(8,book_end_time);
            statement.setString(9,vip_id);
            statement.setDouble(10,origin_price);
            statement.setDouble(11,actual_price);
            k = statement.executeUpdate();
            connection.commit();
            System.out.println(k);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return k != 0;
    }

    //确定按钮动作
    public void addOrder() throws SQLException {
        create_Order();
        push_to_Database();
        //将表格房间状态修改
        connection.setAutoCommit(false);
        String sql = "update Hotel_manager.room set current_state = 'Full' where room_number = "+"'"+room_number+"'";
        Statement pst = connection.prepareStatement(sql);
        pst.executeUpdate(sql);
        connection.commit();
    }

    //关闭当前窗口
    public void exitButtonOnMouseClicked() {
        //通过stage方式操作窗口，因为一个新的窗口就是一个新的stage
        Stage stage = (Stage)quit.getScene().getWindow();
        stage.close();
    }

    //退出按钮动作
    public void Quit(){
        exitButtonOnMouseClicked();
    }

}
