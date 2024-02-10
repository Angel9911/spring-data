package com.example.model_mapper.runner.json_processing_data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class task1 {
    public static void main(String[] args) {
        /* tasks:

        Extract the street address of the first person . - 1.1 done
        Find and print the second hobby of the second person . - 1.2 done
        Check if the age of any person in is greater than 30. - 1.3 done
        Extract and print the email contacts of all people . - 1.4 done
        Determine the total number of hobbies across all people in each example. -  1.5 done
         */

        Gson gson = new GsonBuilder()
                .setPrettyPrinting().create();


        String path = "E:\\spring-data\\model_mapper\\src\\main\\java\\com\\example\\model_mapper\\runner\\json_processing_data\\task1.json";

        try(BufferedReader reader = new BufferedReader(new FileReader(path))) {

            Type type = new TypeToken<List<Map<String,Object>>>(){}.getType();

            List<Map<String,Object>> readObjects = gson.fromJson(reader, type);

            if(!readObjects.isEmpty()){

                int allHobbies = 0;

                for(Map<String,Object> object: readObjects){

                    if(object.containsKey("name") && !object.get("name").toString().isEmpty() && object.get("name").toString().equals("Alice Smith")) {

                        if (object.containsKey("address")) {
                            Map<String, Object> address = (Map<String, Object>) object.get("address");
                            if (address.containsKey("street")){
                                System.out.println("The street address of the first person is "+ address.get("street")); // 1.1 done
                            }
                        }
                    }

                    if(object.containsKey("name") && !object.get("name").toString().isEmpty() && object.get("name").toString().equals("Bob Johnson")){
                        if(object.containsKey("hobbies")){

                            List<String> hobbies = (List<String>) object.get("hobbies");

                            String getSecondHobby = hobbies.get(1);

                            System.out.println("Second hobby of second person "+getSecondHobby); // 1.2 done
                        }
                    }

                    if(object.containsKey("age") && !object.get("age").toString().isEmpty()){
                        double getDoubleAge = Double.parseDouble(object.get("age").toString());
                        int age = (int) getDoubleAge;
                        if(age > 30){
                            System.out.println("Person that his age is greater than 30: "+object.get("name").toString()); // 1.3 done
                        }

                    }

                    if(object.containsKey("contacts")){

                        List<Map<String,Object>> contacts = (List<Map<String, Object>>) object.get("contacts");

                        Map<String, Object> email = contacts.get(0);

                        if(email.containsKey("type") && email.get("type").toString().equals("email")){
                            System.out.println(email.get("value").toString()); // 1.4 done
                        }
                    }

                    if(object.containsKey("hobbies")){
                        List<String> hobbies = (List<String>) object.get("hobbies");
                        allHobbies += hobbies.size();
                        System.out.println("All hobbies of all persons "+allHobbies); // 1.5 done
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
