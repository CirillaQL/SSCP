package control;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;


public class LoginController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button quit;

    /*
        SQL部分
     */
    private String name;
    private String Pwd;

    //检查是否能登录
    protected boolean ifgetConnection(){
        String driverClass = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        Connection connection;
        try {
            Class.forName(driverClass);
            connection = DriverManager.getConnection(url,name,Pwd);
            if (connection != null) {
                connection.close();
                return true;
            }
            else{
                assert false;
                connection.close();
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /*
    按钮操作区域
     */
    @FXML
    protected void print() throws IOException {
        name = username.getText();
        Pwd = password.getText();
        //成功后进入新打开的界面
        if (ifgetConnection()){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
            Parent root = (Parent)loader.load();
            MainController mainController = loader.getController();
            mainController.getUserAndPwd(name,Pwd);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/resource/coffee.png")));
            stage.setTitle("酒店管理系统");
            stage.show();
            exitButtonOnMouseClicked();
        }
        //报错
        else {
            Alert error = new Alert(Alert.AlertType.ERROR,"用户名或密码错误，请检查后重试。");
            error.showAndWait();
        }
    }

    //关闭当前窗口
    public void exitButtonOnMouseClicked() {
        //通过stage方式操作窗口，因为一个新的窗口就是一个新的stage
        Stage stage = (Stage)quit.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void Quit(){
        System.exit(0);
    }

}
