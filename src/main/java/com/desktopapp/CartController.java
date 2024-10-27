package com.desktopapp;

import java.net.URL;
import java.util.ResourceBundle;

import com.desktopapp.model.Cart;
import com.desktopapp.model.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CartController implements Initializable
{

    private User loggedUser;

    @FXML
    protected Button goBack;

    @FXML 
    private TableView<Cart> tableView; 

    @FXML
    private TableColumn<Cart, ?> idCol; 

    @FXML
    private TableColumn<Cart, ?> nameCol; 

    @FXML
    private TableColumn<Cart, ?> priceCol; 

    @FXML
    private TableColumn<Cart, ?> quantCol; 

    public static Scene CreateScene(User user) throws Exception {
        URL sceneUrl = CartController.class.getResource("cartScene.fxml");
        FXMLLoader loader = new FXMLLoader(sceneUrl);
        Parent root = loader.load();
        
        CartController controller = loader.getController(); 
        controller.setLoggedUser(user); 
    
        return new Scene(root);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("idCart"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nameProd"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("valueProd"));
    
        ObservableList<Cart> lista = produtos();
        if (lista != null && !lista.isEmpty()) {
            this.tableView.setItems(lista);
        } else {
            System.out.println("Seu carrinho está vazio.");
        }
    }

    public ObservableList<Cart> produtos() {
        Context ctx = new Context();
        int cont = 1;
        ObservableList<Cart> Lista2 = FXCollections.observableArrayList();
        while (true) {
            Cart produto = ctx.find(Cart.class, (Object)cont);
            if (produto == null) {
                break;
            }
            Lista2.add(produto);
            cont ++;
        }
        return Lista2;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    @FXML
    protected void goBackHome()
    {
        try {
            var scene = ShowRoomController.CreateScene(getLoggedUser());
            Stage currentStage = (Stage) goBack.getScene().getWindow();
            currentStage.setScene(scene);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
 