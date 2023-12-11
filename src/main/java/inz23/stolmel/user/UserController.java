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

  @PostMapping("/clientByEmail")
  @ResponseBody
  @CrossOrigin(origins = APIaddress)
  public Client getClientByEmail(@RequestBody String email) {
    return User.getClientByEmail(email, postgreSQL);
  }

  @PostMapping("/login")
  @ResponseBody
  @CrossOrigin(origins = APIaddress)
  public int login(@RequestBody ObjectNode json) {
    return User.login(json.get("email").asText(), json.get("password").asText(), postgreSQL);
  }

  @PostMapping("/register")
  @ResponseBody
  @CrossOrigin(origins = APIaddress)
  public boolean register(@RequestBody ObjectNode json) {
    int id = User.getFreeClientId(postgreSQL) + 1;
    String name = json.get("name").asText();
    String surname = json.get("surname").asText();
    String number = json.get("number").asText();
    String email = json.get("email").asText();
    String hash = SHA512.hash(json.get("password").asText());
    boolean isAdmin = false;
    Client client = new Client(id, name, surname, number, email, hash, isAdmin);
    // check if user already exists
    if (User.getClientByEmail(client.getEmail(), postgreSQL) != null)
      return false;
    return User.register(client, postgreSQL);
  }

}