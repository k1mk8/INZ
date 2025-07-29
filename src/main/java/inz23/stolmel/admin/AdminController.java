package inz23.stolmel.admin;

import inz23.stolmel.postgreSQL.PostgreSQL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/admin/news")
public class AdminController {

    private final PostgreSQL db;

    public AdminController(PostgreSQL postgreSQL) {
        this.db = postgreSQL;
    }

    /** GET /news */
    @GetMapping(produces = "application/json")
    public String listAll() {
        JSONArray arr = Admin.getAllNews(db);
        return arr.toString();
    }

    /** POST /news */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public String create(@RequestBody String body) {
        JSONObject input = new JSONObject(body);
        int newId = Admin.createNews(db, input);
        return new JSONObject().put("id", newId).toString();
    }

    /** PUT /news/{id} */
    @PutMapping(path = "/{id}", consumes = "application/json")
    public String update(@PathVariable int id, @RequestBody String body) {
        JSONObject input = new JSONObject(body);
        boolean ok = Admin.updateNews(db, id, input);
        return new JSONObject().put("success", ok).toString();
    }

    /** DELETE /news/{id} */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        boolean ok = Admin.deleteNews(db, id);
        return new JSONObject().put("success", ok).toString();
    }
}
