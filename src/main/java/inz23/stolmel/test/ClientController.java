import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class ClientController {

  private final ClientRepository clientRepository;

  public ClientController(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @GetMapping("/employees")
  public Iterable<Client> findAllEmployees() {
    return this.clientRepository.findAll();
  }

  @PostMapping("/employees")
  public Client addOneEmployee(@RequestBody Client client) {
    return this.clientRepository.save(client);
  }

}
