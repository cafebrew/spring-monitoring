package spring.monitoring.api;

import java.security.Principal;
import java.util.List;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import spring.monitoring.NotFoundException;
import spring.monitoring.model.Account;
import spring.monitoring.service.AccountService;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {


  private final AccountService service;

  private final Counter counter;

  public AccountController(AccountService service, MeterRegistry meterRegistry) {
    this.service = service;
    counter = Counter.builder("account-counter")
      .description("indicate new account count")
      .tags(List.of(Tag.of("module", "account")))
      .register(meterRegistry);
  }

  @GetMapping
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<Account> detail(Principal principal) {
    return service.detail(principal.getName())
      .map(ResponseEntity::ok)
      .orElseThrow(NotFoundException::new);
  }

  @PostMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<Account> create(@RequestBody Account request) {
    var account = service.create(request);
    counter.increment();
    return ResponseEntity.created(
      MvcUriComponentsBuilder.fromController(getClass())
        .path("/account/{id}")
        .build(account.getId())
    ).body(account);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<Account> modify(@PathVariable Long id, @RequestBody Account request) {
    return ResponseEntity.created(
      ServletUriComponentsBuilder.fromCurrentRequestUri().build(id)
    ).body(service.modify(id, request));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity delete(@PathVariable Long id) {
    service.remove(id);
    return ResponseEntity.noContent().build();
  }

}
