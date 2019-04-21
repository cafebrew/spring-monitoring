package spring.monitoring.config;

import java.net.InetAddress;
import java.util.List;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricConfiguration {

  @Value("${spring.application.name}")
  private String applicationName;

  @Bean
  public MeterRegistryCustomizer<MeterRegistry> meterRegistryCustomizer() {
    return registry -> registry.config()
      .commonTags(List.of(
        Tag.of("application", applicationName),
        Tag.of("instance", InetAddress.getLoopbackAddress().getHostAddress()))
      );
  }

  @Bean
  public Counter newUserCounter(MeterRegistry registry) {
    return Counter.builder("newUserCounter")
      .description("indicate new user count")
      .tags(List.of(
        Tag.of("module", "user"),
        Tag.of("action", "join"))
      ).register(registry);
  }

  @Bean
  public Counter newFeedCounter(MeterRegistry registry) {
    return Counter.builder("newFeedCounter")
      .description("indicate new feed count")
      .tags(List.of(
        Tag.of("module", "feed"),
        Tag.of("action", "create"))
      ).register(registry);
  }

  @Bean
  public TimedAspect timedAspect(MeterRegistry registry) {
    return new TimedAspect(registry);
  }

}
