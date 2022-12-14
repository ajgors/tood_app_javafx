package com.example.todo_app_javafx.controllers;

import com.example.todo_app_javafx.dao.UserDao;
import com.example.todo_app_javafx.model.ConnectToDb;
import com.example.todo_app_javafx.model.Model;
import com.example.todo_app_javafx.model.User;
import com.example.todo_app_javafx.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField loginEmailFld;
    @FXML
    private PasswordField passwordFld;
    @FXML
    private Button loginBtn;
    @FXML
    private Button createNewAccountBtn;
    @FXML
    private AnchorPane root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginBtn.setOnAction(e -> onLoginButtonClick());
        createNewAccountBtn.setOnAction(e -> root.getScene().setRoot(ViewFactory.getRegisterView()));
    }

    private void onLoginButtonClick() {
        String userTypedLoginOrEmail = loginEmailFld.getText();
        String userTypedPassword = passwordFld.getText();

        User user = UserDao.getUserByLoginAndPassword(userTypedLoginOrEmail, userTypedPassword);

        if (ConnectToDb.entityManager != null) {
            if (user != null) {
                Model.getInstance().setUser(user);
                ViewFactory.getTasksWindow();
                closeCurrentStage();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.show();
            }
        }

    }

    private void closeCurrentStage() {
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        stage.close();
    }

}