package lee.andyfxq.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lee.andyfxq.model.Client;
import lee.andyfxq.service.ClientService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/CLIENT")
public class ClientController {
	
	@Autowired
	@Qualifier("clientService")
	ClientService clientService;
	
	@GetMapping("/list")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<Client> requestClients() {
		return clientService.getAll();
	}
	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping("/name/{fn}/{ln}")
	public List<Client> requestByName(@PathVariable String fn, @PathVariable String ln) {
		return clientService.getByName(fn, ln);
	}
	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping("/email/{email}")
	public List<Client> requestByEmail(@PathVariable String email) {
		return clientService.getByEmail(email);
	}
	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping("/address/{address}")
	public List<Client> requestByAddress(@PathVariable String address) {
		return clientService.getByAddress(address);
	}
	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping("/phone/{phone}")
	public List<Client> requestByPhone(@PathVariable String phone) {
		return clientService.getByPhone(phone);
	}
	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping("/id/{id}")
	public Optional<Client> requestById(@PathVariable String id) {
		return clientService.getById(id);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<Client> createClient(@RequestBody Client client) {
		try {
			Client _client = clientService._save(new Client(
					client.getFn(), client.getLn(), client.getEmail(),
					client.getAddress(), client.getPhone(), client.getPost(), client.getBirthdate()
					));
			return new ResponseEntity<>(_client, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@PutMapping("/edit/{id}")
	public ResponseEntity<Client> editClient(@PathVariable("id") String id, @RequestBody Client client) {
		Optional<Client> clientData = clientService.getById(id);
		
		if (clientData.isPresent()) {
			Client _client = clientData.get();
			_client.setFn(client.getFn());
			_client.setLn(client.getLn());
			_client.setEmail(client.getEmail());
			_client.setAddress(client.getAddress());
			_client.setPost(client.getPost());
			_client.setPhone(client.getPhone());
			_client.setBirthdate(client.getBirthdate());
			_client.setUpdated(Client.dtf.format(LocalDateTime.now()));
			
			return new ResponseEntity<>(clientService._save(_client), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/edit/{id}")
	public ResponseEntity<HttpStatus> deleteClient(@PathVariable("id") String id) {
		try {
			clientService.removeById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
