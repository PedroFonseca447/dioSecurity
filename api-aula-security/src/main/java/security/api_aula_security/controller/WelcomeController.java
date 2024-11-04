package security.api_aula_security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    @GetMapping
    public String welcome(){ //página inicial
        return "Welcome to My Spring Boot Web API";
    }
    @GetMapping("/users") //rota de users
    public String users() {
        return "Authorized user";
    }
    @GetMapping("/managers") //rota para managers
    public String managers() {
        return "Authorized manager";
    }
}