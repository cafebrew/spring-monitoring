package spring.monitoring.api.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException() {
    super("Could not found user");
  }

  public UserNotFoundException(String userId) {
    super("Could not found user " + userId);
  }
}
