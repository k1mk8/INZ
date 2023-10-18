package com.example.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.*;

@RestController
public class OrderController {

  private final PostgreSQL postgreSQL = new PostgreSQL();
  private final String APIaddress = "http://localhost:4200";

  @PostMapping("/getOrders")
  @CrossOrigin(origins = APIaddress)
  @ResponseBody
  public String getOrdersOfClient(@RequestBody ObjectNode json) {
    Client client = User.getClientByEmail(json.get("email").asText(), postgreSQL);
    Integer id = client.getId();
    JSONArray ordersOfClient = Order.getOrdersOfClient(id, postgreSQL);
    return ordersOfClient.toString();
  }

  @PostMapping("/getProductsFromOrder")
  @CrossOrigin(origins = APIaddress)
  @ResponseBody
  public String getProductsFromOrder(@RequestBody ObjectNode json) {
    JSONArray orderProductsInBucket = Order.getProductIdsFromOrder(json.get("order_id").asInt(), postgreSQL);
    JSONArray orderProducts = new JSONArray();
    for(int it = 0; it < orderProductsInBucket.length(); it++) {
      Integer product_id = orderProductsInBucket.getJSONObject(it).getInt("product_id");
      Integer amount = orderProductsInBucket.getJSONObject(it).getInt("amount");
      Product product = Order.getProductFromId(product_id, postgreSQL);
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
    Client client = User.getClientByEmail(json.get("email").asText(), postgreSQL);
    JSONObject clientBasket = Order.getBasketOfClient(client.getId(), postgreSQL);
    if (!clientBasket.has("id")) {
      return null;
    }
    return clientBasket.toString();
  }

  @PostMapping("/addToBasket")
  @ResponseBody
  @CrossOrigin(origins = APIaddress)
  public void addToBasket(@RequestBody ObjectNode json) {
    Client client = User.getClientByEmail(json.get("email").asText(), postgreSQL);
    JSONObject clientBasket = Order.getBasketOfClient(client.getId(), postgreSQL);
    if (!clientBasket.has("id")) {
      Order.createOrderForClient(client.getId(), postgreSQL);
      clientBasket = Order.getBasketOfClient(client.getId(), postgreSQL);
    }
    Integer productId = ProductManager.getProductId(json.get("name").asText(), postgreSQL);
    Order.addToBasket(clientBasket.getInt("id"), productId, json.get("amount").asInt(), postgreSQL);
  }

  @PostMapping("/removeFromBasket")
  @ResponseBody
  @CrossOrigin(origins = APIaddress)
  public void removeFromBasket(@RequestBody ObjectNode json) {
    Integer id = ProductManager.getProductId(json.get("product_name").asText(), postgreSQL);
    Order.removeFromBasket(json.get("order_id").asInt(), id, postgreSQL);
  }

  @PostMapping("/order")
  @ResponseBody
  @CrossOrigin(origins = APIaddress)
  public void order(@RequestBody ObjectNode json) {
    Integer orderId = json.get("id").asInt();
    JSONArray orderProductsInBucket = Order.getProductIdsFromOrder(orderId, postgreSQL);
    Order.order(orderProductsInBucket, orderId, postgreSQL);
    Order.changeOrderStatus(orderId, postgreSQL);
  }
}