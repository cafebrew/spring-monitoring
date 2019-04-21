package spring.monitoring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spring.monitoring.model.Feed;

@Repository
public interface FeedRepository extends CrudRepository<Feed, Long> {

  Iterable<Feed> findByOwnerUsername(String username);
}
