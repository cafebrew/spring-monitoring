package spring.monitoring.service;

import java.util.Optional;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.monitoring.NotFoundException;
import spring.monitoring.model.Feed;
import spring.monitoring.model.User;
import spring.monitoring.repository.FeedRepository;
import spring.monitoring.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class FeedService {

  private final FeedRepository repository;

  private final UserRepository userRepository;

  public Iterable<Feed> list(Long id) {
    return repository.findByOwnerId(id);
  }

  public Optional<Feed> detail(Long id) {
    return repository.findById(id);
  }

  @Transactional
  public Feed create(Long userId, Feed feed) {
    User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
    feed.setOwner(user);
    return repository.save(feed);
  }

  @Transactional
  public Feed modify(Long feedId, Feed request) {
    Feed feed = repository.findById(feedId).orElseThrow(NotFoundException::new);
    feed.setName(request.getName());
    feed.setUrl(request.getUrl());
    return repository.save(feed);
  }

  @Transactional
  public void remove(Long feedId) {
    repository.deleteById(feedId);
  }
}
