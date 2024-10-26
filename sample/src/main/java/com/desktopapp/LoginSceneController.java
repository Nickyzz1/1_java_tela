package com.desktopapp;

import java.net.URL;

import com.desktopapp.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginSceneController {

    public static Scene CreateScene() throws Exception {
        URL sceneUrl = LoginSceneController.class
                .getResource("loginScene.fxml");
        Parent root = FXMLLoader.load(sceneUrl);
        Scene scene = new Scene(root);
        return scene;
    }
    @FXML
    protected Button btLogon;
    @FXML
    protected Button btGoToCreateAccount;
    @FXML
    protected TextField userName;
    @FXML
    protected PasswordField userPass;
    @FXML
    protected CheckBox cbPass;


    @FXML
    protected void submit(ActionEvent e) throws Exception {
        Context ctx = new Context();
        var users = ctx.find(User.class,
                "SELECT u FROM User u WHERE u.name = :arg0",
                userName.getText()
        );

        if (users.isEmpty()) {
            Alert alert = new Alert(
                AlertType.ERROR,
                "Usuário não está cadastrado!",
                ButtonType.OK
            );
            alert.showAndWait();
            return;
        }

        var user = users.get(0);
        if (!userPass.getText().equals(user.getPassword())) {
            Alert alert = new Alert(
                AlertType.ERROR,
                "Senha incorreta!",
                ButtonType.OK
            );
            alert.showAndWait();
            return;
        }

        var scene = ShowRoomController.CreateScene(user);
        Stage currentStage = (Stage) btLogon.getScene().getWindow();
        currentStage.setScene(scene);
    }

    @FXML
    protected void changeToCreateAccount(ActionEvent e) throws Exception {
        URL newSceneUrl = getClass().getResource("createAccountScene.fxml");
        Parent newRoot = FXMLLoader.load(newSceneUrl);

        // Crie uma nova cena
        Scene newScene = new Scene(newRoot);

        // Obtenha o estágio atual e defina a nova cena
        Stage currentStage = (Stage) btGoToCreateAccount.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }

}
