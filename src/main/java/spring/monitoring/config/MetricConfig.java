package spring.monitoring.config;

import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricConfig {

  @Bean
  MeterRegistryCustomizer meterRegistryCustomizer() {
    return registry -> registry.config().commonTags("application", "SpringMonitoring");
  }

}
