package spring.monitoring.api;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import spring.monitoring.NotFoundException;
import spring.monitoring.model.Feed;
import spring.monitoring.service.FeedService;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class FeedController {

  private final FeedService service;

  @GetMapping("/{userId}/feed")
  public ResponseEntity<Iterable<Feed>> list(@PathVariable Long userId) {
    return ResponseEntity.ok(service.list(userId));
  }

  @GetMapping("/feed/{feedId}")
  public ResponseEntity<Feed> detail(@PathVariable Long feedId) {
    return service.detail(feedId)
      .map(ResponseEntity::ok)
      .orElseThrow(NotFoundException::new);
  }

  @PostMapping("/{userId}/feed")
  public ResponseEntity<Feed> create(@PathVariable Long userId, @RequestBody Feed request) {
    Feed feed = service.create(userId, request);
    return ResponseEntity.created(
      MvcUriComponentsBuilder.fromController(getClass())
        .path("/feed/{feedId}")
        .build(feed.getId())
    ).body(feed);
  }

  @PostMapping("/feed/{feedId}")
  public ResponseEntity<Feed> modify(@PathVariable Long feedId, @RequestBody Feed request) {
    return ResponseEntity.created(
      ServletUriComponentsBuilder.fromCurrentRequestUri().build(feedId)
    ).body(service.modify(feedId, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable Long id) {
    service.remove(id);
    return ResponseEntity.noContent().build();
  }
}
