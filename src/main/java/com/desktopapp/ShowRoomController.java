package com.desktopapp;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.desktopapp.model.Cart;
import com.desktopapp.model.Product;
import com.desktopapp.model.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

// Collections
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ShowRoomController {
    
    @FXML
    private Button logout;

    @FXML
    private VBox buttonContainer;  // Certifique-se de que o fx:id "buttonContainer" corresponde ao do FXML

    @FXML
    private Button btGoToCart;

    private User loggedUser;

    // isso configura o jpa, o java persistence

    //o entityManagerFactory é para interagir com o banco de dados

    // exemplo de entitymenager

    // Criar uma única instância de EntityManagerFactory (normalmente em um ponto central da aplicação)
    // EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("my-persistence-unit");

    // // Criar um novo EntityManager sempre que precisar interagir com o banco de dados
    // EntityManager entityManager = entityManagerFactory.createEntityManager();

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("my-persistence-unit");

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User setLoggedUser) {
        this.loggedUser = setLoggedUser;
    }

    public static Scene CreateScene(User user) throws Exception {
        try {
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
    public void initialize() {
        // Carrega a lista de produtos e cria os botões
        ObservableList<Product> products = loadProducts(); // lista que pode ser observada por componentes da interface gráfica, por isso criamos uma obervableList quado precisamos, por exemplo, fazer um "for" para mostrar todos os produtos que existemgráficamente na janela
        createProductButtons(buttonContainer, products); // o primeiro parâmetro é ode vc quer que elesz sejam carregados, por exemplo, esse é o id da minha vbox
    }

    private ObservableList<Cart> cartItems = FXCollections.observableArrayList();

    // Método para criar os botões de produtos
    public void createProductButtons(VBox buttonContainer, ObservableList<Product> products) {
        buttonContainer.getChildren().clear();
    
        for (Product product : products) { // um for
            Button productButton = new Button(product.getNameProd()); // botão com o nome do produto
            productButton.setOnAction(event -> {
                System.out.println("Produto selecionado: " + product.getNameProd());

                Cart cartItem = new Cart();

                cartItem.setIdProduct(product.getidProduct());
                cartItem.setNameProd(product.getNameProd());
                cartItem.setValueProd(product.getPriceProd());

                cartItems.add(cartItem);
                saveCartToDatabase();

            }); // vai printar qual produto que é quando vc apertar

            buttonContainer.getChildren().add(productButton);
        }
    }

     // Método para salvar os itens do carrinho no banco de dados
     public void saveCartToDatabase() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        
        try {
            entityManager.getTransaction().begin();
            
            // Persiste cada item do carrinho no banco de dados
            for (Cart cartItem : cartItems) {
                entityManager.merge(cartItem);
            }
            
            entityManager.getTransaction().commit();
            System.out.println("Carrinho salvo no banco de dados.");
            
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    // private void contator()
    // {
    //     entityManagerFactory.createEntityManager();
    //     ObservableList<Product> productList = FXCollections.observableArrayList();
    //     // fxcollection tem um método estático chamado observbleList() que retorna uma oberrvableList
    //     // qualquer deleção ou adição nessa lsita pode ser monnitorada pelo javafx

    //     try {
    //         EntityManager entityManager = entityManagerFactory.createEntityManager();
    //         entityManager.getTransaction().begin();
    //         List<Cart> cart = entityManager.createQuery("FROM tbCart", Cart.class).getResultList();
    //         int count;
    //         for ( int i = 0; i < cart.size(); i++) {
    //             count = 1;
    //             for (int j = 0; j < cart.size(); j++) {

    //                 if(cart.get(i).getidProduct() == cart.get(j).getidProduct())
    //                 {
                       
    //                 }
                    
    //             }
    //         }
                
    //         }
    //     } catch (IOException e) {
    //         System.err.println(e);
    //     } finally {
    //         entityManager.close();
    //     }

    // }

    // Método para carregar produtos do banco de dados
    private ObservableList<Product> loadProducts() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        ObservableList<Product> productList = FXCollections.observableArrayList();
        // fxcollection tem um método estático chamado observbleList() que retorna uma oberrvableList
        // qualquer deleção ou adição nessa lsita pode ser monnitorada pelo javafx

        try {
            entityManager.getTransaction().begin(); // inicializa as operaões no banco de dados, vc vai começar uma nova transação e td a seguir faz parte dela

            // create query é para fazer selects no banco de dados
            List<Product> products = entityManager.createQuery("FROM Product", Product.class).getResultList();

            // Adiciona produtos à lista usando um loop for
            for (Product product : products) {
                productList.add(product);
            }
            
            entityManager.getTransaction().commit(); // finaliza a transação

        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback(); // Reverte a transação em caso de erro
            }
            e.printStackTrace();
        } finally {
            entityManager.close(); // Fecha o EntityManager
        }
    
        return productList; // Retorna a lista de produtos
    }
    
    @FXML
    public void GoToCart() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("cartScene.fxml"));
        Parent root = loader.load();
        CartController controller = loader.getController(); // jeito de pegar controller é
        
        if (controller != null) {
            controller.atualizarTabelaCart(); // Chame o método apenas se o controller não for nulo
            Stage currentStage = (Stage) btGoToCart.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } else {
            System.err.println("Erro: CartController está nulo.");
        }
    }     
}
