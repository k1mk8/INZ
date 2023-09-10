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

  // @GetMapping("/clients")
  // public String getAllClients() {
  //   return api.getAllClients();
  // }

  @PostMapping("/clientByEmail")
  @ResponseBody
  public Client getClientByEmail(@RequestBody String email) {
    return api.getClientByEmail(email);
  }

  @PostMapping("/login")
  @ResponseBody
  @CrossOrigin(origins = "http://localhost:4200")
  public boolean login(@RequestBody ObjectNode json) {
    return api.login(json.get("email").asText(), json.get("password").asText());
  }
}