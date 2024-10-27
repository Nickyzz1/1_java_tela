package com.desktopapp;

import java.net.URL;
import java.io.IOException;

import com.desktopapp.model.Product;
import com.desktopapp.model.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddProductsController {

    @FXML
    private Button btAdd;

    @FXML
    private Button btCancel;

    @FXML
    private TextField nameProd;

    @FXML
    private TextField priceProd;

    private User loggedUser;
    
    
    public User getLoggedUser() {
        return loggedUser;
    }
    
    public void setLoggedUser(User setLoggedUser) {
        this.loggedUser = setLoggedUser;
    }
    
    @FXML
    public void add() 
    {
        Product prod1 = new Product();
        prod1.setNameProd(nameProd.getText());
        prod1.setPriceProd(Double.parseDouble(priceProd.getText()));

        Context ctx = new Context();

        ctx.begin();
        ctx.save(prod1);
        ctx.commit();

        try {
            var scene = HomeAdminController.CreateScene(getLoggedUser());
            Stage currentStage = (Stage) btCancel.getScene().getWindow();
            currentStage.setScene(scene);
        } catch (Exception e) {
            System.err.println(e);
        }
        try {
            var scene = HomeAdminController.CreateScene(getLoggedUser());
            Stage currentStage = (Stage) btCancel.getScene().getWindow();
            currentStage.setScene(scene);
        } catch (Exception e) {
            System.err.println(e);
        }   
        
        // fechar janela closeWindow();
    }


    public static Scene CreateScene(User user) throws Exception {
        try {
            URL sceneUrl = AddProductsController.class.getResource("addingScene.fxml");
            FXMLLoader loader = new FXMLLoader(sceneUrl);
            Parent root = loader.load();
        
            AddProductsController controller = loader.getController(); 
            controller.setLoggedUser(user);
        
            return new Scene(root);

        } catch (IOException e) {
            System.err.println(e);
            return null;
        }
  
    }

    public void cancelAction()
    {
        try {
            var scene = HomeAdminController.CreateScene(getLoggedUser());
            Stage currentStage = (Stage) btCancel.getScene().getWindow();
            currentStage.setScene(scene);
        } catch (Exception e) {
            System.err.println(e);
        }   
    }
    
}
