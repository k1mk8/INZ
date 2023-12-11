package inz23.stolmel.schedule;

import inz23.stolmel.postgreSQL.*;
import inz23.stolmel.product.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.json.*;

@RestController
public class SchedulesController {

  private final PostgreSQL postgreSQL;

  @Autowired
  public SchedulesController(PostgreSQL postgreSQL) {
    this.postgreSQL = postgreSQL;
  }

  private final String APIaddress = "http://localhost:4200";

  @PostMapping("/checkAvailability")
  @CrossOrigin(origins = APIaddress)
  @ResponseBody
  public boolean checkMaterialsAvailability(@RequestBody ObjectNode json) {
    int id = ProductManager.getProductId(json.get("name").asText(), postgreSQL);
    boolean materialAvailability = Schedules.checkMaterialsAvailability(id, postgreSQL);
    System.out.println(String.format("Poduct availability: %b", materialAvailability));
    return materialAvailability;
  }

  @PostMapping("/checkSchedule")
  @CrossOrigin(origins = APIaddress)
  @ResponseBody
  public String getLastHourOfTasks(@RequestBody ObjectNode json) {
    int id = ProductManager.getProductId(json.get("name").asText(), postgreSQL);
    List<JSONObject> neededProfessionsTime = ProductManager.getNeededProfessions(id, postgreSQL);
    List<JSONObject> ListOfTimestampsAndEmployees = Schedules.getLastHourOfTasks(neededProfessionsTime, postgreSQL);
    String lastTimestamp = ListOfTimestampsAndEmployees.get(ListOfTimestampsAndEmployees.size() - 1).get("timestamp")
        .toString();
    System.out.println(String.format("==== Delivery time: %s ====", lastTimestamp));
    return String.format("{\"date\":\"%s\"}", lastTimestamp);
  }
}