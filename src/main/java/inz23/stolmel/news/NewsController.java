package inz23.stolmel.news;

import inz23.stolmel.postgreSQL.*;
import inz23.stolmel.news.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.json.*;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class NewsController {

  private final PostgreSQL postgreSQL;

  @Autowired
  public NewsController(PostgreSQL postgreSQL) {
    this.postgreSQL = postgreSQL;
  }

  @GetMapping(value = "/news", produces = MediaType.APPLICATION_JSON_VALUE)
  public String getNews() {
      JSONArray arr = News.getNews(postgreSQL);
      return arr.toString();
  }

  @GetMapping(value = "/news/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> getNewsById(@PathVariable("id") int id) {
      JSONObject obj = News.getNewsById(postgreSQL, id);
      if (obj == null) {
          JSONObject error = new JSONObject();
          error.put("error", "Not found");
          return ResponseEntity.status(404).body(error.toString());
      }
      return ResponseEntity.ok(obj.toString());
  }
}
