package com.desktopapp;

import java.net.URL;
import java.util.ResourceBundle;

import com.desktopapp.model.Cart;
import com.desktopapp.model.Product;
import com.desktopapp.model.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;


import jakarta.persistence.TypedQuery;

import javafx.util.Callback;

public class CartController implements Initializable {

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

    @FXML
    private TableColumn<Cart, Void> deleteCol; // Corrigido o tipo aqui

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
        idCol.setCellValueFactory(new PropertyValueFactory<>("idProduct")); // Corrigido
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nameProd")); // Corrigido
        priceCol.setCellValueFactory(new PropertyValueFactory<>("valueProd")); // Corrigido
        quantCol.setCellValueFactory(new PropertyValueFactory<>("quat")); // Corrigido
      
        deleteCol.setCellFactory(new Callback<TableColumn<Cart, Void>, TableCell<Cart, Void>>() { // Corrigido o tipo aqui
            @Override
            public TableCell<Cart, Void> call(TableColumn<Cart, Void> param) {
                return new TableCell<Cart, Void>() {
                    private final Button btn = new Button("Excluir");

                    {
                        btn.setOnAction(event -> {
                            Cart cartItem = getTableView().getItems().get(getIndex()); 
                            deleteProduct(cartItem); 
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : btn);
                    }
                };
            }
        }); 

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
            cont++;
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
    protected void goBackHome() {
        try {
            var scene = ShowRoomController.CreateScene(getLoggedUser());
            Stage currentStage = (Stage) goBack.getScene().getWindow();
            currentStage.setScene(scene);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    // Implemente o método deleteProduct
    private void deleteProduct(Cart product) {
        Context ctx = new Context();
        ctx.begin();
        try {
            ctx.remove(product); // Chama o método para remover o produto
            ctx.commit();        // Confirma a remoção no banco de dados
            tableView.getItems().remove(product); // Atualiza a tabela
            tableView.setItems(produtos());
        } catch (Exception e) {
            e.printStackTrace();
            if (ctx != null) {
                ctx.commit(); // Rollback em caso de erro
            }
        }
    }
}
