package spring.monitoring.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spring.monitoring.model.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

  Optional<Account> findByUsername(String username);
}
