package com.mitprojekt;

import com.mitprojekt.dao.IngredientDAO;
import com.mitprojekt.model.Ingredient;
import com.mitprojekt.util.SetupDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        SetupDatabase.initialize();
        System.out.println("Klar til at køre tests eller indsætte data.");
        IngredientDAO dao = new IngredientDAO();
        Ingredient i = new Ingredient("TestIngredient");

    }
}

