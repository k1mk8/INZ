package inz23.stolmel.order;

import inz23.stolmel.postgreSQL.*;
import inz23.stolmel.user.*;
import inz23.stolmel.product.*;
import inz23.stolmel.dataTypeClasses.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.json.*;

@RestController
public class OrderController {

  private final PostgreSQL postgreSQL;

  @Autowired
  public OrderController(PostgreSQL postgreSQL) {
    this.postgreSQL = postgreSQL;
  }

}