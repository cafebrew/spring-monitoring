package spring.monitoring.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/user")
public class UserController {

  private final UserService service;

  @Autowired
  public UserController(UserService userService) {
    service = userService;
  }

  @GetMapping("/{userId}")
  @ResponseBody
  public ResponseEntity<User> detailUser(@PathVariable String userId) {
    return service.queryUser(userId)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new UserNotFoundException(userId));
  }

  @PostMapping
  @ResponseBody
  public ResponseEntity<User> createUser(@RequestBody User user) {
    URI uri = MvcUriComponentsBuilder.fromController(getClass())
        .path("/{userId}")
        .buildAndExpand(user.getUserId())
        .toUri();
    return ResponseEntity.created(uri).body(service.createUser(user));
  }

  @PutMapping("/{seq}")
  @ResponseBody
  public ResponseEntity<User> modifyUser(@PathVariable Long seq, @RequestBody User user) {
    return service.queryUser(seq)
        .map(u -> ResponseEntity.created(URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toString()))
            .body(service.modifyUser(u, user)))
        .orElseThrow(UserNotFoundException::new);
  }

  @DeleteMapping("/{userId}")
  @ResponseBody
  public ResponseEntity<String> deleteUser(@PathVariable String userId) {
    service.deleteUser(userId);
    return ResponseEntity.noContent().build();
  }
}
