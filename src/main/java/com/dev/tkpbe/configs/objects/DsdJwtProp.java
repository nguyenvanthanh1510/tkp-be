package com.dev.tkpbe.configs.objects;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("tkp-config.jwt")
public class DsdJwtProp {
  String secret;
  long expiration;
}
