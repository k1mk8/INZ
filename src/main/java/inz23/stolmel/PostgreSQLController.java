package com.example.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;
import org.json.*;

@RestController
public class PostgreSQLController {

  private final PostgreSQL api = new PostgreSQL();
  private final String APIaddress = "http://localhost:4200";

  @PostMapping("/clientByEmail")
  @ResponseBody
  @CrossOrigin(origins = APIaddress)
  public Client getClientByEmail(@RequestBody String email) {
    return api.getClientByEmail(email);
  }

  @PostMapping("/login")
  @ResponseBody
  @CrossOrigin(origins = APIaddress)
  public boolean login(@RequestBody ObjectNode json) {
    return api.login(json.get("email").asText(), json.get("password").asText());
  }

  @PostMapping("/register")
  @ResponseBody
  @CrossOrigin(origins = APIaddress)
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

  @PostMapping("/checkAvailability")
  @CrossOrigin(origins = APIaddress)
  @ResponseBody
  public boolean checkMaterialsAvailability(@RequestBody ObjectNode json) {
    int id = api.getProductId(json.get("name").asText());
    boolean materialAvailability = api.checkMaterialsAvailability(id);
    System.out.println(String.format("Poduct availability: %b", materialAvailability));
    return materialAvailability;
  }

  @PostMapping("/checkSchedule")
  @CrossOrigin(origins = APIaddress)
  @ResponseBody
  public String getLastHourOfTasks(@RequestBody ObjectNode json) {
    List<JSONObject> neededProfessionsTime = api.getNeededProfessions(Integer.valueOf(json.get("id").asText()));
    List<JSONObject> ListOfTimestampsAndEmployees = api.getLastHourOfTasks(neededProfessionsTime);
    //System.out.println(String.format("==== wynik: %d ====", id));
    return ListOfTimestampsAndEmployees.get(ListOfTimestampsAndEmployees.size()-1).get("timestamp").toString();
  }

  @PostMapping("/setSchedule")
  @CrossOrigin(origins = APIaddress)
  @ResponseBody
  public void setLastHourOfTasks(@RequestBody ObjectNode json) {
    List<JSONObject> neededProfessionsTime = api.getNeededProfessions(Integer.valueOf(json.get("id").asText()));
    List<JSONObject> ListOfTimestampsAndEmployees = api.getLastHourOfTasks(neededProfessionsTime);
    api.setHoursForEmployees(ListOfTimestampsAndEmployees);
    System.out.println(String.format("==== order successful ===="));
  }
}