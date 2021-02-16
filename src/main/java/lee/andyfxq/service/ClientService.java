package lee.andyfxq.service;

import java.util.List;
import java.util.Optional;

import lee.andyfxq.model.Client;

public interface ClientService {
	
	List<Client> getAll();
	Optional<Client> getById(String id);
	List<Client> getByName(String fn, String ln);
	List<Client> getByEmail(String email);
	List<Client> getByAddress(String address);
	List<Client> getByPhone(String phone);
	Client _save(Client client);
	void removeById(String id);
}
