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
public class LoginController {

  private final PostgreSQL api = new PostgreSQL();
  private final String APIaddress = "http://localhost:4200";

  @PostMapping("/clientByEmail")
  @ResponseBody
  @CrossOrigin(origins = APIaddress)
  public Client getClientByEmail(@RequestBody String email) {
    return Login.getClientByEmail(email, api);
  }

  @PostMapping("/login")
  @ResponseBody
  @CrossOrigin(origins = APIaddress)
  public boolean login(@RequestBody ObjectNode json) {
    return Login.login(json.get("email").asText(), json.get("password").asText(), api);
  }

  @PostMapping("/register")
  @ResponseBody
  @CrossOrigin(origins = APIaddress)
  public boolean register(@RequestBody ObjectNode json) {
    int id = Login.getFreeClientId(api) + 1;
    String name = json.get("name").asText();
    String surname = json.get("surname").asText();
    String number = json.get("number").asText();
    String email = json.get("email").asText();
    String hash = SHA512.hash(json.get("password").asText());
    Client client = new Client(id, name, surname, number, email, hash);
    return Login.register(client, api);
  }

  @PostMapping("/checkAvailability")
  @CrossOrigin(origins = APIaddress)
  @ResponseBody
  public boolean checkMaterialsAvailability(@RequestBody ObjectNode json) {
    int id = Login.getProductId(json.get("name").asText(), api);
    boolean materialAvailability = Login.checkMaterialsAvailability(id, api);
    System.out.println(String.format("Poduct availability: %b", materialAvailability));
    return  materialAvailability;
  }

  @PostMapping("/checkSchedule")
  @CrossOrigin(origins = APIaddress)
  @ResponseBody
  public String getLastHourOfTasks(@RequestBody ObjectNode json) {
    int id = Login.getProductId(json.get("name").asText(), api);
    List<JSONObject> neededProfessionsTime = Login.getNeededProfessions(id, api);
    List<JSONObject> ListOfTimestampsAndEmployees = Login.getLastHourOfTasks(neededProfessionsTime, api);
    String lastTimestamp = ListOfTimestampsAndEmployees.get(ListOfTimestampsAndEmployees.size()-1).get("timestamp").toString();
    System.out.println(String.format("==== Delivery time: %s ====", lastTimestamp));
    return String.format("{\"date\":\"%s\"}", lastTimestamp);
  }

  @PostMapping("/setSchedule")
  @CrossOrigin(origins = APIaddress)
  @ResponseBody
  public void setLastHourOfTasks(@RequestBody ObjectNode json) {
    int id = Login.getProductId(json.get("name").asText(), api);
    List<JSONObject> neededProfessionsTime = Login.getNeededProfessions(id, api);
    List<JSONObject> ListOfTimestampsAndEmployees = Login.getLastHourOfTasks(neededProfessionsTime, api);
    Login.setHoursForEmployees(ListOfTimestampsAndEmployees, api);
    System.out.println(String.format("==== order successful ===="));
  }
}