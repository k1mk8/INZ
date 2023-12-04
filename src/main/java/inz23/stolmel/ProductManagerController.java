package com.example.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.websocket.Decoder.Binary;

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

  @PostMapping("/addProduct")
  @CrossOrigin(origins = APIaddress)
  @ResponseBody
  public boolean addProduct(@RequestBody ObjectNode json) {
    String productName = json.get("name").asText();
    String productDimension = json.get("dimension").asText();
    String productType = json.get("type").asText();
    Integer productPrice = json.get("price").asInt();
    String productDescription = json.get("description").asText();
    String productImage = json.get("image").asText();
    // cut off the data:image/png;base64, from the string
    productImage = productImage.substring(23);
    Integer freeId = ProductManager.getFreeProductId(postgreSQL);
    return ProductManager.addProduct(freeId, productName, productDimension, 
      productType, productPrice, productDescription, productImage, postgreSQL);
  }

  @PostMapping("/manageProductStatus")
  @CrossOrigin(origins = APIaddress)
  @ResponseBody
  public void manageProductStatus(@RequestBody ObjectNode json) {
    String productName = json.get("name").asText();
    boolean newStatus = json.get("newStatus").asBoolean();
    Integer id = ProductManager.getProductId(productName, postgreSQL);
    ProductManager.manageProductStatus(id, newStatus, postgreSQL);
  }

  @GetMapping("/getProducts")
  @CrossOrigin(origins = APIaddress)
  @ResponseBody
  public String getProducts() {
    return ProductManager.getProducts(postgreSQL).toString();
  }
}