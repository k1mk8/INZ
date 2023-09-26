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
import java.util.ArrayList;
import org.json.*;

@RestController
public class OrderController {

  private final Order api = new Order();
  private final String APIaddress = "http://localhost:4200";

  @PostMapping("/getOrders")
  @CrossOrigin(origins = APIaddress)
  @ResponseBody
  public String getOrdersOfClient(@RequestBody ObjectNode json) {
    Client client = PostgreSQL.getClientByEmail(json.get("email").asText());
    Integer id = client.getId();
    JSONArray ordersOfClient = api.getOrdersOfClient(id);
    return ordersOfClient.toString();
  }
}