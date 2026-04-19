package org.epam.utils;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class PayLoad {

    public static String addUser() {
        return "{\r\n"
                + "    \"name\": \"name\",\r\n"
                + "    \"email\": \"Mahadev" + (int) (Math.random() * 100) + "@test\",\r\n"
                + "    \"gender\": \"male\",\r\n"
                + "    \"status\": \"active\"\r\n"
                + "}";
    }

    public static String gender() {
        return "{\r\n"
                + "    \"name\": \"Mahadeva_R\"\r\n"
                + "}";
    }

    public static Map<String, String> addUserUsingHashmap() {
        Map<String, String> data = new HashMap<String, String>();
        data.put("name", "Mahadev");
        data.put("email", "Mahadev" + (int) (Math.random() * 1000) + "@test");
        data.put("status", "active");
        data.put("gender", "male");

        return data;
    }

    public static JSONObject addUserUsingJsonObject() {
        JSONObject data = new JSONObject();
        data.put("name", "Mahadev");
        data.put("email", "Mahadev" + (int) (Math.random() * 1000) + "@test");
        data.put("status", "active");
        data.put("gender", "male");

        return data;
    }

    public static Map<String, String> updateUserUsingHashmap() {
        Map<String, String> data = new HashMap<String, String>();
        data.put("name", "Mahadev_Madhu");
        data.put("status", "inactive");

        return data;
    }

    public static JSONObject updateUserUsingJsonObject() {
        JSONObject data = new JSONObject();
        data.put("name", "Mahadev_Madhu");
        data.put("status", "inactive");

        return data;
    }

    public static UserDataPOJO addUserUsingPOJO() {
        UserDataPOJO data = new UserDataPOJO();
        data.setName("scott");
        data.setEmail("Scott" + (int) (Math.random() * 1000) + "@test");
        data.setGender("female");
        data.setStatus("inactive");

        return data;
    }

    public static UserDataPOJO updateUserUsingPOJO() {
        UserDataPOJO data = new UserDataPOJO();
        data.setName("scott123");
        data.setGender("male");
        data.setStatus("active");

        return data;
    }

    public static JSONObject addUserUsingExternalJsonFile() throws FileNotFoundException {
        File file = new File("./src/test/resources/addUserData.json");
        System.out.println("********* File Name - "+file+"  *********");
        FileReader fileReader = new FileReader(file);
        JSONTokener jt = new JSONTokener(fileReader);
        JSONObject data = new JSONObject(jt);

        return data;
    }

    public static JSONObject updateUserUsingExternalJsonFile() throws FileNotFoundException {
        File file = new File("./src/test/resources/updateUserData.json");
        System.out.println("********* File Name - "+file+"  *********");
        FileReader fileReader = new FileReader(file);
        JSONTokener jt = new JSONTokener(fileReader);
        JSONObject data = new JSONObject(jt);

        return data;
    }
}
