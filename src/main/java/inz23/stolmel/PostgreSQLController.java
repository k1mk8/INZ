package com.example.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class PostgreSQLController {

  private final PostgreSQL api = new PostgreSQL();

  @PostMapping("/clientByEmail")
  @ResponseBody
  @CrossOrigin(origins = "http://localhost:4200")
  public Client getClientByEmail(@RequestBody String email) {
    return api.getClientByEmail(email);
  }

  @PostMapping("/login")
  @ResponseBody
  @CrossOrigin(origins = "http://localhost:4200")
  public boolean login(@RequestBody ObjectNode json) {
    return api.login(json.get("email").asText(), json.get("password").asText());
  }

  @PostMapping("/register")
  @ResponseBody
  @CrossOrigin(origins = "http://localhost:4200")
  public boolean register(@RequestBody ObjectNode json) {
    int id = api.getFreeClientId() + 1;
    String name = json.get("name").asText();
    String surname = json.get("surname").asText();
    String number = json.get("number").asText();
    String email = json.get("email").asText();
    String hash = SHA512.hash(json.get("password").asText());
    Client client = new Client(id, name, surname, number, email, hash);
    return api.register(client);
  }
}