package org.example.testhttpurlconnection.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.example.testhttpurlconnection.utils.StreamUtils.readStream;

@Controller
public class HttpUrlController {
    // Getall
    @GetMapping("/get-all")
    public String HttpUrlGetAll() throws IOException {
        var url = "https://sof306201-d6872-default-rtdb.asia-southeast1.firebasedatabase.app/students.json";
        var connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        connection.setRequestMethod("GET");
        if(connection.getResponseCode() == 200) {
            String data = readStream(connection.getInputStream());
            System.out.println(data);
        }
        connection.disconnect();
        return "index";
    }
    @GetMapping("/get-by-key")
    public String HttpUrlGetByKey() throws IOException {
        var url = "https://sof306201-245eb-default-rtdb.asia-southeast1.firebasedatabase.app/students/-OeQ0OKY39fHTWUpyCNs.json";
        var connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        connection.setRequestMethod("GET");
        if(connection.getResponseCode() == 200) {
            String data = readStream(connection.getInputStream());
            System.out.println(data);
        }
        connection.disconnect();
        return "index";
    }
    @GetMapping("/create")
    public String HttpUrlPost() throws IOException {
        var url = "https://sof306201-245eb-default-rtdb.asia-southeast1.firebasedatabase.app/students.json";
        var connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        var postData = """ 
                {"id":"SV06","name":"Sinh viên 06","mark":8.5,"gender":true}
                """;

        connection.getOutputStream().write(postData.getBytes());
        if(connection.getResponseCode() == 200) {
            String data = readStream(connection.getInputStream());
            System.out.println(data);
        }
        connection.disconnect();
        return "index";
    }
    // PUT - Cập nhật dữ liệu
    @GetMapping("/put")
    public String HttpUrlPut() throws IOException {
        var url = "https://sof306201-245eb-default-rtdb.asia-southeast1.firebasedatabase.app/students/-Oez9SFIXM0FJUyhihi6.json";
        var connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        connection.setRequestMethod("PUT"); // SỬA: DELETE -> PUT
        connection.setDoOutput(true); // THÊM: Cho phép ghi dữ liệu

        var putData = """ 
                {"id":"SV07","name":"Sinh viên 07","mark":8.0,"gender":false}
                """;

        connection.getOutputStream().write(putData.getBytes());
        if(connection.getResponseCode() == 200) {
            String data = readStream(connection.getInputStream());
            System.out.println(data);
        }
        connection.disconnect();
        return "index";
    }
    // DELETE - Xóa dữ liệu
    @GetMapping("/delete")
    public String HttpUrlDelete() throws IOException {
        var url = "https://sof306201-245eb-default-rtdb.asia-southeast1.firebasedatabase.app/students/-Oez9SFIXM0FJUyhihi6.json";
        var connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        connection.setRequestMethod("DELETE"); // SỬA: POST -> DELETE
        if(connection.getResponseCode() == 200) {
            String data = readStream(connection.getInputStream());
            System.out.println(data);
        }
        connection.disconnect();
        return "index";
    }
}