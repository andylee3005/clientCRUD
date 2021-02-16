package lee.andyfxq.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lee.andyfxq.model.Client;
import lee.andyfxq.repository.ClientRepository;
import lee.andyfxq.service.ClientService;

@Service("clientService")
public class ClientServiceImpl implements ClientService {

	@Autowired
	ClientRepository clientRepository;
	
	private Comparator<Client> comparatorKey() {
		Comparator<Client> orderByKey = Comparator.comparing(Client::getUpdated).reversed()
				.thenComparing(Client::getLn)
				.thenComparing(Client::getFn)
				.thenComparing(Client::getAddress);
		return orderByKey;
	}
	
	@Override
	public List<Client> getAll() {
		return clientRepository.findAll().stream().sorted(comparatorKey()).collect(Collectors.toList());
	}

	@Override
	public List<Client> getByName(String fn, String ln) {
		return clientRepository.findByFnAndLn(fn, ln).stream().sorted(comparatorKey()).collect(Collectors.toList());
	}

	@Override
	public List<Client> getByEmail(String email) {
		return clientRepository.findByEmail(email).stream().sorted(comparatorKey()).collect(Collectors.toList());
	}

	@Override
	public List<Client> getByAddress(String address) {
		return clientRepository.findByAddress(address).stream().sorted(comparatorKey()).collect(Collectors.toList());	}

	@Override
	public List<Client> getByPhone(String phone) {
		return clientRepository.findByPhone(phone).stream().sorted(comparatorKey()).collect(Collectors.toList());
	}

	@Override
	public Optional<Client> getById(String id) {
		return clientRepository.findById(id);
	}

	@Override
	public Client _save(Client client) {
		return clientRepository.save(client);
	}

	@Override
	public void removeById(String id) {
		clientRepository.deleteById(id);
	}

}
