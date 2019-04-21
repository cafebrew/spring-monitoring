package spring.monitoring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import spring.monitoring.repository.AccountRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class BasicAuthDetailsService implements UserDetailsService {

  private final AccountRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return repository.findByUsername(username)
      .map(a -> User.builder()
        .username(a.getUsername())
        .password("{noop}" + a.getPassword())
        .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList(a.getRoles()))
        .build())
      .orElseThrow(() -> new UsernameNotFoundException("Could not find " + username));
  }
}
