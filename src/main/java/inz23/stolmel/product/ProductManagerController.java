package inz23.stolmel.product;

import inz23.stolmel.postgreSQL.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.*;

@RestController
@Service
public class ProductManagerController {

  private final PostgreSQL postgreSQL;

  @Autowired
  public ProductManagerController(PostgreSQL postgreSQL) {
    this.postgreSQL = postgreSQL;
  }

  private final String APIaddress = "http://localhost:4200";

}