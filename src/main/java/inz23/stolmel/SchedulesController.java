package com.example.application;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;
import org.json.*;

@RestController
public class SchedulesController {

  private final PostgreSQL api = new PostgreSQL();
  private final String APIaddress = "http://localhost:4200";

  @PostMapping("/checkAvailability")
  @CrossOrigin(origins = APIaddress)
  @ResponseBody
  public boolean checkMaterialsAvailability(@RequestBody ObjectNode json) {
    int id = ProductManager.getProductId(json.get("name").asText(), api);
    boolean materialAvailability = Schedules.checkMaterialsAvailability(id, api);
    System.out.println(String.format("Poduct availability: %b", materialAvailability));
    return  materialAvailability;
  }

  @PostMapping("/checkSchedule")
  @CrossOrigin(origins = APIaddress)
  @ResponseBody
  public String getLastHourOfTasks(@RequestBody ObjectNode json) {
    int id = ProductManager.getProductId(json.get("name").asText(), api);
    List<JSONObject> neededProfessionsTime = ProductManager.getNeededProfessions(id, api);
    List<JSONObject> ListOfTimestampsAndEmployees = Schedules.getLastHourOfTasks(neededProfessionsTime, api);
    String lastTimestamp = ListOfTimestampsAndEmployees.get(ListOfTimestampsAndEmployees.size()-1).get("timestamp").toString();
    System.out.println(String.format("==== Delivery time: %s ====", lastTimestamp));
    return String.format("{\"date\":\"%s\"}", lastTimestamp);
  }

  @PostMapping("/setSchedule")
  @CrossOrigin(origins = APIaddress)
  @ResponseBody
  public void setLastHourOfTasks(@RequestBody ObjectNode json) {
    int id = ProductManager.getProductId(json.get("name").asText(), api);
    List<JSONObject> neededProfessionsTime = ProductManager.getNeededProfessions(id, api);
    List<JSONObject> ListOfTimestampsAndEmployees = Schedules.getLastHourOfTasks(neededProfessionsTime, api);
    Schedules.setHoursForEmployees(ListOfTimestampsAndEmployees, api);
    System.out.println(String.format("==== order successful ===="));
  }
}