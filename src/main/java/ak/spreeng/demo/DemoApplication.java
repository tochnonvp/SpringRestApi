package ak.spreeng.demo;

import Model.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DemoApplication {


        public static void main(String[] args) {

            RestTemplate restTemplate = new RestTemplate();
            String baseUrl = "http://94.198.50.185:7081/api/users";
            StringBuilder responses = new StringBuilder();

            // Получение списка всех пользователей
            ResponseEntity<String> response1 = restTemplate.getForEntity(baseUrl, String.class);
            HttpHeaders headers1 = response1.getHeaders();
            String sessionID = headers1.getFirst("Set-Cookie");
            responses.append(response1.getBody());

            // Создание нового пользователя
            User user = new User(3L, "James", "Brown", (byte) 35);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Cookie", sessionID);
            HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);
            ResponseEntity<String> response2 = restTemplate.postForEntity(baseUrl, requestEntity, String.class);
            responses.append(response2.getBody());

            // Изменение пользователя с id = 3
            user.setName("Thomas");
            user.setLastName("Shelby");
            HttpEntity<User> requestEntity2 = new HttpEntity<>(user, headers);
            ResponseEntity<String> response3 = restTemplate.exchange(baseUrl, HttpMethod.PUT, requestEntity2, String.class);
            responses.append(response3.getBody());

            // Удаление пользователя с id = 3
            ResponseEntity<String> response4 = restTemplate.exchange(baseUrl + "/3", HttpMethod.DELETE, new HttpEntity<>(headers), String.class);
            responses.append(response4.getBody());

            System.out.println(responses);
        }
    }
