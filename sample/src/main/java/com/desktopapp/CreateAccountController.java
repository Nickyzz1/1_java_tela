package com.desktopapp;

import java.net.URL;
// import java.util.ResourceBundle;

import com.desktopapp.model.User;

// imports fxml
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
// import javafx.fxml.Initializable;

//imports scene
import javafx.scene.Parent;
import javafx.scene.Scene;

//imports control
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

public class CreateAccountController  {

    @FXML
    protected TextField userNameCad;
    @FXML
    protected PasswordField userPassCad;
    @FXML
    protected Button btCreateAccount;

    // @Override
    // public void initialize(URL location, ResourceBundle resources) 
    // {
        
    // }

    @FXML
    protected void createAccount(ActionEvent e) throws Exception 
    {
        User user = new User();
        user.setName(userNameCad.getText());
        user.setPassword(userPassCad.getText());
    
        Context ctx = new Context();
        ctx.begin();
        ctx.save(user);
        ctx.commit();
        
        URL newSceneUrl = getClass().getResource("loginScene.fxml");
        Parent newRoot = FXMLLoader.load(newSceneUrl);
        Scene newScene = new Scene(newRoot);
        Stage currentStage = (Stage) btCreateAccount.getScene().getWindow();
        currentStage.setScene(newScene);
        currentStage.show();
    }
}
