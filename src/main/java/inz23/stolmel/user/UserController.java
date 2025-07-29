package inz23.stolmel.user;

import inz23.stolmel.postgreSQL.*;
import inz23.stolmel.dataTypeClasses.*;
import inz23.stolmel.sha.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class UserController {

  private final PostgreSQL postgreSQL;

  @Autowired
  public UserController(PostgreSQL postgreSQL) {
    this.postgreSQL = postgreSQL;
  }

  private final String APIaddress = "http://localhost:4200";

  @PostMapping("/login")
  @ResponseBody
  @CrossOrigin(origins = APIaddress)
  public boolean login(@RequestBody ObjectNode json) {
    return User.login(json.get("email").asText(), json.get("password").asText(), postgreSQL);
  }

}