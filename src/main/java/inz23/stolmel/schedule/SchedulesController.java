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

}