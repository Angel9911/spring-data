package com.example.model_mapper.runner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class GsonTest {

    private static final String json =
            """
                    {
                     "username": "acho",
                     "password": "test1234"
                     }
                    """;

    static class LoginData{
        @Expose
        private String username;
        @Expose
        private String password;

        public LoginData(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        public String toString() {
            return "LoginData{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
    public static void main(String[] args) {

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();

        // if we don't want a control on class fields which will be added in json file, we can remove .excludeFieldsWithoutExposeAnnotation()
        // and remove @Expose annotations from class fields.

        LoginData loginData = new LoginData("acho","test1234");

        String result = gson.toJson(loginData);
        System.out.println("Convert object to json");

        System.out.println(result);
        System.out.println("Convert json to object");

        LoginData toLoginData = gson.fromJson(json, LoginData.class);
        System.out.println(toLoginData);
    }
}
