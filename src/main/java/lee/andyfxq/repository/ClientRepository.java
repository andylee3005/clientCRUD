package lee.andyfxq.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import lee.andyfxq.model.Client;

public interface ClientRepository extends MongoRepository<Client, String> {

		List<Client> findByFnAndLn(String fn, String ln);
		List<Client> findByEmail(String email);
		List<Client> findByAddress(String address);
		List<Client> findByPhone(String phone);
		
}
