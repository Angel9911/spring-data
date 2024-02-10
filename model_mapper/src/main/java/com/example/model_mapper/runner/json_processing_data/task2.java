package com.example.model_mapper.runner.json_processing_data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class task2 {
    public static void main(String[] args) {

        /*Extract the name of the library/music store from each example.
        Find and print the genres of all books/instruments in Example 1.
        Extract and print the brands of guitars in Example 2.
        Determine the types of instruments in the "rock_band" set in Example 2.*/

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String path = "E:\\spring-data\\model_mapper\\src\\main\\java\\com\\example\\model_mapper\\runner\\json_processing_data\\task2.json";
        
        Set<String> genres = new TreeSet<>();
        Set<String> brands = new TreeSet<>();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){

            Type type = new TypeToken<Map<String,Object>>(){}.getType();

            Map<String, Object> readJsonObjects = gson.fromJson(bufferedReader, type);

            if(!readJsonObjects.isEmpty()){

                for(String key:readJsonObjects.keySet()){

                    if(!key.isEmpty() && key.equals("music_store")){
                        System.out.println("name of music store: "+readJsonObjects.get(key)); // name of the music store from each example
                    }
                    if(key.equals("instruments")){

                        List<Map<String,Object>> instruments = (List<Map<String, Object>>) readJsonObjects.get(key);

                        if(!instruments.isEmpty()){
                            
                            for(Map<String,Object>instrument:instruments) {

                                if (instrument.containsKey("genres")) {

                                    List<String> readGenres = (List<String>) instrument.get("genres");

                                    if (!readGenres.isEmpty()) {
                                        genres.addAll(readGenres); // Find the genres of all instruments
                                    }
                                }
                                if (instrument.containsKey("type") && instrument.get("type").toString().equals("Guitar")) {

                                    if (instrument.containsKey("brands")) {

                                        List<String> readBrands = (List<String>) instrument.get("brands");

                                        if (!readBrands.isEmpty()) {
                                            brands.addAll(readBrands); // Extract the brands of guitars in Example 2.
                                        }
                                    }
                                    System.out.println("All brands of guitar are: ");
                                    brands.forEach(brand -> System.out.print(brand + " ")); // print the brands of guitars in Example 2.
                                    System.out.println();
                                }
                            }

                            System.out.println("All genres are: ");
                            genres.forEach(genre -> System.out.print(genre + " ")); // print the genres of all instruments
                            System.out.println();
                        }
                    }
                    if(key.equals("instrument_sets")){

                        Map<String, Object> instrumentSets = (Map<String, Object>) readJsonObjects.get(key);

                        if(instrumentSets.containsKey("rock_band")){  //Determine the types of instruments in the "rock_band" set in Example 2.

                            List<String> rockBandSet = (List<String>) instrumentSets.get("rock_band");

                            if(!rockBandSet.isEmpty()){
                                System.out.println("Instrument set in rock band is: ");
                                rockBandSet.forEach(instrument -> System.out.print(instrument + " , "));
                            }
                        }
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
