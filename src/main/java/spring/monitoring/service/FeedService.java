package spring.monitoring.service;

import java.util.Optional;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.monitoring.NotFoundException;
import spring.monitoring.model.Account;
import spring.monitoring.model.Feed;
import spring.monitoring.repository.AccountRepository;
import spring.monitoring.repository.FeedRepository;

@Service
@RequiredArgsConstructor
public class FeedService {

  private final FeedRepository repository;

  private final AccountRepository accountRepository;

  public Iterable<Feed> list(String username) {
    return repository.findByOwnerUsername(username);
  }

  public Optional<Feed> detail(Long id) {
    return repository.findById(id);
  }

  @Transactional
  public Feed create(String username, Feed feed) {
    Account account = accountRepository.findByUsername(username).orElseThrow(NotFoundException::new);
    feed.setOwner(account);
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
