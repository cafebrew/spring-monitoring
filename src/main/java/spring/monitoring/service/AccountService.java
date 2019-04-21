package spring.monitoring.service;

import java.util.Optional;
import javax.transaction.Transactional;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.monitoring.NotFoundException;
import spring.monitoring.model.Account;
import spring.monitoring.repository.AccountRepository;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository repository;

  private final Counter userJoinCounter;

  @Timed("timed-detail")
  public Optional<Account> detail(String username) {
    return repository.findByUsername(username);
  }

  @Timed("timed-detail")
  public Optional<Account> detail(Long id) {
    return repository.findById(id);
  }

  @Timed("timed-create")
  @Transactional
  public Account create(Account account) {
    userJoinCounter.increment();
    account.setRoles("ROLE_USER");
    return repository.save(account);
  }

  @Timed("timed-modify")
  @Transactional
  public Account modify(Long id, Account request) {
    Account account = repository.findById(id).orElseThrow(NotFoundException::new);
    account.setEmail(request.getEmail());
    return repository.save(account);
  }

  @Timed("timed-remove")
  @Transactional
  public void remove(Long id) {
    repository.deleteById(id);
  }

}
