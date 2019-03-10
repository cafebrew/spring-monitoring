package spring.monitoring.user;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository repository;

  public UserService(UserRepository userRepository) {
    repository = userRepository;
  }

  public Optional<User> queryUser(String userName) {
    return repository.findByUserName(userName);
  }

  public Optional<User> queryUser(Long seq) {
    return repository.findById(seq);
  }

  @Transactional
  public User createUser(User user) {
    return repository.save(user);
  }

  @Transactional
  public User modifyUser(User user, User request) {
    user.setEmail(request.getEmail());
    return repository.save(user);
  }

  @Transactional
  public void deleteUser(String userName) {
    repository.deleteByUserName(userName);
  }

}
