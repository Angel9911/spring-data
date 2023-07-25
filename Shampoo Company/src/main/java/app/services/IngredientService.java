package app.services;

import app.entities.Ingredient;

import java.util.List;

public interface IngredientService {
    List<Ingredient> selectIngredientsStartWith(String start);
    List<Ingredient> selectIngredientsByNames(List<String> ingredientsNames);
    int deleteByName(String name);
    int increasePriceByPercentage(double percent);
}
