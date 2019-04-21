package spring.monitoring.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import spring.monitoring.NotFoundException;
import spring.monitoring.model.User;
import spring.monitoring.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService service;

  @GetMapping
  public ResponseEntity<User> detail(@RequestParam(name = "username") String username) {
    return service.detail(username)
      .map(ResponseEntity::ok)
      .orElseThrow(NotFoundException::new);
  }

  @PostMapping
  public ResponseEntity<User> create(@RequestBody User request) {
    User user = service.create(request);
    return ResponseEntity.created(
      ServletUriComponentsBuilder.fromCurrentRequest()
        .path("{id}")
        .build(user.getId())
    ).body(user);
  }

  @PutMapping("/{id}")
  public ResponseEntity<User> modify(@PathVariable Long id, @RequestBody User request) {
    return ResponseEntity.created(
      ServletUriComponentsBuilder.fromCurrentRequestUri().build(id)
    ).body(service.modify(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable Long id) {
    service.remove(id);
    return ResponseEntity.noContent().build();
  }

}
