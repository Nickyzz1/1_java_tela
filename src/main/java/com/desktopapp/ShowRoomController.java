package com.desktopapp;

import java.io.IOException;
import java.net.URL;

import com.desktopapp.model.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ShowRoomController {
    
    @FXML
    protected Button logout;
    
    private User loggedUser;
    
    public User getLoggedUser() {
        return loggedUser;
    }


    public void setLoggedUser(User setLoggedUser) {
        this.loggedUser = setLoggedUser;
    }

    public static Scene CreateScene(User user) throws Exception {

        try{

            URL sceneUrl = ShowRoomController.class.getResource("showRoomScene.fxml");
            FXMLLoader loader = new FXMLLoader(sceneUrl);
            Parent root = loader.load();
        
            ShowRoomController controller = loader.getController(); 
            controller.setLoggedUser(user);
        
            return new Scene(root);

        } catch (IOException e) {
            System.err.println(e);
            return null;
        }
    }

    // estrutura para voltar sem user logado
    @FXML
    public void logOut()
    {
        
      try {
            var scene = LoginSceneController.CreateScene();
            Stage currentStage = (Stage) logout.getScene().getWindow();
            currentStage.setScene(scene);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
