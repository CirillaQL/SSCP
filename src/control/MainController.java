package control;

import Database.RoomData;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class MainController {
    @FXML
    private TableView<RoomData> roomtable;
    @FXML
    private TableColumn<RoomData,String> idColumn;
    @FXML
    private TableColumn<RoomData,String> typeColumn;
    @FXML
    private TableColumn<RoomData,String> sizeColumn;
    @FXML
    private TableColumn<RoomData,String> stateColumn;
    private ObservableList<RoomData> roomDataObservableList = FXCollections.observableArrayList();


    private String SQL_username;
    private String SQL_password;
    private Connection con;

    //获取用户名和密码
    public void getUserAndPwd(String name, String pwd)  {
        this.SQL_username = name;
        this.SQL_password = pwd;
    }

    //设置表格信息
    private void setRoomTableData(){
        idColumn.setCellValueFactory(cellData -> cellData.getValue().room_idProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().room_typeProperty());
        sizeColumn.setCellValueFactory(cellData -> cellData.getValue().room_sizeProperty());
        stateColumn.setCellValueFactory(cellData -> cellData.getValue().room_stateProperty());
        roomtable.setItems(roomDataObservableList);
    }

    //获取房间信息
    public void getRoominfomation(){
        String driverClass = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        try {
            Class.forName(driverClass);
            con = DriverManager.getConnection(url,SQL_username,SQL_password);
            String sql = "select ROOM_NUMBER,ROOM_TYPE,ROOM_SIZE,CURRENT_STATE from Hotel_Manager.ROOM";
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            setRoomTableData();
            while (rs.next()){
                RoomData the_room = new RoomData(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
                roomDataObservableList.add(the_room);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //创建订单
    public void addOrder() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Order.fxml"));
        Parent root = (Parent)loader.load();
        OrderController controller = loader.getController();
        controller.getUserAndPwd(SQL_username,SQL_password);
        controller.getConnection(SQL_username,SQL_password);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/resource/coffee.png")));
        stage.setTitle("增加订单");
        stage.show();
    }
}
