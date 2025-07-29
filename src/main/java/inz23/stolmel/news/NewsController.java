package inz23.stolmel.news;

import inz23.stolmel.postgreSQL.*;
import inz23.stolmel.news.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.json.*;

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
}