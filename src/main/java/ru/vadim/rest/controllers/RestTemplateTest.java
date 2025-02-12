package ru.vadim.rest.controllers;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.vadim.rest.models.User;

import java.util.List;

public class RestTemplateTest {
    private static final String BASE_URL = "http://94.198.50.185:7081/api/users";
    private static String sessionId;
    private static final RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        getUsers();
        addUser();
        updateUser();
        deleteUser();
    }

    private static void getUsers() {
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL, String.class);
        List<String> cookies = response.getHeaders().get("Set-Cookie");
        if (cookies != null && !cookies.isEmpty()) {
            sessionId = cookies.get(0);
        }
        System.out.println("Session ID: " + sessionId);
    }

    private static void addUser() {
        User user = new User(3L, "James", "Brown", (byte) 30);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Cookie", sessionId);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.POST, request, String.class);
        System.out.println(response.getBody());
    }

    private static void updateUser() {
        User user = new User(3L, "Thomas", "Shelby", (byte) 30);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Cookie", sessionId);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.PUT, request, String.class);
        System.out.println(response.getBody());
    }

    private static void deleteUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionId);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/3", HttpMethod.DELETE, request, String.class);
        System.out.println(response.getBody());
    }
}
