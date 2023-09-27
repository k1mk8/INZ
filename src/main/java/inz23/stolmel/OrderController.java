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
    JSONArray orderProductsInBucket = api.getProductIdsFromOrder(json.get("order_id").asInt());
    JSONArray orderProducts = new JSONArray();
    for(int it = 0; it < orderProductsInBucket.length(); it++) {
      Integer product_id = orderProductsInBucket.getJSONObject(it).getInt("product_id");
      Integer amount = orderProductsInBucket.getJSONObject(it).getInt("amount");
      Product product = api.getProductFromId(product_id);
      JSONObject productInBucket = new JSONObject();
      productInBucket.put("id", product_id);
      productInBucket.put("amount", amount);
      productInBucket.put("name", product.getName());
      productInBucket.put("price", product.getPrice());
      orderProducts.put(productInBucket);
    }
    return orderProducts.toString();
  }

  @PostMapping("/getBasketOfClient")
  @CrossOrigin(origins = APIaddress)
  @ResponseBody
  public String getBasketOfClient(@RequestBody ObjectNode json) {
    Client client = PostgreSQL.getClientByEmail(json.get("email").asText());
    JSONObject clientBasket = api.getBasketOfClient(client.getId());
    if (!clientBasket.has("id")) {
      return null;
    }
    return clientBasket.toString();
  }

  @PostMapping("/addToBasket")
  @ResponseBody
  @CrossOrigin(origins = APIaddress)
  public void addToBasket(@RequestBody ObjectNode json) {
    Client client = PostgreSQL.getClientByEmail(json.get("email").asText());
    JSONObject clientBasket = api.getBasketOfClient(client.getId());
    if (!clientBasket.has("id")) {
      api.createOrderForClient(client.getId());
      clientBasket = api.getBasketOfClient(client.getId());
    }
    Integer productId = PostgreSQL.getProductId(json.get("name").asText());
    api.addToBasket(clientBasket.getInt("id"), productId, json.get("amount").asInt());
  }

  @PostMapping("/removeFromBasket")
  @ResponseBody
  @CrossOrigin(origins = APIaddress)
  public void removeFromBasket(@RequestBody ObjectNode json) {
    Integer id = PostgreSQL.getProductId(json.get("product_name").asText());
    api.removeFromBasket(json.get("order_id").asInt(), id);
  }
}