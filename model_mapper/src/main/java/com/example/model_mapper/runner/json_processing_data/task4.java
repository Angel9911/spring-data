package com.example.model_mapper.runner.json_processing_data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class task4 {
    public static void main(String[] args) {

/*      Extract the name of the university from the example.
        Find and print the names of all courses offered in the Computer Science department.
        Check if there are any prerequisites for the "Data Structures and Algorithms" course.
        Extract and print the names of professors who teach the "Data Structures and Algorithms" course.
        Determine the total number of credits for all courses offered in the Electrical Engineering department.*/

        String path = "E:\\spring-data\\model_mapper\\src\\main\\java\\com\\example\\model_mapper\\runner\\json_processing_data\\task4.json";

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Set<String> uniqueCourses = new TreeSet<>();

        Map<String, Object> dataStructureCourse = new HashMap<>();
        Map<String,Object> dataStructureProfessor = new HashMap<>();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){

            Type type = new TypeToken<Map<String,Object>>(){}.getType();

            Map<String,Object> readJsonObjects = gson.fromJson(bufferedReader, type);

            if(!readJsonObjects.isEmpty()){

                if(readJsonObjects.containsKey("university") && !readJsonObjects.get("university").toString().isEmpty()){

                    System.out.println("The university name is "+readJsonObjects.get("university").toString()); // Extract the name of the university from the example.
                }

                if(readJsonObjects.containsKey("departments")){

                    List<Map<String,Object>> departments = (List<Map<String, Object>>) readJsonObjects.get("departments");

                    if(!departments.isEmpty()){

                        for(Map<String,Object>department : departments){

                            if(department.containsKey("name") && department.get("name").toString().equals("Computer Science")){

                                if(department.containsKey("courses")){

                                    List<Map<String,Object>> computerScienceCourses = (List<Map<String, Object>>) department.get("courses");

                                    if(!computerScienceCourses.isEmpty()){

                                        // with lambda
                                        Set<String> cScienceCourseNames = computerScienceCourses.stream()
                                                .flatMap(course -> course.entrySet().stream())
                                                .filter(courseVal -> courseVal.getKey().equals("name"))
                                                .map(Map.Entry::getValue)
                                                .filter(courseNameValue -> courseNameValue != null && !courseNameValue.toString().isEmpty())
                                                .map(Object::toString)
                                                .collect(Collectors.toSet());

                                        System.out.println("Computer Science courses: ");
                                        cScienceCourseNames.forEach(name -> System.out.print(name+ " ")); // Find and print the names of all courses offered in the Computer Science department.
                                        System.out.println();

                                        uniqueCourses.addAll(cScienceCourseNames);

                                        Optional<Map<String, Object>> isCourseExists= computerScienceCourses.stream()
                                                .filter(course -> course.containsKey("name") && course.get("name").toString().equals("Data Structures and Algorithms"))
                                                .filter(course -> course.containsKey("prerequisites") && !course.get("prerequisites").toString().isEmpty())
                                                .findFirst();

                                        if(isCourseExists.isPresent()){

                                            dataStructureCourse  = isCourseExists.get();
                                            System.out.println("Prerequisites for Data Structure and algorithms is: "+dataStructureCourse.get("prerequisites").toString());//Check if there are any prerequisites for the "Data Structures and Algorithms" course.
                                        }
                                    }
                                }

                                if(department.containsKey("professors")){

                                    List<Map<String,Object>> professors = (List<Map<String, Object>>) department.get("professors");

                                    if(!professors.isEmpty()){

                                        for(Map<String,Object>professor : professors){

                                            if(professor.containsKey("courses_taught")){

                                                List<String> coursesTaught = (List<String>) professor.get("courses_taught");
                                                for(String course : coursesTaught){
                                                    if(course.equals(dataStructureCourse.get("code").toString())){
                                                        dataStructureProfessor = new HashMap<>(professor);
                                                    }
                                                }
                                            }
                                        }
                                        System.out.println("Professor that teaches data structe is "+dataStructureProfessor.get("name")); //Extract and print the names of professors who teach the "Data Structures and Algorithms" course.
                                    }
                                }
                            }
                            if(department.containsKey("name") && department.get("name").toString().equals("Electrical Engineering")){
                                if(department.containsKey("courses")){

                                    List<Map<String,Object>> courses = (List<Map<String, Object>>) department.get("courses");

                                    Set<String> courseNames = courses.stream()
                                            .flatMap(course -> course.entrySet().stream())
                                            .filter(course -> course.getKey().equals("name"))
                                            .map(Map.Entry::getValue)
                                            .filter(courseName -> courseName!=null && !courseName.toString().isEmpty())
                                            .map(Object::toString)
                                            .collect(Collectors.toSet());

                                    uniqueCourses.addAll(courseNames);

                                    List<String> stringCredits = courses.stream()
                                            .flatMap(course -> course.entrySet().stream())
                                            .filter(courseKeys -> courseKeys.getKey().equals("credits"))
                                            .map(Map.Entry::getValue)
                                            .filter(Objects::nonNull)
                                            .map(Object::toString)
                                            .collect(Collectors.toList());

                                    double sum = stringCredits.stream()
                                            .mapToDouble(Double::parseDouble)
                                            .sum();

                                    System.out.println("Total credits are: "+(int)sum); // Determine the total number of credits for all courses offered in the Electrical Engineering department
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("The all courses are: ");
            uniqueCourses.forEach(course -> System.out.print(course + " , "));

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
