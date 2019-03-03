package spring.monitoring.api.user;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {

  private final UserRepository repository;

  public UserService(UserRepository userRepository) {
    repository = userRepository;
  }

  public Optional<User> queryUser(String userId) {
    return repository.findByUserId(userId);
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
    user.setRealname(request.getRealname());
    user.setEmail(request.getEmail());
    return repository.save(user);
  }

  @Transactional
  public void deleteUser(String userId) {
    repository.deleteByUserId(userId);
  }

}
