package ak.spreeng.demo;

import Model.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DemoApplication {

    public static RestTemplate restTemplate = new RestTemplate();
    public static String baseUrl = "http://94.198.50.185:7081/api/users";
    public static StringBuilder responses = new StringBuilder();
    public static String sessionID;
    public static User user = new User(3L, "James", "Brown", (byte) 35);
    public static HttpHeaders headers = new HttpHeaders();

    public static void getAllUsers() {
        ResponseEntity<String> response1 = restTemplate.getForEntity(baseUrl, String.class);
        HttpHeaders headers1 = response1.getHeaders();
        sessionID = headers1.getFirst("Set-Cookie");
        responses.append(response1.getBody());
    }

    public static void addUser() {
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", sessionID);
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);
        ResponseEntity<String> response2 = restTemplate.postForEntity(baseUrl, requestEntity, String.class);
        responses.append(response2.getBody());
    }

    public static void updateUser() {
        user.setName("Thomas");
        user.setLastName("Shelby");
        HttpEntity<User> requestEntity2 = new HttpEntity<>(user, headers);
        ResponseEntity<String> response3 = restTemplate.exchange(baseUrl, HttpMethod.PUT, requestEntity2, String.class);
        responses.append(response3.getBody());
    }

    public static void deleteUser() {
        ResponseEntity<String> response4 = restTemplate.exchange(baseUrl + "/3", HttpMethod.DELETE, new HttpEntity<>(headers), String.class);
        responses.append(response4.getBody());
    }

    public static void main(String[] args) {
        getAllUsers();
        addUser();
        updateUser();
        deleteUser();
        System.out.println(responses);

        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "http://94.198.50.185:7081/api/users";
        StringBuilder responses = new StringBuilder();


        ResponseEntity<String> response1 = restTemplate.getForEntity(baseUrl, String.class);
        HttpHeaders headers1 = response1.getHeaders();
        String sessionID = headers1.getFirst("Set-Cookie");
        responses.append(response1.getBody());


        User user = new User(3L, "James", "Brown", (byte) 35);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", sessionID);
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);
        ResponseEntity<String> response2 = restTemplate.postForEntity(baseUrl, requestEntity, String.class);
        responses.append(response2.getBody());


        user.setName("Thomas");
        user.setLastName("Shelby");
        HttpEntity<User> requestEntity2 = new HttpEntity<>(user, headers);
        ResponseEntity<String> response3 = restTemplate.exchange(baseUrl, HttpMethod.PUT, requestEntity2, String.class);
        responses.append(response3.getBody());


        ResponseEntity<String> response4 = restTemplate.exchange(baseUrl + "/3", HttpMethod.DELETE, new HttpEntity<>(headers), String.class);
        responses.append(response4.getBody());


        System.out.println(responses);
    }
}
