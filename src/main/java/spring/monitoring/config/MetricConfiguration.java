package spring.monitoring.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricConfiguration {

  @Value("${spring.application.name}")
  private String applicationName;

  @Bean
  public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
    return registry -> registry.config().commonTags("profile", "my app");
  }

  @Bean
  public Counter userJoinCounter(MeterRegistry registry) {
    return Counter.builder("instance")
      .description("indicates instance count of the object")
      .tags("dev", "performance")
      .register(registry);
  }

  @Bean
  public TimedAspect timedAspect(MeterRegistry registry) {
    return new TimedAspect(registry);
  }

}
