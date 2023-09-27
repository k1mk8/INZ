package com.example.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

  @PostMapping("/getProductsFromOrder")
  @CrossOrigin(origins = APIaddress)
  @ResponseBody
  public String getProductsFromOrder(@RequestBody ObjectNode json) {
    JSONArray orderProductsIds = api.getProductIdsFromOrder(json.get("order_id").asInt());
    JSONArray orderProducts = new JSONArray();
    for(int it = 0; it < orderProductsIds.length(); it++) {
      Integer product_id = orderProductsIds.getJSONObject(it).getInt("product_id");
      Product product = api.getProductFromId(product_id);
      orderProducts.put(product);
    }
    return orderProducts.toString().replace("\\\"", "\"").replace("\"{", "{").replace("}\"", "}");
  }

  @PostMapping("/getBasketOfClient")
  @CrossOrigin(origins = APIaddress)
  @ResponseBody
  public String getBasketOfClient(@RequestBody ObjectNode json) {
    Client client = PostgreSQL.getClientByEmail(json.get("email").asText());
    Integer id = client.getId();
    JSONObject clientBasket = api.getBasketOfClient(id);
    return clientBasket.toString();
  }

  @PostMapping("/addToBasket")
  @ResponseBody
  @CrossOrigin(origins = APIaddress)
  public void addToBasket(@RequestBody ObjectNode json) {
    Client client = PostgreSQL.getClientByEmail(json.get("email").asText());
    JSONObject clientBasket = api.getBasketOfClient(client.getId());
    Integer productId = PostgreSQL.getProductId(json.get("name").asText());
    api.addToBasket(clientBasket.getInt("id"), productId);
  }
}