package spring.monitoring.user;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/user")
public class UserController {

  private final UserService service;

  @Autowired
  public UserController(UserService userService) {
    service = userService;
  }

  @GetMapping("/{userName}")
  @ResponseBody
  public ResponseEntity<User> detailUser(@PathVariable String userName) {
    return service.queryUser(userName)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new UserNotFoundException(userName));
  }

  @PostMapping
  @ResponseBody
  public ResponseEntity<User> createUser(@RequestBody User user) {
    URI uri = MvcUriComponentsBuilder.fromController(getClass())
        .path("/{userId}")
        .buildAndExpand(user.getUserName())
        .toUri();
    return ResponseEntity.created(uri).body(service.createUser(user));
  }

  @PutMapping("/{seq}")
  @ResponseBody
  public ResponseEntity<User> modifyUser(@PathVariable Long seq, @RequestBody User user) {
    return service.queryUser(seq)
        .map(u -> ResponseEntity
            .created(URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toString()))
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
