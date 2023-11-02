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
public class ProductManagerController {

  private final PostgreSQL postgreSQL = new PostgreSQL();
  private final String APIaddress = "http://localhost:4200";

  @PostMapping("/getProductDetails")
  @CrossOrigin(origins = APIaddress)
  @ResponseBody
  public String getProductDetails(@RequestBody ObjectNode json) {
    String productName = json.get("productName").asText();
    Integer id = ProductManager.getProductId(productName, postgreSQL);
    JSONObject productDetails = ProductManager.getProductDetails(id, postgreSQL);
    return productDetails.toString();
  }

}