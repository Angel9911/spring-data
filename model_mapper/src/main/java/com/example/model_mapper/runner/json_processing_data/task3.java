package com.example.model_mapper.runner.json_processing_data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class task3 {
    public static void main(String[] args) {

/*      Extract the name of the restaurant from the example.
        Find and print the ingredients of all dishes.
        Check if any dish contains the allergen "Dairy."
        Extract and print the dishes that are gluten-free.
        Determine the types of specials available (e.g., gluten-free, vegetarian, vegan).*/

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String path = "E:\\spring-data\\model_mapper\\src\\main\\java\\com\\example\\model_mapper\\runner\\json_processing_data\\task3.json";

        Set<String> uniqueIngredients = new LinkedHashSet<>();
        Set<String> uniqueSpecials = new LinkedHashSet<>();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {

            Type type = new TypeToken<Map<String,Object>>(){}.getType();

            Map<String,Object> readJsonObjects = gson.fromJson(bufferedReader, type);

            if(!readJsonObjects.isEmpty()) {

                if (readJsonObjects.containsKey("restaurant")) {

                    System.out.println("The name of the restaurant is: " + readJsonObjects.get("restaurant").toString());
                }
                if (readJsonObjects.containsKey("menu")) {

                    List<Map<String,Object>> menu = (List<Map<String, Object>>) readJsonObjects.get("menu");

                    if(!menu.isEmpty()){

                        for(Map<String,Object>dish : menu){

                            if(dish.containsKey("ingredients")){

                                List<String > ingredients = (List<String>) dish.get("ingredients");

                                if(!ingredients.isEmpty()){
                                    uniqueIngredients.addAll(ingredients);
                                }
                            }

                            if(dish.containsKey("allergens")){

                                List<Object> allergens = (List<Object>) dish.get("allergens");

                                if(!allergens.isEmpty()){
                                    allergens
                                            .forEach(d -> {
                                                if(d.toString().equals("Dairy")){

                                                    System.out.println(dish.get("dish").toString());
                                                }
                                            });
                                }
                            }
                        }

                        System.out.println("All ingredients are: ");
                        uniqueIngredients.forEach(ingredient -> System.out.print(ingredient + ","));
                        System.out.println();
                    }
                }
                if(readJsonObjects.containsKey("specials")){

                    Map<String,Object> specials = (Map<String, Object>) readJsonObjects.get("specials");
                    if(!specials.isEmpty()){

                        for(Map.Entry<String, Object> key : specials.entrySet()){

                            if(key.getKey().equals("gluten_free")){

                                List<String> glutenFree = (List<String>) key.getValue();
                                if(!glutenFree.isEmpty()){
                                    glutenFree.forEach(dish -> System.out.println("dish that's gluten free is: "+dish));
                                }
                            }
                            uniqueSpecials.add(key.getKey());
                        }
                    }
                }
                System.out.println("Specials are: ");
                uniqueSpecials.forEach(special -> System.out.print(special + " "));
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
