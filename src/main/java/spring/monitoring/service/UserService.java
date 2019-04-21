package spring.monitoring.service;

import java.util.Optional;
import javax.transaction.Transactional;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.monitoring.NotFoundException;
import spring.monitoring.model.User;
import spring.monitoring.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;

  private final Counter userJoinCounter;

  @Timed("timed-detail")
  public Optional<User> detail(String username) {
    return repository.findByUsername(username);
  }

  @Timed("timed-create")
  @Transactional
  public User create(User user) {
    userJoinCounter.increment();
    return repository.save(user);
  }

  @Timed("timed-modify")
  @Transactional
  public User modify(Long id, User request) {
    User user = repository.findById(id).orElseThrow(NotFoundException::new);
    user.setEmail(request.getEmail());
    return repository.save(user);
  }

  @Timed("timed-remove")
  @Transactional
  public void remove(Long id) {
    repository.deleteById(id);
  }

}
