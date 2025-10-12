package com.mitprojekt;

import com.mitprojekt.dao.IngrediensDAO;
import com.mitprojekt.model.Ingrediens;
import com.mitprojekt.dao.SetupDatabase;

public class App {
    public static void main(String[] args) {
        SetupDatabase.initialize();
        System.out.println("Klar til at køre tests eller indsætte data.");
        IngrediensDAO dao = new IngrediensDAO();
        Ingrediens i = new Ingrediens("TestIngrediens");

    }
}
