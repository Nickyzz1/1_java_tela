package com.desktopapp;

import java.io.IOException;
import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;
import javafx.util.Callback;

import com.desktopapp.model.Product;
import com.desktopapp.model.User;

import jakarta.persistence.TypedQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class HomeAdminController implements Initializable{

    @FXML
    protected Button logout;

    private User loggedUser;

    @FXML 
    private TableView<Product> tableView; 

    @FXML
    private TableColumn<Product, Long> idCol; 

    @FXML
    private TableColumn<Product, String> nameCol; 

    @FXML
    private TableColumn<Product, Double> priceCol; 
    
    @FXML
    private TableColumn<Product, Void> editCol; 

    @FXML
    private TableColumn<Product, Void> deleteCol; 

    @FXML
    private TextField nomeProduto;

    @FXML
    private TextField priceProduto;


    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User setLoggedUser) {
        this.loggedUser = setLoggedUser;
    }

    public static Scene CreateScene(User user) throws Exception {
        try {
            URL sceneUrl = HomeAdminController.class.getResource("homeAdminScene.fxml");
            FXMLLoader loader = new FXMLLoader(sceneUrl);
            Parent root = loader.load();
        
            HomeAdminController controller = loader.getController(); 
            controller.setLoggedUser(user);
        
            return new Scene(root);

        } catch (IOException e) {
            System.err.println(e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nameProd"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("priceProd"));

        // Configuração da coluna de editar
        editCol.setCellFactory(new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @FXML
            public TableCell<Product, Void> call(TableColumn<Product, Void> param) {
                return new TableCell<Product, Void>() {
                    private final Button editButton = new Button("Editar");
    
                    {
                        // Configurar o evento de clique para abrir a nova tela de edição
                        editButton.setOnAction(event -> {
                            Product selectedProduct = getTableView().getItems().get(getIndex());
                            openEditProductScreen(selectedProduct);
                        });
                    }
    
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : editButton);
                    }
                };
            }
        });
    
        // Adicionar a coluna editar à tabela, se ainda não estiver adicionada
        if (!tableView.getColumns().contains(editCol)) {
            tableView.getColumns().add(editCol);
        }

        // Configuração da coluna de excluir
        deleteCol.setCellFactory(new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(TableColumn<Product, Void> param) {
                return new TableCell<Product, Void>() {
                    private final Button btn = new Button("Excluir");

                    {
                        btn.setOnAction(event -> {
                            Product product = getTableView().getItems().get(getIndex());
                            deleteProduct(product);
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

        // Adicionando as colunas de editar e excluir à tabela
        tableView.getColumns().addAll(editCol, deleteCol);

        // Carregar produtos na tabela
        ObservableList<Product> lista = produtos();
        if (lista != null && !lista.isEmpty()) {
            this.tableView.setItems(lista);
        } else {
            System.out.println("Seu carrinho está vazio.");
        }
    }


    @FXML
    protected void createProduct()
    {
        Product prod1 = new Product();
        prod1.setNameProd( nomeProduto.getText());
        prod1.setPriceProd(Double.parseDouble(priceProduto.getText()));

        Context ctx = new Context();

        ctx.begin();
        ctx.save(prod1);
        ctx.commit();
    }

   

    public ObservableList<Product> produtos() {
        Context ctx = new Context();
        ObservableList<Product> lista = FXCollections.observableArrayList();
        try {
            String jpql = "SELECT p FROM Product p";
            TypedQuery<Product> query = ctx.createQuery(Product.class, jpql);
            List<Product> produtosList = query.getResultList();
            lista.addAll(produtosList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return lista;
    }


    private void openEditProductScreen(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editarProduto.fxml"));
            Parent editRoot = loader.load();
    
            EditProductController editController = loader.getController();
            editController.setProduct(product);
    
            Stage editStage = new Stage();
            editStage.setScene(new Scene(editRoot));
            editStage.setTitle("Editar Produto");
            editStage.show();
            
            // Atualizar a tabela após fechar a janela de edição
            editStage.setOnHiding(event -> tableView.refresh());
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteProduct(Product product) {
        Context ctx = new Context();
        ctx.begin();
        try {
            ctx.remove(product); // Chama o método para remover o produto
            ctx.commit();        // Confirma a remoção no banco de dados
            tableView.getItems().remove(product); // Atualiza a tabela
        } catch (Exception e) {
            e.printStackTrace();
            if (ctx != null) {
                ctx.commit(); // Rollback em caso de erro
            }
        }
    }

    @FXML
    public void logOut() {
        try {
            var scene = LoginSceneController.CreateScene();
            Stage currentStage = (Stage) logout.getScene().getWindow();
            currentStage.setScene(scene);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @FXML
    public void changeToAddproduct()
    {}

}