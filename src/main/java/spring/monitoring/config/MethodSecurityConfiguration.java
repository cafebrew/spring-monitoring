package spring.monitoring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(
  prePostEnabled = true,
  jsr250Enabled = true)
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

}
