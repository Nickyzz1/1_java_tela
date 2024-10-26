package com.desktopapp;

import com.desktopapp.model.User;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
    public static void main(String[] args) {

        User user = new User();
        user.setName("");
        user.setPassword("");

        Context ctx = new Context();
        ctx.begin();
        ctx.save(user);
        ctx.commit();

        User user2 = new User();
        user2.setName("trevis");
        user2.setPassword("inquisicaoMelhorLivro");

        ctx.begin();
        ctx.save(user2);
        ctx.commit();


        // Product prod1 = new Product();
        // prod1.setNameProd("Inquisicao vol 1");
        // prod1.setPriceProd(10.0);

        // ctx.begin();
        // ctx.save(prod1);
        // ctx.commit();

        // Product prod2 = new Product();
        // prod2.setNameProd("Memorias do subsolo");
        // prod2.setPriceProd(10.0);

        // ctx.begin();
        // ctx.save(prod2);
        // ctx.commit();

        // Product prod3 = new Product();
        // prod3.setNameProd("Inquisicao vol 2");
        // prod3.setPriceProd(10.0);

        // ctx.begin();
        // ctx.save(prod3);
        // ctx.commit();

        launch(args);
    }
    @Override
    public void start(Stage arg0) throws Exception
    {
        Scene scene = LoginSceneController.CreateScene();
        arg0.setScene(scene);
        arg0.show();

    }
}